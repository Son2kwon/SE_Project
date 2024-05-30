import React, { useState } from 'react';
import IssueStatisticByDate from './IssueStatisticsByDate';
import IssueStatisticByTag from './IssueStatisticsByTag';

const IssueStatistics = () => {
  const [dataType, setDataType] = useState('date');

  const handleDataTypeChange = (type) => {
    setDataType(type);
  };

  return (
    <div>
      <h1>Issue Statistics</h1>
      <div>
        <button onClick={() => handleDataTypeChange('date')}>View by Date</button>
        <button onClick={() => handleDataTypeChange('tag')}>View by Tag</button>
      </div>
      {dataType === 'date' && (
        <div>
          <IssueStatisticByDate xLabel="Date" yLabel="Count" />
        </div>
      )}
      {dataType === 'tag' && (
        <IssueStatisticByTag xLabel="Tag" yLabel="Count" />
      )}
    </div>
  );
};

export default IssueStatistics;