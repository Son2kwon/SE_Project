import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import URLs from '../utils/urls';

const SelectProject = () => {
    const [projectList, setProjectList] = useState([]);
    const [selectedProject, setSelectedProject] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
      const fetchData = async () => {
        try {
          const response = await axios.get(URLs.GetProjectList, {
            params: {
              token: sessionStorage.getItem('token')
            }
          });
          setProjectList(response.data);
        } catch (error) {
          console.error('Error fetching data: ', error);
        }
      };
      fetchData();
    }, []);
  
    return (
      <div>
        <div>
          {projectList.length === 0 ? (
            <div>No projects found</div>
          ) : (
            projectList.map(project => (
              <button
                key={project.id}
                onClick={() => navigate(`/project/${project.id}`)}
              >
                Project {project.id}: {project.name}
              </button>
            ))
          )}
        </div>
      </div>
    );
  };
  
  export default SelectProject;