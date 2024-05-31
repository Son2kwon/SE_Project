import "../styles/SearchIssueForm.css"
import React, { useState,useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Select from 'react-select';
import URLs from '../utils/urls';
import SearchResultTable from './SearchResultTable';
import axios from 'axios';
import LogOut from '../login/LogOut';
import styled from 'styled-components';

const StyledSelect = styled(Select)`width:200px`

const Search = () => {
  const {projectId, projectName} = useParams();
  const [placeholder, setPlaceholder] = useState('검색 조건 선택');
  const [searchTerm, setSearchTerm] = useState('');
  const [searchData, setSearchData] = useState([]);
  const [selectedOption, setSelectedOption] = useState([]);
  const [nextSelectedOption, setNextSelectedOption] = useState({});
  const [nextDropdownOptions, setNextDropdownOptions] = useState([]);
  const [showSearchOptions, setShowSearchOptions] = useState(false);

  let role = sessionStorage.getItem('role')
  let email = sessionStorage.getItem('email')
  if(role==='admin') role='PL'

  const optionsByRole={
     issueStatus: [
      { value: 'NEW', label: 'New' },
      { value: 'ASSIGNED', label: 'Assigned' },
      { value: 'FIXED', label: 'Fixed' },
      { value: 'RESOLVED', label: 'Resolved' },
      { value: 'CLOSED', label: 'Closed' },
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
  const fetchFirstData=async()=>{
    await axios.get(URLs.SEARCH+'/all',{
      params:{projectId:projectId,token:sessionStorage.getItem('token'),role:role}
    })
    .then(response=>{
      setSearchData(response.data)
    }).catch(error=>{console.log(error)})
  }
  useEffect(()=>{
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
          break;
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
    //event.preventDefault();
    try{
      let url,searchParam
      switch (selectedOption.value){
        case 'issueStatus':
          if(!nextSelectedOption.value) alert("이슈 상태를 선택하세요!");
          url = URLs.SEARCH + "/byIssueStatus"
          searchParam = {status: nextSelectedOption.value}
          break;
        case 'personField':
          if(!nextSelectedOption || !nextSelectedOption.value || !searchTerm) alert("검색어를 입력하세요!");
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
        default:
        case 'all':
          url = URLs.SEARCH + "/all"
          break;
      }
      await axios.get(url,{
        params:{...searchParam,projectId:projectId,token:sessionStorage.getItem('token')}
      })
      .then(response=>{
        setSearchData(response.data)
      })
      
    }catch(error){
      console.log(error);
    }
    setSelectedOption(null);
    setNextSelectedOption(null);
    setNextDropdownOptions(null);
  }

  return (
    <div>
      <button className="button" onClick={() => setShowSearchOptions(!showSearchOptions)}>검색하기</button>
      {showSearchOptions && (
        <div>
          <StyledSelect
            className="search-select"
            placeholder={placeholder}
            onChange={(selected)=>handleDropdownChange(selected)}
            options={[
              { value: 'issueStatus', label: '이슈 상태' },
              { value: 'personField', label: '사람으로 검색' },
              { value: 'priorityField', label: '우선순위' },
              { value: 'all', label:  '전체 검색'}
            ]}
          />
        
      {nextDropdownOptions && nextDropdownOptions.length > 0 && (
        <div style={{ marginTop: '10px' }}>
          <StyledSelect
            className="search-select"
            placeholder="선택"
            options={nextDropdownOptions}
            onChange={(selected)=>{setNextSelectedOption(selected)}}
          />
          {selectedOption && selectedOption.value === 'personField' && (
            <input
              className="search-input"
              type="text"
              value={searchTerm}
              onChange={handleTermChange}
              placeholder="id"
              style={{ marginTop: '10px' }}
              required
            />
          )}
        </div>
        )}
        <button className="search-button" onClick={handleSearch} style={{ marginTop: '10px' }}>검색</button>
        {searchData && searchData.length > 0 && <SearchResultTable props={searchData} projectId={projectId} />}
        {(!searchData || searchData.length==0) && <div>검색 결과가 없습니다</div>}
        </div>)}
    </div>
  );
};

export default Search;