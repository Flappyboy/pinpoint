import React, { Component } from 'react';
import { Table, Pagination } from '@icedesign/base';
import axios from 'axios';
import emitter from '../ev';

// MOCK 数据，实际业务按需进行替换

export default class CustomTable extends Component {
  static displayName = 'CustomTable';

  static propTypes = {};

  static defaultProps = {};

  componentDidMount() {
    // 声明一个自定义事件
    // 在组件装载完成以后
    this.eventEmitter = emitter.addListener('query_partition_detail_ne', this.queryNodeAndEdge);
  }

  // 组件销毁前移除事件监听
  componentWillUnmount() {
    emitter.removeListener('query_partition_detail_ne', this.queryNodeAndEdge);
  }

  constructor(props) {
    super(props);
    this.state = {
      init: true,
      dataSource: [],
      dataType: 'class',
      isLoading: true,
    };
    // this.queryNodeAndEdge('class');
  }

  render() {
    if (this.state.init) {
      return (
        <div />
      );
    }
    if (this.state.dataType === 'class') {
      return (
        <div>
          <Table dataSource={this.state.dataSource} hasBorder={false} isLoading={this.state.isLoading}>
            <Table.Column title="类名" dataIndex="simpleName" />
            <Table.Column title="包名" dataIndex="packageName" />
          </Table>
          <div style={styles.pagination}>
            <Pagination onChange={this.change} />
          </div>
        </div>
      );
    }
    return (
      <div>
        <Table dataSource={this.state.dataSource} hasBorder={false} isLoading={this.state.isLoading}>
          <Table.Column title="调用类" dataIndex="simpleName" />
          <Table.Column title="被调类" dataIndex="simpleName" />
        </Table>
        <div style={styles.pagination}>
          <Pagination onChange={this.change} />
        </div>
      </div>
    );
  }

  queryNodeAndEdge = (type) => {
    let url = '/api/partition-detail-node';
    if (type === 'call') {
      url = '/api/partition-detail-edge';
    }
    this.setState({
      init: false,
      isLoading: true,
    });
    axios.get(url, {
      params: {
        id: 2,
      },
    })
      .then((response) => {
        this.setState({
          dataSource: response.data.data,
          dataType: response.data.type,
          isLoading: false,
        });
      })
      .catch((error) => {
        console.log(error);
        this.setState({
          isLoading: false,
        });
      });
  };
}
const styles = {
  pagination: {
    textAlign: 'right',
    paddingTop: '26px',
  },
};