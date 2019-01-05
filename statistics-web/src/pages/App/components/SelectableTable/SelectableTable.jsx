import React, { Component } from 'react';
import { Table, Button, Icon, Pagination } from '@icedesign/base';
import IceContainer from '@icedesign/container';
import moment from 'moment';
import emitter from '../ev';
import AddDialog from './components/AddDialog';
import DeleteBalloon from './components/DeleteBalloon';
import { BrowserRouter as Router, Route, Link, Redirect, withRouter } from 'react-router-dom';

const getMockData = () => {
  const result = [];
  for (let i = 0; i < 10; i += 1) {
    result.push({
      id: 100306660940 + i,
      app: 'hap',
      createTime: moment(1546256554000).format('YYYY-MM-DD HH:mm:ss'),
      classCount: 100,
      interfaceCount: 20,
      functionCount: 1000,
      interFaceFunctionCount: 150,
      desc: '测试',
      status: true,
    });
  }
  return result;
};

export default class SelectableTable extends Component {
  static displayName = 'SelectableTable';

  static propTypes = {};

  static defaultProps = {};


  componentDidMount() {
    // 声明一个自定义事件
    // 在组件装载完成以后
    this.eventEmitter = emitter.addListener('query_apps', this.queryApps);
  }

  // 组件销毁前移除事件监听
  componentWillUnmount() {
    emitter.removeListener('query_apps', this.queryApps);
  }

  constructor(props) {
    super(props);

    // 表格可以勾选配置项
    this.rowSelection = {
      // 表格发生勾选状态变化时触发
      onChange: (ids) => {
        console.log('ids', ids);
        this.setState({
          selectedRowKeys: ids,
        });
      },
      // 全选表格时触发的回调
      onSelectAll: (selected, records) => {
        console.log('onSelectAll', selected, records);
      },
      // 支持针对特殊行进行定制
      getProps: (record) => {
        return {
          disabled: record.status === false,
        };
      },
    };

    this.state = {
      selectedRowKeys: [],
      dataSource: getMockData(),
      redirectToPartition: false,
    };
  }

  queryApps = (param) => {
    console.log('query aha ', param);
  };

  clearSelectedKeys = () => {
    this.setState({
      selectedRowKeys: [],
    });
  };

  deleteSelectedKeys = () => {
    const { selectedRowKeys } = this.state;
    console.log('delete keys', selectedRowKeys);
  };

  deleteItem = (record) => {
    const { id } = record;
    console.log('delete item', id);

    const data = this.state.dataSource;
    console.log(record);
    record.status = false;
    const index = data.findIndex((item) => {
      return item.id === record.id;
    });
    if (index !== -1) {
      data.splice(index, 1);
    }
    this.setState({
      dataSource: data,
    });
  };

  partition = (record) => {
    const data = this.state.dataSource;

    data.forEach((item) => {
      if (item.id === record.id) {
        // todo
      }
    });

    this.setState({
      redirectToPartitionParam: 'haha',
      redirectToPartition: true,
    });
  };

  addNewItem = (values) => {
    const data = this.state.dataSource;
    console.log(values);
    values.status = false;
    data.splice(0, 0, values);
    this.setState({
      dataSource: data,
    });
  };

  renderOperator = (value, index, record) => {
    if (!record.status) {
      return (
        <div>
          <Icon type="loading" />
        </div>
      );
    }
    return (
      <div>
        <a onClick={this.partition.bind(this, record)}>划分</a>
        <a style={styles.removeBtn} onClick={this.deleteItem.bind(this, record)} >
          删除
        </a>
      </div>
    );
  };

  render() {
    if (this.state.redirectToPartition) {
      return (
        <Redirect to={{ pathname: '/partition', search: `?filter=${this.state.redirectToPartitionParam}` }} />
      );
    }
    return (
      <div className="selectable-table" style={styles.selectableTable}>
        <IceContainer style={styles.IceContainer}>
          <div>
            <AddDialog addNewItem={this.addNewItem} />
            {/* <Button onClick={this.addStatistics} size="small" style={styles.batchBtn}>
              <Icon type="add" />增加
            </Button> */}
            <Button
              onClick={this.deleteSelectedKeys}
              size="small"
              style={styles.batchBtn}
              disabled={!this.state.selectedRowKeys.length}
            >
              <Icon type="ashbin" />删除
            </Button>
            <Button
              onClick={this.clearSelectedKeys}
              size="small"
              style={styles.batchBtn}
            >
              <Icon type="close" />清空选中
            </Button>
          </div>
          <div>
            <a href="/" download>
              <Icon size="small" type="download" /> 导出表格数据到 .csv 文件
            </a>
          </div>
        </IceContainer>
        <IceContainer>
          <Table
            dataSource={this.state.dataSource}
            isLoading={this.state.isLoading}
            rowSelection={{
              ...this.rowSelection,
              selectedRowKeys: this.state.selectedRowKeys,
            }}
          >
            <Table.Column title="编码" dataIndex="id" width={120} />
            <Table.Column title="应用" dataIndex="app" width={120} />
            <Table.Column title="创建日期" dataIndex="createTime" width={150} />
            <Table.Column title="类数" dataIndex="classCount" width={120} />
            <Table.Column title="接口数" dataIndex="interfaceCount" width={120} />
            <Table.Column title="方法数" dataIndex="functionCount" width={120} />
            <Table.Column title="接口方法数" dataIndex="interFaceFunctionCount" width={120} />
            <Table.Column title="描述" dataIndex="desc" width={160} />
            <Table.Column
              title="操作"
              cell={this.renderOperator}
              lock="right"
              width={120}
            />
          </Table>
          <div style={styles.pagination}>
            <Pagination onChange={this.change} />
          </div>
        </IceContainer>
      </div>
    );
  }
}

const styles = {
  batchBtn: {
    marginRight: '10px',
  },
  IceContainer: {
    marginBottom: '20px',
    minHeight: 'auto',
    display: 'flex',
    justifyContent: 'space-between',
  },
  removeBtn: {
    marginLeft: 10,
  },
  pagination: {
    textAlign: 'right',
    paddingTop: '26px',
  },
};
