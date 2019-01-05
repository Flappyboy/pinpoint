import React, { Component } from 'react';
import SelectableTable from './components/SelectableTable';
import ColumnForm from './components/ColumnForm';

export default class App extends Component {
  static displayName = 'App';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="app-page">
        <ColumnForm />
        <SelectableTable />
      </div>
    );
  }
}
