import React, { useState } from 'react';
import { Navigate, useParams } from 'react-router-dom';
import URLs from '../utils/urls';
import axios from 'axios';

const CreateIssue = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [priority, setPriority] = useState('');
  const [showIssueCreate, setShowIssueCreate] = useState();
  const {projectId} = useParams()
  const priorities = [
    { value: 'LOW', label: 'Low' },
    { value: 'MEDIUM', label: 'Medium' },
    { value: 'HIGH', label: 'High' },
    { value: 'CRITICAL', label: 'Critical' },
  ];

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = sessionStorage.getItem('token')
    try {
      const response = await axios.post(URLs.CreateIssue, {
        token:token,
        projectId:projectId,
        title: title,
        issueDescription: description,
        priority: priority,
      }, 
      {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      alert("이슈 생성 완료")
      setShowIssueCreate(false);
    } catch (error) {
      console.error('There was an error creating the issue!', error);
    }
  };

  return (
      <div>
        <button onClick={() => setShowIssueCreate(true)}>Create Issue</button>
        {showIssueCreate?
        <form onSubmit={handleSubmit}>
        <div>
            <label htmlFor="title">Title</label>
            <input
            id="title"
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            />
        </div>
        <div>
            <label htmlFor="description">Description</label>
            <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            ></textarea>
        </div>
        <div>
            <label htmlFor="priority">Priority</label>
            <select
              id="priority"
              value={priority}
              onChange={(e) => setPriority(e.target.value)}
            >
            {priorities.map((p) => (
              <option key={p.value} value={p.value}>
                {p.label}
              </option>
            ))}
            </select>
        </div>
        <button type="submit">이슈 생성</button>
        </form>
        :null}
      </div>
  );
};

export default CreateIssue;