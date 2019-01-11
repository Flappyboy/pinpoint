import React, { Component } from 'react';

// 引入 ECharts 主模块
import echarts from 'echarts/lib/echarts';
import 'echarts';
import emitter from '../ev';

const graphStyle = {
  height: 700,
};

let myChart;
class Graph extends Component {
  constructor(props) {
    super(props);
    this.state = {

    };
  }

  componentDidUpdate(prevProps) {
    if (this.props.isLoading) {
      myChart.showLoading();
    } else {
      myChart.hideLoading();
      this.loadData(this.props.data);
    }
  }

  componentDidMount() {
    myChart = echarts.init(document.getElementById('graph'));
    myChart.showLoading();
  }

  loadData = (json) => {
    // 基于准备好的dom，初始化echarts实例
    // 绘制图表

    myChart.hideLoading();

    const option = {
      legend: {
        data: ['HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other'],
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
        roam: true,
        data: json.nodes.map((node, idx) => {
          return {
            id: node.id,
            name: node.name,
            symbolSize: node.size,
            x: null,
            y: null,
            draggable: true,
          };
        }),
        // categories: json.categories,
        force: {
          // initLayout: 'circular'
          // repulsion: 20,
          // edgeLength: 20,
          repulsion: 200,
          // gravity: 0.2,
        },
        edges: json.links,
      }],
    };

    myChart.setOption(option);
    myChart.on('click', (params) => {
      if (params.seriesType === 'graph') {
        if (params.dataType === 'edge') {
          // 点击到了 graph 的 edge（边）上。
          emitter.emit('query_partition_detail_ne', 'edge');
        } else {
          // 点击到了 graph 的 node（节点）上。
          console.log(params);
          emitter.emit('query_partition_detail_ne', 'node');
        }
      }
    });
  }

  render() {
    return (
      <div id="graph" style={graphStyle} />
    );
  }
}

export default Graph;
