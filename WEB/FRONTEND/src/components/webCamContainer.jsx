import React, { Component } from 'react'

import Webcam from "react-webcam";

export default class webCamContainer extends Component {
    state = {
        image: "",
        webcamRef: React.createRef(null),
        date: "",
      };
    

    componentDidUpdate(prevProps){
        if(this.props.captured){
            this.props.captureImage(this.state.webcamRef.current.getScreenshot());
        }

    }

  render() {
    const videoConstraints = {
        width: 220,
        height: 200,
        facingMode: "user",
      };
    return (
        <div className="webcam-container">
            <div className="webcam-img">
            
                <Webcam
                audio={false}
                height={525}
                width={700}
                ref={this.state.webcamRef}
                screenshotFormat="image/jpeg"
                />
            
            </div>
      </div>
    )
  }
}
