import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import URLs from '../../utils/urls';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const IssueStatisticsByDate = ({}) => {
  const [period, setPeriod] = useState('week'); // 'week' or 'month'
  const [chartData, setChartData] = useState({ labels: [], datasets: [] });

  useEffect(() => {
    fetchData();
  }, [period]);

  const processData = (data) => {
    if (period === 'week') {
      setChartData(processWeeklyData(data));
    } else if (period === 'month') {
      setChartData(processMonthlyData(data));
    }
  };

  const fetchData = async () => {
    axios.get(URLs.GetIssueCountByDate)
    .then(response=>{
      processData(response.data);
    })
    .catch(error=>{
      console.error("Error fetching data", error);
    })
  }

  const processWeeklyData = (data) => {
    const sortedData = data.sort((a, b) => new Date(a.date) - new Date(b.date));
    const labels = sortedData.map(entry => entry.date);
    const counts = sortedData.map(entry => entry.count);
    
    return {
      labels,
      datasets: [
        {
          label: 'Count',
          data: counts,
          fill: false,
          backgroundColor: 'rgba(220, 235, 55, 0.6)',
          borderColor: 'rgba(220, 235, 55, 1)',
        },
      ],
    };
  };

  const processMonthlyData = (data) => {
    const weeks = [0, 0, 0, 0]; // Four weeks
    const sortedData = data.sort((a, b) => new Date(a.date) - new Date(b.date));

    sortedData.forEach(entry => {
      const date = new Date(entry.date);
      const week = Math.floor(date.getDate() / 7);
      weeks[week] += entry.count;
    });

    return {
      labels: ['1st Week', '2nd Week', '3rd Week', '4th Week'],
      datasets: [
        {
          label: 'Count',
          data: weeks,
          fill: false,
          backgroundColor: 'rgba(153, 102, 255, 0.6)',
          borderColor: 'rgba(153, 102, 255, 1)',
        },
      ],
    };
  };

  return (
    <div>
      <div>
        <label htmlFor="period">Select Period: </label>
        <select id="period" value={period} onChange={(e) => setPeriod(e.target.value)}>
          <option value="week">Weekly</option>
          <option value="month">Monthly</option>
        </select>
      </div>
      <div class="chart-container">
        <Line data={chartData} />
      </div>
    </div>
  );
};

export default IssueStatisticsByDate;