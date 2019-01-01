import React, { Component } from 'react';
import { Table, Button, Icon, Pagination } from '@icedesign/base';
import IceContainer from '@icedesign/container';
import moment from 'moment';
import emitter from '../ev';
import AddDialog from './components/AddDialog';
import DeleteBalloon from './components/DeleteBalloon';


const getMockData = () => {
  const result = [];
  for (let i = 0; i < 10; i += 1) {
    result.push({
      id: 100306660940 + i,
      app: 'hap',
      createTime: moment(1546256554000).format('YYYY-MM-DD HH:mm:ss'),
      algorithm: '社区发现',
      statistics: 100306660940,
      desc: '测试',
    });
  }
  return result;
};

// 注意：下载数据的功能，强烈推荐通过接口实现数据输出，并下载
// 因为这样可以有下载鉴权和日志记录，包括当前能不能下载，以及谁下载了什么

export default class SelectableTable extends Component {
  static displayName = 'SelectableTable';

  static propTypes = {};

  static defaultProps = {};


  componentDidMount() {
    // 声明一个自定义事件
    // 在组件装载完成以后
    this.eventEmitter = emitter.addListener('query_statistics', this.queryStatistics);
  }

  // 组件销毁前移除事件监听
  componentWillUnmount() {
    emitter.removeListener('query_statistics', this.queryStatistics);
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
          disabled: record.id === 100306660941,
        };
      },
    };

    this.state = {
      selectedRowKeys: [],
      dataSource: getMockData(),
    };
  }

  queryStatistics = (param) => {
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
  };

  partition = (record) => {
    const data = this.state.dataSource;

    data.forEach((item) => {
      if (item.id === record.id) {
        // todo
      }
    });
  };

  renderOperator = (value, index, record) => {
    return (
      <div>
        <a onClick={this.partition.bind(this, record)}>查看</a>
        <a style={styles.removeBtn} onClick={this.deleteItem.bind(this, record)} >
          删除
        </a>
      </div>
    );
  };

  render() {
    return (
      <div className="selectable-table" style={styles.selectableTable}>
        <IceContainer style={styles.IceContainer}>
          <div>
            <AddDialog addNewStatistics={this.addNewStatistics} />
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
            <Table.Column title="统计编码" dataIndex="statistics" width={120} />
            <Table.Column title="创建日期" dataIndex="createTime" width={150} />
            <Table.Column title="算法" dataIndex="algorithm" width={150} />
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
