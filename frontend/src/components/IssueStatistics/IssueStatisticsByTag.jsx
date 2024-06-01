import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Bar } from 'react-chartjs-2';
import { BarElement, Chart } from 'chart.js';
import URLs from '../../utils/urls';
Chart.register(BarElement);

const IssueStatisticByTag = () => {
  const [chartData, setChartData] = useState({ labels: [], datasets: [] });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    axios.get(URLs.GetIssueCountByTag)
    .then(response=>{
      processData(response.data);
    })
    .catch(error=>{
      console.error("Error fetching data", error);
    })
  }

  const processData = (data) => {
    const processedData = data.map(entry => {
      return {
          ...entry,
          tag: entry.tag === null ? 'no tag' : entry.tag
      };
  });
    const sortedData = processedData.sort((a, b) => b.count - a.count);
    const labels = sortedData.map(entry => entry.tag);
    const counts = sortedData.map(entry => entry.count);
    setChartData({
      labels,
      datasets: [
        {
          label: 'Count',
          data: counts,
          backgroundColor: 'rgba(75, 192, 192, 0.6)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1,
        },
      ],
    });
  };

  return (
    <div>
      {chartData.labels.length > 0 ? (
      <Bar class="chart-container"
        data={chartData}
        options={{
          scales: {
            x: {
              title: {
                display: true,
                text: 'Tag',
                color: 'black',
                font: {
                  weight: 'bold',
                  size: 16
                }
              },
              grid: {
                display: false
              }
            },
            y: {
              title: {
                display: true,
                text: 'Count',
                color: 'black',
                font: {
                  weight: 'bold',
                  size: 16
                }
              },
              grid: {
                display: true
              }
            }
          }
        }}
      />
    ) : (<div>No data available</div>)}
    </div>
  );
};

export default IssueStatisticByTag;