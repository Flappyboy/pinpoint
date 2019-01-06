import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Grid } from '@icedesign/base';
import CustomTable from './CustomTable';
import Graph from './Graph';

const { Row, Col } = Grid;

export default class TableChartCard extends Component {
  static displayName = 'TableChartCard';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <IceContainer style={styles.container}>
        <h4 id="partition-detail" style={styles.title}>划分详细信息</h4>
        <Row wrap>
          <Col l="12">
            <Graph />
          </Col>
          <Col l="12">
            <CustomTable />
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
