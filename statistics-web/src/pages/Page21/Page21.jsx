import React, { Component } from 'react';
import FilterTable from './components/FilterTable';

export default class Page21 extends Component {
  static displayName = 'Page21';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="page21-page">
        <FilterTable />
      </div>
    );
  }
}
