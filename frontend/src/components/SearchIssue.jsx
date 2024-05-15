import React, { useState,useEffect } from 'react';
import Select from 'react-select';
import URLs from '../utils/urls';
import SearchResultTable from './SearchResultTable';
import axios from 'axios';

const Search = () => {
  //const [optionsByRole,setOptionsByRole] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [searchData, setSearchData] = useState([]);
  const [selectedOption, setSelectedOption] = useState({});
  const [nextSelectedOption, setNextSelectedOption] = useState({});
  const [nextDropdownOptions, setNextDropdownOptions] = useState([]);
  const role = sessionStorage.getItem('role')

  const optionsByRole={
    pl:{
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

  useEffect(() => {
    if (role==='pl'&& selectedOption && selectedOption.value !== '') {
      switch (selectedOption.value) {
        case 'issueStatus':
          setNextDropdownOptions(optionsByRole['pl'].issueStatus);
          break;
        case 'personField':
          setNextDropdownOptions(optionsByRole['pl'].personField);
          break;
        case 'priorityField':
          setNextDropdownOptions(optionsByRole['pl'].priorityField);
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
            id: searchTerm
          }
          break;
        case 'priorityField':
          url = URLs.SEARCH + "/byPriority"
          searchParam = {priority: nextSelectedOption.value}
          break;
      }
      await axios.get(url,{
        params:searchParam
      })
      .then(response=>{
        setSearchData(response.data.results)
      })
    }catch(error){
        console.error('Error fetching search results:', error);
    }
}

  return (
    <div>
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