import React, { useState,useEffect } from 'react';
import Select from 'react-select';
import URLs from '../utils/urls';
import SearchResultTable from './SearchResultTable';
const plOption = [
  { value: '', label: "선택 안 함" },
  { value: 'new', label: 'new' },
  { value: 'assigned', label: 'assigned' },
  { value: 'fixed', label: 'fixed' },
  { value: 'resolved', label: 'resolved' },
  { value: 'closed', label: 'closed' },
  { value: 'reopened', label: 'reopened' },
];
const personField = [
    { value: '', label: '선택 안 함'},
    { value: 'assignee', label: "담당자" },
    { value: 'reporter', label: "보고자" },
    { value: 'fixer', label: "수정자" },
]
const priorityField = [
    { value: '', label: "선택 안 함"},
    { value: 'blocker', label: "blocker" },
    { value: 'blocker', label: "critical" },
    { value: 'major', label: "major" },
    { value: 'minor', label: "minor" },
    { value: 'trivial', label: "trivial"}
]

const testerOption = [{ value: 'fixed', label: 'fixed' }]
const devOption = [{ value: 'assigned', label: 'assigned' }]


const Search = () => {
  const [issueStatus, setIssueStatus] = useState('');
  const [person, setPerson] = useState('');
  const [priority, setPriority] = useState('');
  const [optionsByRole, setOptionsByRole] = useState(plOption);
  const [searchTerm, setSearchTerm] = useState('');
  const [searchData, setSearchData] = useState([]);

  const role = sessionStorage.getItem('role')
  useEffect(() => {
    const role = sessionStorage.getItem('role');
    if (role === 'tester') {
      setOptionsByRole(testerOption);
    } else if (role === 'developer') {
      setOptionsByRole(devOption);
    } else {
      setOptionsByRole(plOption);
    }
  }, []);
  const handleStatusChange = issueStatus => {
    setIssueStatus(issueStatus);
  };
  const handlePersonChange = person => {
    setPerson(person);
  };
  const handlePriorityChange = priority => {
    setPriority(priority);
  };
  const handleTermChange = event => {
    setSearchTerm(event.target.value);
  };
  
  const handleSearch = async(event) => {
    event.preventDefault();
    try{
      console.log("검색어:", issueStatus, person, searchTerm, priority);
      const response = await fetch(`${URLs.SEARCH}?status=${issueStatus.value? issueStatus.value : ''}&person=${person.value ? person.value : ''}&term=${searchTerm.value ? searchTerm.value : ''}&priority=${priority.value ? priority.value : ''}`);
      const data = await response.json();
      setSearchData(data.results);
    }catch(error){
        console.error('Error fetching search results:', error);
    }
}

  return (
    <div>
      <div style={{ width:'180px',marginBottom: '10px'}}>
        <Select
            placeholder="이슈 상태"
            value={issueStatus}
            onChange={handleStatusChange}
            options={optionsByRole}
            //options={plOption}
            //isSearchable // 이것이 드롭다운에서 검색을 가능하게 하는 부분입니다.
        />
      </div>
      {role === 'pl' && (
        <div>
          <div style={{  display:'flex',marginBottom: '10px'}}>
            <div style={{width:'180px',}} >
              <Select
                placeholder="사람으로 검색"
                value={person}
                onChange={handlePersonChange}
                options={personField}/>
            </div>
            {person && person.value !== '' && (
              <input
                type="text"
                value={searchTerm}
                onChange={handleTermChange}
                placeholder="텍스트 검색어 입력"
                style={{ flex: 0.2, marginLeft: '10px'}}/>
            )}
          </div>
          <div style={{  display:'flex',marginBottom: '10px'}}>
            <div style={{width:'180px',}} >
              <Select
                placeholder="우선순위"
                value={priority}
                onChange={handlePriorityChange}
                options={priorityField}/>
            </div>
          </div>
        </div>
      )}
      <button onClick={handleSearch}>검색</button>
      {searchData.length > 0 && <SearchResultTable props={searchData}/>}

    </div>
  );
};

export default Search;