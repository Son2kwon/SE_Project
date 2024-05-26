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
  useEffect(() => {
    // 서버로부터 assignee 값을 받아옴
    axios({
      url: URLs.GetDev,
      method: 'get',
      params: {token: sessionStorage.getItem('token'), projectId:projectId}
    })
      .then(response => {
        setAssigneeList(response.data);
        console.log(assigneeList);
        console.log(role);
      })
      .catch(error => {
        console.error('Error fetching assignees:', error);
      });
  }, []);
  
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
  return (
    <div>
    <table className="table">
      <thead>
        <tr>
          <th>Title</th>
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
            <td>{item.title}</td>
            <td>{item.status}</td>
            <td>{item.reporter}</td>
            <td>
              {item.status === 'NEW' && (role === 'PL' || role ==='admin') ? 
              <Select 
                onChange={event=>handleChangeAssignee(event,item.id)}
                options={assigneeList.map((assignee, index) => ({
                value: assignee,
                label: assignee
                }))}
                isMulti
              />
              : item.assignee
              }
            </td>
            <td>{item.fixer}</td>
            <td>{item.priority}</td>
            <td>{item.date}</td>

          </tr>
        ))}
      </tbody>
    </table>
    <button onClick={handleSaveAssignee}>저장</button>
    </div>
  );
};

export default SearchResultTable