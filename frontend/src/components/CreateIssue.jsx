import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const CreateIssue = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [priority, setPriority] = useState('blocker');
  const [showIssueCreate, setShowIssueCreate] = useState();
  const {projectId} = useParams()
  const priorities = [
    { value: 'blocker', label: 'Blocker' },
    { value: 'critical', label: 'Critical' },
    { value: 'major', label: 'Major' },
    { value: 'minor', label: 'Minor' },
    { value: 'trivial', label: 'Trivial' },
  ];

  const handleSubmit = async (e) => {
    e.preventDefault();
    setShowIssueCreate(false)
    setTitle('');
    setDescription('');
    
    const token = sessionStorage.getItem('token')
    const issue = {
      title: title,
      description: description,
      priority: priority,
    };
    try {
      const response = await axios.post(URLS.CreateIssue, {
        token:token,
        projectId:projectId,
        issue:issue,
      }, 
      {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      console.log('Issue created:', response.data);
      // 성공적으로 생성된 후 추가적인 로직을 추가할 수 있습니다.
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