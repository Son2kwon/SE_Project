import React from 'react';
import LogOut from '../login/LogOut'
import Search from '../components/SearchIssue';
import SelectProject from '../components/SelectProject';
import '../styles/HomePage.css'

const HomePage = () => {
  return (
    <div className="page-container">
      <LogOut/>
      <SelectProject/>
    </div>
  );
};

export default HomePage;