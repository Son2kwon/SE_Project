import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Bar } from 'react-chartjs-2';
import { BarElement, Chart } from 'chart.js';
import URLs from '../../utils/urls';
Chart.register(BarElement);

const IssueStatisticByPriority = () => {
  const [chartData, setChartData] = useState({ labels: [], datasets: [] });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    axios.get(URLs.GetIssueCountByPriority)
      .then(response => {
        processData(response.data);
      })
      .catch(error => {
        console.error("Error fetching data", error);
      });
  };

  const processData = (data) => {
    // 데이터를 우선순위에 따라 정렬하고, 그래프에 표시하기 적합한 형식으로 가공
    const sortedData = data.sort((a, b) => {
      const priorityA = Object.keys(a)[0];
      const priorityB = Object.keys(b)[0];

      const priorityOrder = {
        'LOW': 0,
        'MEDIUM': 1,
        'HIGH': 2,
        'CRITICAL': 3
      };

      return priorityOrder[priorityA] - priorityOrder[priorityB];
    });

    const labels = [];
    const counts = [];

    const priorityTags = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
    priorityTags.forEach(tag => {
      const dataEntry = sortedData.find(entry => Object.keys(entry)[0] === tag);
      if (dataEntry) {
        labels.push(tag);
        counts.push(dataEntry[tag]);
      } else {
        labels.push(tag);
        counts.push(0);
      }
    });

    setChartData({
      labels,
      datasets: [
        {
          label: 'Count',
          data: counts,
          backgroundColor: 'rgba(54, 162, 235, 0.6)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
        },
      ],
    });
  };

  return (
    <div>
      {chartData.labels.length > 0 ? (
        <Bar
          class="chart-container"
          data={chartData}
          options={{
            scales: {
              x: {
                title: {
                  display: true,
                  text: 'Priority',
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

export default IssueStatisticByPriority;