import React from 'react';
import LogOut from '../login/LogOut'
import { Link } from 'react-router-dom';
import Search from '../components/SearchIssue';

const HomePage = () => {
    return (
      <div>
        <LogOut/>
        <Search/>
      </div>
    );
  };

  export default HomePage;