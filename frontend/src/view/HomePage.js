import React from 'react';
import LogOut from '../login/LogOut'
import Search from '../components/SearchIssue';
import SelectProject from '../components/SelectProject';

const HomePage = () => {
  return (
    <div>
      <LogOut/>
      <SelectProject/>
    </div>
  );
};

export default HomePage;