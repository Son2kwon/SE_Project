import React, { useState,useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Select from 'react-select';
import URLs from '../utils/urls';
import SearchResultTable from './SearchResultTable';
import axios from 'axios';

const Search = () => {
  const {projectId} = useParams()
  const [searchTerm, setSearchTerm] = useState('');
  const [searchData, setSearchData] = useState([]);
  const [selectedOption, setSelectedOption] = useState({});
  const [nextSelectedOption, setNextSelectedOption] = useState({});
  const [nextDropdownOptions, setNextDropdownOptions] = useState([]);
  let role = sessionStorage.getItem('role')
  if(role==='admin') role='PL'

  const optionsByRole={
    PL:{
     issueStatus: [
      { value: 'new', label: 'New' },
      { value: 'assigned', label: 'Assigned' },
      { value: 'fixed', label: 'Fixed' },
      { value: 'resolved', label: 'Resolved' },
      { value: 'closed', label: 'Closed' },
      { value: 'reopened', label: 'Reopened' },
      ],
      personField: [
        { value: 'assignee', label: "Assignee" },
        { value: 'reporter', label: "Reporter" },
        { value: 'fixer', label: "Fixer" },
      ],
      priorityField: [
        { value: 'blocker', label: "Blocker" },
        { value: 'critical', label: "Critical" },
        { value: 'major', label: "Major" },
        { value: 'minor', label: "Minor" },
        { value: 'trivial', label: "Trivial"}
      ],},
      tester: [{ value: 'fixed', label: 'fixed' }],
      dev: [{ value: 'assigned', label: 'assigned' }]
  }
  useEffect(()=>{
    const fetchFirstData=async()=>{
      if(role==='PL'){
        await axios.get(URLs.SEARCH+'/all',{
          params:{projectId:projectId,token:sessionStorage.getItem('token'),role:role}
        })
        .then(response=>{
          setSearchData(response.data.results)
        }).catch(error=>{console.log(error)})
      }
    }
    fetchFirstData();
  },[])

  useEffect(() => {
    if (role==='PL'&& selectedOption && selectedOption.value !== '') {
      switch (selectedOption.value) {
        case 'issueStatus':
          setNextDropdownOptions(optionsByRole['PL'].issueStatus);
          break;
        case 'personField':
          setNextDropdownOptions(optionsByRole['PL'].personField);
          break;
        case 'priorityField':
          setNextDropdownOptions(optionsByRole['PL'].priorityField);
          break;
        default:
          setNextDropdownOptions([]);
      }
    }
  }, [selectedOption]);

  const handleDropdownChange = (selected) => {
    setSelectedOption(selected);
  };
  const handleTermChange = event => {
    setSearchTerm(event.target.value);
  };
  
  const handleSearch = async(event) => {
    event.preventDefault();
    try{
      let url,searchParam
      //tester일 경우는 자신이 report하고 fixed된 이슈만 검색.
      if (role==='TESTER'){
        url = URLs.SEARCH + '/byIssueStatus'
        searchParam = {status:'fixed', reporter: sessionStorage.getItem('id')}
      }
      else if (role==='DEV'){
        url = URLs.SEARCH + '/byIssueStatus'
        searchParam = {status:'assigned', assignee: sessionStorage.getItem('id')}
      }
      else{
        switch (selectedOption.value){
          case 'issueStatus':
            url = URLs.SEARCH + "/byIssueStatus"
            searchParam = {status: nextSelectedOption.value}
            break;
          case 'personField':
            url = URLs.SEARCH + "/byPerson"
            searchParam = {
              role: nextSelectedOption.value,
              id: searchTerm,
            }
            break;
          case 'priorityField':
            url = URLs.SEARCH + "/byPriority"
            searchParam = {priority: nextSelectedOption.value}
            break;
        }
      }
      await axios.get(url,{
        params:{...searchParam,projectId:projectId,token:sessionStorage.getItem('token')}
      })
      .then(response=>{
        setSearchData(response.data.results)
      })
    }catch(error){
      alert('검색어를 선택하세요!')
    }
}

  return (
    <div>
      Project {projectId}<br/>
      { (
        <Select
          placeholder="검색 조건 선택"
          value={selectedOption}
          onChange={handleDropdownChange}
          options={[
            { value: 'issueStatus', label: '이슈 상태' },
            { value: 'personField', label: '사람으로 검색' },
            { value: 'priorityField', label: '우선순위' }
          ]}
        />
      )}
      {nextDropdownOptions.length > 0 && (
      <div style={{ marginTop: '10px' }}>
        <Select
          placeholder="선택"
          options={nextDropdownOptions}
          onChange={(selected)=>{setNextSelectedOption(selected)}}
        />
        {selectedOption.value === 'personField' && (
          <input
            type="text"
            value={searchTerm}
            onChange={handleTermChange}
            placeholder="id"
            style={{ marginTop: '10px' }}
          />
        )}
      </div>
      )}
      <button onClick={handleSearch} style={{ marginTop: '10px' }}>검색</button>
      {searchData.length > 0 && <SearchResultTable props={searchData} />}
    </div>
  );
};

export default Search;