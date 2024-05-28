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
     issueStatus: [
      { value: 'NEW', label: 'New' },
      { value: 'ASSIGNED', label: 'Assigned' },
      { value: 'FIXED', label: 'Fixed' },
      { value: 'RESOLVED', label: 'Resolved' },
      { value: 'CLOSED', label: 'Closed' },
      { value: 'REOPENED', label: 'Reopened' },
      ],
      personField: [
        { value: 'assignee', label: "Assignee" },
        { value: 'reporter', label: "Reporter" },
        { value: 'fixer', label: "Fixer" },
      ],
      priorityField: [
        { value: 'LOW', label: "Low" },
        { value: 'MEDIUM', label: "Medium" },
        { value: 'HIGH', label: "High" },
        { value: 'CRITICAL', label: "Critical" },
      ]
  }
  useEffect(()=>{
    const fetchFirstData=async()=>{
      if(role==='PL'){
        await axios.get(URLs.SEARCH+'/all',{
          params:{projectId:projectId,token:sessionStorage.getItem('token'),role:role}
        })
        .then(response=>{
          setSearchData(response.data)
        }).catch(error=>{console.log(error)})
      }
    }
    fetchFirstData();
  },[])

  useEffect(() => {
    if (selectedOption && selectedOption.value !== '') {
      switch (selectedOption.value) {
        case 'issueStatus':
          setNextDropdownOptions(optionsByRole.issueStatus);
          break;
        case 'personField':
          setNextDropdownOptions(optionsByRole.personField);
          break;
        case 'priorityField':
          setNextDropdownOptions(optionsByRole.priorityField);
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
      await axios.get(url,{
        params:{...searchParam,projectId:projectId,token:sessionStorage.getItem('token')}
      })
      .then(response=>{
        setSearchData(response.data)
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
      {searchData && searchData.length > 0 && <SearchResultTable props={searchData} projectId={projectId} />}
    </div>
  );
};

export default Search;