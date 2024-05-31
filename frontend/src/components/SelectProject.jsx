import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import URLs from '../utils/urls';
import '../styles/SelectProjectForm.css'

const SelectProject = () => {
    const [projectList, setProjectList] = useState([]);
    const [selectedProject, setSelectedProject] = useState(null);
    const navigate = useNavigate();

    const fetchRole=async(projectId)=>{
      let role = ''
      await axios.get(URLs.GetRole,{
       params:{projectId:projectId, token:sessionStorage.getItem('token')} 
      })
      .then(response=>{
        role = response.data.role;
        sessionStorage.setItem('role',role)
      })
    }

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
      <div className="project-container">
        {projectList.length === 0 ? (
          <div className="no-projects">No projects found</div>
        ) : (
          projectList.map(project => (
            <button
              key={project.id}
              className="project-button"
              onClick={async() => {
                await fetchRole(project.id)
                navigate(`/project/${project.id}/${encodeURIComponent(project.name)}`)}}
            >
              Project {project.id}: {project.name}
            </button>
          ))
        )}
      </div>
    );
  };
  
  export default SelectProject;