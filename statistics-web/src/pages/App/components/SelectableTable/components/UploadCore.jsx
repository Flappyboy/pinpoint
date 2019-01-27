import { Upload } from '@icedesign/base';
import React, { Component } from 'react';
import { Base } from '../../../../../api';

const { Core } = Upload;

export default class UploadCore extends Component {
  constructor(props) {
    super(props);

    this.state = {
      disabled: false,
      dragable: true,
    };

    /* eslint-disable */
    ["onDisabledHandler", "onDragableHandler", "onAbortHandler"].map(fn => {
      this[fn] = this[fn].bind(this);
    });
    /* eslint-enable */
  }

  onDisabledHandler() {
    this.setState({
      disabled: !this.state.disabled,
    });
  }

  onDragableHandler() {
    this.setState({
      dragable: !this.state.dragable,
    });
  }

  onAbortHandler() {
    this.refs.inner.abort();
  }

  render() {
    return (
      <div>
        <Core
          ref="inner"
          style={{
            display: 'block',
            textAlign: 'center',
            width: '200px',
            height: '150px',
            lineHeight: '150px',
            border: '1px dashed #aaa',
            borderRadius: '5px',
            fontSize: '12px',
          }}
          action="http://172.19.163.242:8088/api/upload"
          accept=""
          name="file"
          disabled={this.state.disabled}
          multiple
          dragable={this.state.dragable}
          // multipart={{ _token: 'sdj23da' }}
          // headers={{ Authorization: 'user_1' }}
          beforeUpload={beforeUpload}
          onStart={onStart}
          onProgress={onProgress}
          onSuccess={onSuccess}
          onError={onError}
          onAbort={onAbort}
        >
          {this.state.disabled ? '禁止上传' : this.state.dragable ? '点击或者拖拽上传' : '点击上传'}
        </Core>
        {/* <br />
        <div>
          <Button type="primary" onClick={this.onDisabledHandler}>
            切换 disabled 状态
          </Button>&nbsp;
          <Button type="primary" onClick={this.onDragableHandler}>
            切换 dragable 状态
          </Button>&nbsp;
          <Button type="primary" onClick={this.onAbortHandler}>
            中断全部上传
          </Button>
        </div> */}
      </div>
    );
  }
}

function beforeUpload(file) {
  console.log('beforeUpload callback : ', file);
}

function onStart(files) {
  console.log('onStart callback : ', files);
}

function onProgress(e, file) {
  console.log('onProgress callback : ', e, file);
}

function onSuccess(res, file) {
  console.log('onSuccess callback : ', res, file);
}

function onError(err, res, file) {
  console.log('onError callback : ', err, res, file);
}

function onAbort(e, file) {
  console.log('onAbort callback : ', e, file);
}
