import React, { useState,useEffect } from 'react';

const SearchResult=({ searchData }) =>{
  return (
    <table>
      <thead>
        <tr>
          <th>Title</th>
          <th>Status</th>
          <th>Priority</th>
          <th>Date</th>
          <th>Reporter</th>
          <th>Fixer</th>
          <th>Assignee</th>
        </tr>
      </thead>
      <tbody>
        {searchData.map((item, index) => (
          <tr key={index}>
            <td>{item.title}</td>
            <td>{item.status}</td>
            <td>{item.priority}</td>
            <td>{item.date}</td>
            <td>{item.reporter}</td>
            <td>{item.fixer}</td>
            <td>{item.assignee}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default SearchResult