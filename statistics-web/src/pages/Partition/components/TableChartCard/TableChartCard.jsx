import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Grid } from '@icedesign/base';
import CustomTable from './CustomTable';
import Graph from './Graph';
import emitter from '../ev';
import { queryPartitionDetail } from '../../../../api';

const { Row, Col } = Grid;

export default class TableChartCard extends Component {
  static displayName = 'TableChartCard';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {
      show: false,
      isLoading: true,
    };
  }

  componentDidMount() {
    this.eventEmitter = emitter.addListener('query_partition_detail', this.queryPartitionDetails);
  }
  // 组件销毁前移除事件监听
  componentWillUnmount() {
    emitter.removeListener('query_partition_detail', this.queryPartitionDetails);
  }

  queryPartitionDetails = (param) => {
    this.setState({
      show: true,
      isLoading: true,
    });
    console.log(param);
    queryPartitionDetail(param).then((response) => {
      console.log(response.data.data);
      this.setState({
        data: response.data.data,
        isLoading: false,
      });
      // 找到锚点
      const anchorElement = document.getElementById('partition-detail');
      // 如果对应id的锚点存在，就跳转到锚点
      if (anchorElement) { anchorElement.scrollIntoView(); }
    })
      .catch((error) => {
        console.log(error);
      });
  }

  render() {
    if (!this.state.show) {
      return (<div id="partition-detail" />);
    }
    return (
      <IceContainer style={styles.container}>
        <h4 id="partition-detail" style={styles.title}>划分详细信息</h4>
        <Row wrap>
          <Col l="12">
            <Graph isLoading={this.state.isLoading} data={this.state.data} />
          </Col>
          <Col l="12">
            <CustomTable isLoading={this.state.isLoading} />
          </Col>
        </Row>
      </IceContainer>
    );
  }
}

const styles = {
  container: {
    padding: '0',
  },
  title: {
    margin: '0 0 20px',
    padding: '15px 20px',
    fonSize: '16px',
    color: 'rgba(0, 0, 0, 0.85)',
    fontWeight: '500',
    borderBottom: '1px solid #f0f0f0',
  },
};
