import React, { Component } from 'react';
import { Table, Pagination } from '@icedesign/base';
import IceContainer from '@icedesign/container';
import emitter from '../ev';

const getMockData = () => {
  const result = [];
  for (let i = 0; i < 10; i += 1) {
    result.push({
      id: 100306660940 + i,
      caller: 'Caller',
      callee: 'Callee',
      count: 50,
    });
  }
  return result;
};

export default class DetailTable extends Component {
  static displayName = 'DetailTable';

  static propTypes = {};

  static defaultProps = {};


  componentDidMount() {
    this.eventEmitter = emitter.addListener('query_statistics_detail', this.queryStatisticsDetail);
  }

  componentWillUnmount() {
    emitter.removeListener('query_statistics_detail', this.queryStatisticsDetail);
  }

  constructor(props) {
    super(props);

    this.state = {
      dataSource: getMockData(),
    };
  }

  queryStatisticsDetail = (param) => {
    console.log('query aha ', param);
  };

  render() {
    return (
      <div id="statistics-detail" className="selectable-table" style={styles.selectableTable}>
        <IceContainer>
          <Table
            dataSource={this.state.dataSource}
            isLoading={this.state.isLoading}
          >
            <Table.Column title="调用类" dataIndex="caller" width={120} />
            <Table.Column title="被调用类" dataIndex="callee" width={120} />
            <Table.Column title="次数" dataIndex="count" width={150} />
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
