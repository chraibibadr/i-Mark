import React, { Component } from "react";

import Webcam from "react-webcam";

import Annotation from "../../../../../src";
import { RectangleSelector } from "../../../../../src/selectors";
import Button from "../../Button";
import { WebcamCapture } from "../../Webcam";

import img from "../../../img2.jpg";

import camera from "../../../camera.png";

import "./index.css";

export default class Multiple extends Component {
  state = {
    type: RectangleSelector.TYPE,
    annotations: [],
    annotation: {},
    image: "",
    webcamRef: React.createRef(null),
    date: "",
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
  onSubmit = () => {
    console.log("submited");
    const img = new Image();
    img.src = this.state.image;

    navigator.geolocation.getCurrentPosition((position) => {
      const payload = {
        file_url: this.state.image,
        width: img.width,
        height: img.height,
        date_captured: this.state.date,
        gps_location: {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        },
        objects: this.state.annotations.map((annotation) => {
          return {
            annotation: annotation.data.text,
            bbox: [
              annotation.geometry.x,
              annotation.geometry.y,
              annotation.geometry.x + annotation.geometry.width,
              annotation.geometry.y + annotation.geometry.height,
            ],
          };
        }),
      };
      console.log(this.state.annotations);
      console.log(payload);
    });
  };

  onChangeType = (e) => {
    this.setState({
      annotation: {},
      type: e.currentTarget.innerHTML,
    });
  };
  capture = (e) => {
    if (this.state.image != "") {
      this.setState({ image: "" });
      this.setState({ annotations: [] });
    } else {
      const imageSrc = this.state.webcamRef.current.getScreenshot();
      this.setState({ image: imageSrc });
      this.setState({ date: new Date() });
    }
    console.log("captured");
  };

  render() {
    const videoConstraints = {
      width: 220,
      height: 200,
      facingMode: "user",
    };

    return (
      <div>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          {this.state.image != "" ? (
            <Button
              onClick={this.capture}
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
                Retake
              </div>
            </Button>
          ) : (
            <Button
              onClick={this.capture}
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
          )}

          <Button onClick={this.onSubmit}>
            <p style={{ fontSize: "15px", margin: "0px" }}>Submit!</p>
          </Button>
        </div>
        {this.state.image == "" ? (
          <div className="webcam-container">
            <div className="webcam-img">
              {this.state.image == "" ? (
                <Webcam
                  audio={false}
                  height={520}
                  ref={this.state.webcamRef}
                  screenshotFormat="image/jpeg"
                  videoConstraints={videoConstraints}
                />
              ) : (
                <img src={this.state.image} />
              )}
            </div>
          </div>
        ) : (
          ""
        )}
        {this.state.image != "" ? (
          <Annotation
            src={this.state.image}
            annotations={this.state.annotations}
            type={this.state.type}
            value={this.state.annotation}
            onChange={this.onChange}
            onSubmit={this.onAdd}
          />
        ) : (
          ""
        )}
      </div>
    );
  }
}
