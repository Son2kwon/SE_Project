import React, { useState,useEffect, } from 'react';
import '../styles/tableStyle.css'
import axios from 'axios';

const SearchResultTable=({ props }) =>{
  const role = sessionStorage.getItem('role')
  const [assignees, setAssignees] = useState([]);
  const [assigneeList, setAssigneeList] = useState([]);

  const handleChangeAssignee = (event, key) => {
    const selected = event.target.value;
    setAssignees((prevMap)=>({
      ...prevMap,
      [key]:selected
    }))
  }

  useEffect(() => {
    // 서버로부터 assignee 값을 받아옴
    axios({
      url: 'http://localhost:5000/get-developers',
      method: 'get',
      params: {token: sessionStorage.getItem('token')}
    })
      .then(response => {
        setAssigneeList(response.data);
      })
      .catch(error => {
        console.error('Error fetching assignees:', error);
      });
  }, []);


  const handleSaveAssignee = () => {
    axios.post('http://localhost:5000/assign', {
      assignees: assignees,
      token: sessionStorage.getItem("token"),
    })
    .then(response => {
      console.log('Assignees successfully saved:', response.data);
    })
    .catch(error => {
      console.error('Error saving assignments:', error);
    });
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
              {item.status === 'new' && role === 'pl' ? 
              <select onChange={event=>handleChangeAssignee(event,item.key)}>
                <option value="">Select Assignee</option>
                  {assigneeList.map((assignee, index) => (
                <option key={index} value={assignee}>{assignee}</option>
                ))}
              </select>
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