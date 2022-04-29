import React, { Component } from "react";
import Annotation from "../../../../../src";
import {
  PointSelector,
  RectangleSelector,
  OvalSelector,
} from "../../../../../src/selectors";

import Button from "../../Button";

import img from "../../../img2.jpg";

import camera from "../../../camera.png";

export default class Multiple extends Component {
  state = {
    type: RectangleSelector.TYPE,
    annotations: [],
    annotation: {},
  };

  onChange = (annotation) => {
    this.setState({ annotation });
  };

  onAdd = (annotation) => {
    const { geometry, data } = annotation;

    this.setState({
      annotation: {},
      annotations: this.state.annotations.concat({
        geometry,
        data: {
          ...data,
          id: Math.random(),
        },
      }),
    });
  };

  onChangeType = (e) => {
    this.setState({
      annotation: {},
      type: e.currentTarget.innerHTML,
    });
  };

  render() {
    return (
      <div>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <Button
            onClick={this.onChangeType}
            active={RectangleSelector.TYPE === this.state.type}
          >
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <img
                style={{ width: "20px", marginRight: "10px" }}
                src={camera}
              />
              Capture
            </div>
          </Button>
          <Button>
            <p style={{ fontSize: "15px", margin: "0px" }}>Submit!</p>
          </Button>
        </div>

        <Annotation
          src={img}
          annotations={this.state.annotations}
          type={this.state.type}
          value={this.state.annotation}
          onChange={this.onChange}
          onSubmit={this.onAdd}
        />
      </div>
    );
  }
}
