import React, { Component } from 'react';
import SelectableTable from './components/SelectableTable';
import ColumnForm from './components/ColumnForm';
import TableChartCard from './components/TableChartCard';

export default class Partition extends Component {
  static displayName = 'Partition';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="partition-page">
        <ColumnForm />
        <SelectableTable search={this.props.location.search}/>
        <TableChartCard />
      </div>
    );
  }
}
