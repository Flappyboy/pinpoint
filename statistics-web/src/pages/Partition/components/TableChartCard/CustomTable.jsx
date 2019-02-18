import React, { Component } from 'react';
import { Table, Pagination } from '@icedesign/base';
import axios from 'axios';
import emitter from '../ev';
import { queryEdge, queryNode } from '../../../../api';

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
      isLoading: true,
      pageSize: 11,
      total: 0,
    };
    // this.queryNodeAndEdge('class');
  }

  render() {
    if (this.state.init) {
      return (
        <div />
      );
    }
    if (this.state.dataType === 'node') {
      return (
        <div style={styles.table}>
          <Table dataSource={this.state.dataSource} hasBorder={false} isLoading={this.state.isLoading}>
            <Table.Column title="类名" dataIndex="simpleName" />
            <Table.Column title="包名" dataIndex="packageName" />
          </Table>
          <div style={styles.pagination}>
            <Pagination pageSize={this.state.pageSize} total={this.state.total} onChange={this.handleChange} />
          </div>
        </div>
      );
    }
    return (
      <div style={styles.table}>
        <Table dataSource={this.state.dataSource} hasBorder={false} isLoading={this.state.isLoading}>
          <Table.Column title="调用类" dataIndex="caller.simpleName" />
          <Table.Column title="被调类" dataIndex="callee.simpleName" />
        </Table>
        <div style={styles.pagination}>
          <Pagination pageSize={this.state.pageSize} total={this.state.total} onChange={this.handleChange} />
        </div>
      </div>
    );
  }
  handleChange = (current) => {
    console.log(current);
    this.updateList(current);
  }
  updateList = (pageNum) => {
    let call = queryNode;
    if (this.state.dataType === 'edge') {
      call = queryEdge;
    }
    const queryParam = {
      id: this.state.data.id,
      pageSize: this.state.pageSize,
      page: pageNum,
    };
    this.setState({
      init: false,
      isLoading: true,
    });
    call(queryParam).then((response) => {
      this.setState({
        dataSource: response.data.data.list,
        isLoading: false,
        total: response.data.data.total,
      });
    })
      .catch((error) => {
        console.log(error);
        this.setState({
          isLoading: false,
        });
      });
  }
  queryNodeAndEdge = (type, data) => {
    this.setState({
      dataType: type,
      data,
    });
    this.updateList(1, data);
  };
}
const styles = {
  pagination: {
    textAlign: 'right',
    paddingTop: '26px',
  },
  table: {
    margin: 20,
  },
};