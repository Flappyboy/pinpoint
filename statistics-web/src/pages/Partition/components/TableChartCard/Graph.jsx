import React, { Component } from 'react';

// 引入 ECharts 主模块
import echarts from 'echarts/lib/echarts';
import axios from 'axios';
import 'echarts';

const graphStyle = {
  width: 800,
  height: 400,
};


class Graph extends Component {
  componentDidMount() {
    axios.get('https://5b5e71c98e9f160014b88cc9.mockapi.io/api/v1/lists')
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  loadData = (json) => {
    // 基于准备好的dom，初始化echarts实例
    const myChart = echarts.init(document.getElementById('graph'));
    // 绘制图表
    myChart.showLoading();
    myChart.hideLoading();

    const option = {
      legend: {
        data: ['HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other']
      },
      series: [{
        type: 'graph',
        layout: 'force',
        animation: false,
        label: {
          normal: {
            position: 'right',
            formatter: '{b}',
          },
        },
        draggable: true,
        data: json.nodes.map((node, idx) => {
          node.id = idx;
          return node;
        }),
        categories: json.categories,
        force: {
          // initLayout: 'circular'
          // repulsion: 20,
          edgeLength: 5,
          repulsion: 20,
          gravity: 0.2,
        },
        edges: json.links,
      }],
    };

    myChart.setOption(option);
  }

  render() {
    return (
      <div id="graph" style={graphStyle}></div>
    );
  }
}

export default Graph;