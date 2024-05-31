import "../../styles/Graph.css"
import React, { useState } from 'react';
import IssueStatisticByDate from './IssueStatisticsByDate';
import IssueStatisticByTag from './IssueStatisticsByTag';
import LogOut from '../../login/LogOut';
import IssueStatisticByPriority from "./IssueStatisticsByPriority";

const IssueStatistics = () => {
  const [dataType, setDataType] = useState('date');

  const handleDataTypeChange = (type) => {
    setDataType(type);
  };

  return (
    <div>
      <LogOut/><br/>
      <div className="container">
        <h1>Issue Statistics</h1>
        <div>
          <button onClick={() => handleDataTypeChange('date')}>View by Date</button>
          <button onClick={() => handleDataTypeChange('tag')}>View by Tag</button>
          <button onClick={() => handleDataTypeChange('priority')}>View by Priority</button>
        </div>
        {dataType === 'date' && (
          <div className='chart-container'> 
            <IssueStatisticByDate xLabel="Date" yLabel="Count" />
          </div>
        )}
        {dataType === 'tag' && (
          <><div className='chart-container'>
              <IssueStatisticByTag xLabel="Tag" yLabel="Count" />
            </div>
          </>
        )}
        {dataType === 'priority' && (
          <><div className='chart-container'>
              <IssueStatisticByPriority xLabel="Priority" yLabel="Count" />
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default IssueStatistics;