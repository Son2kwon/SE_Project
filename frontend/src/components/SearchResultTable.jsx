import React, { useState,useEffect, } from 'react';
import '../styles/tableStyle.css'
import axios from 'axios';
import URLs from '../utils/urls';
import Select from 'react-select';

const SearchResultTable=({ props,projectId }) =>{
  const role = sessionStorage.getItem('role')
  const [assignees, setAssignees] = useState([]);
  const [assigneeList, setAssigneeList] = useState([]);
  const [assignedAssigneeList,setAssignedAssigneeList] = useState([]);
  const [items,setItems] = useState([]);
  const [tagToAssigneesMap, setTagToAssigneesMap] = useState([]);
  useEffect(() => {
    // 서버로부터 assignee 값을 받아옴
    axios({
      url: URLs.GetDev,
      method: 'get',
      params: {token: sessionStorage.getItem('token'), projectId:projectId}
    })
      .then(response => {
        setAssigneeList(response.data);
      })
      .catch(error => {
        console.error('Error fetching assignees:', error);
      });
  }, []);

  const mapRecommendedAssignees = async (props) => {
    const tags = [...new Set(props.map(item => item.tag))];
    setTagToAssigneesMap(await fetchRecommendedAssigneesForTags(tags));
  };

  async function fetchRecommendedAssigneesForTags(tags) {
    const tagToAssigneesMap = {};
  
    await Promise.all(tags.map(async (tag) => {
      const recommendedAssignees = await fetchRecommendedAssignee(tag);
      tagToAssigneesMap[tag] = recommendedAssignees;
    }));
  
    return tagToAssigneesMap;
  }

  async function fetchRecommendedAssignee(tag) {
    if (!tag) {
      return [];
    }
    try {
      const response = await axios.get(URLs.GetRecommendDev, {
        params: { tag:tag, projectID:projectId }
      });
      return response.data;
    } catch (error) {
      throw new Error(`Failed to fetch recommended assignees for tag: ${tag}`);
    }
  }

  useEffect(() => {
    async function fetchData() {
      try {
        await mapRecommendedAssignees(props);
      } catch (error) {
        console.error('Failed to fetch recommended assignees', error);
      }
    }
    fetchData();
  }, [props]);

  const handleChangeAssignee = (selectedOptions, issueId) => {
    const selectedAssignees = selectedOptions.map(option => option.value);
    const newAssignments = selectedAssignees.map(assignee => ({ issueId, assignee }));
    const updatedAssignments = [...assignedAssigneeList, ...newAssignments.filter(newAssignment => (
      !assignedAssigneeList.some(existingAssignment => (
        existingAssignment.issueId === newAssignment.issueId && existingAssignment.assignee === newAssignment.assignee
      ))
    ))];
  setAssignedAssigneeList(updatedAssignments);
  }

  const handleSaveAssignee = () => {
    axios.post(URLs.AssignDev, {
      assignees: assignedAssigneeList,
      token: sessionStorage.getItem("token"),
      projectId: projectId,
    })
    .then(response => {
      console.log('Assignees successfully saved', response.data);
    })
    .catch(error => {
      console.error('Error saving assignments:', error);
    });
    window.location.reload();
  };
  console.log(tagToAssigneesMap)
  return (
    <div>
    <table className="table">
      <thead>
        <tr>
          <th>Title</th>
          <th>Tag</th>
          <th>Status</th>
          <th>Reporter</th>
          <th>Assignee</th>
          <th>Fixer</th>
          <th>Priority</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        {props.map((item, index) => (
          <tr key={index}>
            <td><a href={`http://localhost:8080/issue/detail/${projectId}/${item.id}?token=${sessionStorage.getItem('token')}`} target="_blank" rel="noopener noreferrer">
            {item.title}</a></td>
            <td>{item.tag}</td>
            <td>{item.status}</td>
            <td>{item.reporter}</td>
            <td>
              {item.status === 'NEW' && (role === 'PL' || role ==='admin') ? 
              <Select 
                onChange={event=>handleChangeAssignee(event,item.id)}
                options={
                 assigneeList.map(assignee => ({
                  value: assignee,
                  label: `${assignee} ${(tagToAssigneesMap[item.tag] || []).includes(assignee) ? "(추천)" : ""}`
                }))
                }
                isMulti
              />
              : item.assignees
              }
            </td>
            <td>{item.fixer}</td>
            <td>{item.priority}</td>
            <td>{item.date.slice(2, 16).replace('T', ' ')}</td>

          </tr>
        ))}
      </tbody>
    </table>
    <button onClick={handleSaveAssignee}>저장</button>
    </div>
  );
};

export default SearchResultTable