import React, { Component } from 'react';
import SelectableTable from './components/SelectableTable';
import ColumnForm from './components/ColumnForm';

export default class Statistics extends Component {
  static displayName = 'Statistics';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="statistics-page">
        <ColumnForm />
        <SelectableTable />
      </div>
    );
  }
}
