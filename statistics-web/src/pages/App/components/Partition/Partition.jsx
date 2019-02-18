import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Input, Button, Select, Checkbox, Form, NumberPicker, SplitButton, Table, Pagination } from '@alifd/next';
import { queryStatisticsList, queryStatistics, delStatistics, addStatistics, addPartition } from '../../../../api';
import { BrowserRouter as Router, Route, Link, Redirect, withRouter } from 'react-router-dom';
import moment from 'moment';

import emitter from '../ev';

const CheckboxGroup = Checkbox.Group;
const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { xxs: 8, s: 3, l: 3 },
  wrapperCol: { s: 12, l: 10 }
};

const DATE_FORMAT = 'YYYY-MM-DD HH:mm:ss';

export default class Partition extends Component {
  static displayName = 'Partition';

  constructor(props) {
    super(props);
    this.state = {
      app: props.app,
      dynamic: {
        dataSource: [],
        isLoading: true,
        pageSize: 10,
        total: 0,
        selected: {
          id: null,
        },
      },
      form: {

      },
    };
  }

  componentWillReceiveProps(nextProps) {
    this.state = {
      app: nextProps.app,
      dynamic: {
        dataSource: [],
        isLoading: true,
        pageSize: 10,
        total: 0,
        selected: {
          id: null,
        },
      },
      form: {
        dynamic: undefined,
      },
    };
    this.setState({});

    this.componentDidMount();
  }

  reset = () => {
  }

  formChange = (values, field) => {
    console.log(values, field)
  }

  submit = (values, errors) => {
    console.log('error', errors, 'value', values);
    if (!errors) {
      const param = {
        appid: this.state.app.id,
        algorithmsid: 1,
        dynamicanalysisinfoid: values.dynamic,
        type: 0,
      };
      addPartition(param).then((response) => {
        console.log(response.data.data);
        this.setState({
          // redirectToPartitionParam: id,
          redirectToPartition: true,
        });
      })
        .catch((error) => {
          console.log(error);
        });
    } else {
      // 处理表单报错
    }
  };

  preprocess = (dataList) => {
    dataList.forEach(data => {
      data.createTime = moment(data.createdat).format(DATE_FORMAT);
      if (data.starttine)
        data.startTime = moment(data.starttine).format(DATE_FORMAT);
      if (data.endtime)
        data.endTime = moment(data.endtime).format(DATE_FORMAT);
      if (!('status' in data)) {
        data.status = true;
      }
    });
  }

  dynamicUpdateList = (pageNum, queryParam) => {
    // const queryParam = {
    //   pageSize: this.state.pageSize,
    //   page: pageNum,
    // };
    if (!queryParam) {
      queryParam = {};
    }
    if (this.state.app) {
      queryParam.appid = this.state.app.id;
    }
    queryParam.pageSize = this.state.pageSize;
    queryParam.page = pageNum;

    this.state.dynamic.isLoading = true;
    this.setState({
      dynamic: this.state.dynamic,
    });
    queryStatisticsList(queryParam).then((response) => {
      console.log(response.data.data);
      this.preprocess(response.data.data.list);
      this.state.dynamic.dataSource = response.data.data.list;
      this.state.dynamic.isLoading = false;
      this.state.dynamic.total = response.data.data.total;
      this.setState({
        dynamic: this.state.dynamic,
      });
    })
      .catch((error) => {
        console.log(error);
      });
  }

  dynamicHhandleChange = (current) => {
    console.log(current);
    this.updateList(current);
  }

  setRowProps = (record, index) => {
    if (this.state.dynamic.selected != null) {
      if (record.id === this.state.dynamic.selected.id) {
        return propsConf;
      }
    }
  }

  componentDidMount() {
    // 找到锚点
    const anchorElement = document.getElementById('micro-partition');
    // 如果对应id的锚点存在，就跳转到锚点
    if (anchorElement) { anchorElement.scrollIntoView(); }

    this.dynamicUpdateList(1);
  }

  selectDynamic = (record, index, e) => {
    console.log(`select : ${record.id} ${this.state.dynamic.dataSource[index].id}`);
    this.state.dynamic.selected = record;
    this.state.form.dynamic = record.id;
    this.setState({});
  }


  render() {
    if (this.state.redirectToPartition) {
      return (
        <Redirect to={{ pathname: '/partition', search: `?addAppId=${this.state.app.id}` }} />
      );
    }
    const title = `${this.state.app.name} 微服务划分`;
    return (
      <div id="micro-partition" className="grouped-form">
        <IceContainer title={title} style={styles.container}>
          <Form onChange={this.formChange} value={this.state.form}>
            <div>
              <div style={styles.subForm}>
                <h3 style={styles.formTitle}>动态分析数据</h3>
                <div>
                  <FormItem label="已选择  " {...formItemLayout}>
                    <Input name="dynamic" readOnly />
                  </FormItem>
                  <Table dataSource={this.state.dynamic.dataSource} loading={this.state.dynamic.isLoading} onRowClick={this.selectDynamic} getRowProps={this.setRowProps} getCellProps={this.setCellProps}>
                    <Table.Column title="编码" dataIndex="id" width={120} />
                    <Table.Column title="应用" dataIndex="appName" width={120} />
                    <Table.Column title="创建日期" dataIndex="createTime" width={150} />
                    <Table.Column title="开始日期" dataIndex="startTime" width={150} />
                    <Table.Column title="结束日期" dataIndex="endTime" width={160} />
                    <Table.Column title="描述" dataIndex="desc" width={160} />
                  </Table>
                  <div style={styles.pagination}>
                    <Pagination pageSize={this.state.dynamic.pageSize} total={this.state.dynamic.total} onChange={this.dynamicHhandleChange} />
                  </div>
                </div>
              </div>

              <div style={styles.subForm}>
                <h3 style={styles.formTitle}>Git数据</h3>
                <div>
                  <FormItem label="因素选择:  " {...formItemLayout}>
                    <CheckboxGroup
                      name="deliveryType"
                      dataSource={[
                        { label: '逻辑耦合', value: 'logic' },
                        { label: '修改频率', value: 'modify' },
                        { label: '可靠性', value: 'reliability' },
                      ]}
                    />
                  </FormItem>
                  {/* <FormItem label="配送费用：" {...formItemLayout} required requiredMessage="请选择物流公司">
                    <NumberPicker
                      style={{ width: 120 }}
                      name="deliveryFee"
                      placeholder="请输入配送费用"
                    />
                  </FormItem> */}
                </div>
              </div>

              <FormItem label=" " {...formItemLayout}>
                <Form.Submit type="primary" validate onClick={this.submit}>
                  立即划分
                  </Form.Submit>
                <Form.Reset style={styles.resetBtn} onClick={this.reset}>
                  重置
                  </Form.Reset>
              </FormItem>
            </div>
          </Form>
        </IceContainer>
      </div>
    );
  }
}

const styles = {
  container: {
    paddingBottom: 0,
  },
  subForm: {
    marginBottom: '20px',
  },
  formTitle: {
    margin: '0 0 20px',
    paddingBottom: '10px',
    fontSize: '14px',
    borderBottom: '1px solid #eee',
  },
  formItem: {
    height: '28px',
    lineHeight: '28px',
    marginBottom: '25px',
  },
  formLabel: {
    textAlign: 'right',
  },
  btns: {
    margin: '25px 0',
  },
  resetBtn: {
    marginLeft: '20px',
  },
};
const propsConf = {
  className: 'next-myclass',
  style: { background: 'gray', color: 'white' },
  // onDoubleClick: () => {
  //   console.log('doubleClicked');
  // }
};
