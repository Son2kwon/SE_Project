import React, { useState } from 'react';
import { Navigate, useParams } from 'react-router-dom';
import URLs from '../utils/urls';
import axios from 'axios';
import '../styles/CreateIssueForm.css'

const CreateIssue = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  //초기값 LOW
  const [priority, setPriority] = useState('LOW');
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
      //issue 필드 값 초기화
      setShowIssueCreate(false);
      setTitle('')
      setDescription('')
      setPriority('LOW')
    } catch (error) {
      console.error('There was an error creating the issue!', error);
    }
  };

  return (
      <div>
        <button className="button" onClick={() => setShowIssueCreate(true)}>Create Issue</button>
        {showIssueCreate?
        <form className="form" onSubmit={handleSubmit}>
        <div>
          <label className="label" htmlFor="title">Title</label>
            <input
              className="input"
              id="title"
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
        </div>
        <div>
          <label className="label" htmlFor="description">Description</label>
            <textarea
              className="textarea"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
            ></textarea>
        </div>
        <div>
            <label className="label" htmlFor="priority">Priority</label>
            <select
              className="select"
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
        <button className="submit-button" type="submit">이슈 생성</button>
        </form>
        :null}
      </div>
  );
};

export default CreateIssue;