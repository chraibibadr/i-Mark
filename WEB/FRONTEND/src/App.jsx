import { useEffect, useState, useRef } from "react";
import { Notification } from "@mantine/core";
import { useNotifications } from "@mantine/notifications";

import WebCamContainer from "./components/webCamContainer";
import Editor from "./components/Editor";
import "./App.css";

function App() {
  const canvasRef = useRef(null);
  const [points, setPoints] = useState([]);
  const [ctx, setCtx] = useState();
  const [done, setDone] = useState(false);
  const [image, setImage] = useState("");
  const [captured, setCaptured] = useState(false);
  const [editorPos, setEditorPos] = useState({});
  const [annotation, setAnnotation] = useState("");
  const [date, setDate] = useState("");
  const [showToast, setShowToast] = useState(false);
  const [showWarning, setShowWarning] = useState(false);

  const notifications = useNotifications();

  useEffect(() => {
    if (image != "") {
      const context = canvasRef.current.getContext("2d");
      canvasRef.current.height = canvasRef.current.width * 0.85;
      setCtx(context);
    }
  }, [image]);

  const onMouseDown = (e) => {
    if (!done) {
      const rect = canvasRef.current.getBoundingClientRect();

      let x = 0;
      let y = 0;

      if (e.hasOwnProperty("touches")) {
        x = e.touches[0].clientX - rect.left;
        y = e.touches[0].clientY - rect.top;
      } else {
        x = e.clientX - rect.left;
        y = e.clientY - rect.top;
      }

      if (points.length == 0) {
        ctx.lineWidth = 4;
        ctx.beginPath();
        ctx.moveTo(x, y);
        setPoints([...points, { x, y }]);
      }
      //ctx.lineTo(x,y);
    }
  };

  const onMouseUp = (e) => {
    if (!done) {
      const rect = canvasRef.current.getBoundingClientRect();
      let x = 0;
      let y = 0;
      if (e.hasOwnProperty("touches")) {
        x = e.touches[0].clientX - rect.left;
        y = e.touches[0].clientY - rect.top;
      } else {
        x = e.clientX - rect.left;
        y = e.clientY - rect.top;
      }
      console.log("x: " + x + " y: " + y);
      console.log(points);
      if (
        checkClose(
          e.hasOwnProperty("touches") ? e.touches : null,
          e.clientX,
          e.clientY
        )
      ) {
        ctx.closePath();
        ctx.strokeStyle = "#00ff2f";
        ctx.stroke();
        setDone(true);
      } else if (points.length >= 1) {
        ctx.lineTo(x, y);
        ctx.strokeStyle = "#00ff2f";
        ctx.stroke();
        if (
          points[points.length - 1].x != x &&
          points[points.length - 1].y != y
        ) {
          setPoints([...points, { x, y }]);
        }
      }
    }
  };

  const checkClose = (touches, clientX, clientY) => {
    const rect = canvasRef.current.getBoundingClientRect();

    let x = 0;
    let y = 0;
    if (touches != null) {
      x = touches[0].clientX - rect.left;
      y = touches[0].clientY - rect.top;
    } else {
      x = clientX - rect.left;
      y = clientY - rect.top;
    }

    const diffX = Math.round(x) - Math.round(points[0].x);
    const diffY = Math.round(y) - Math.round(points[0].y);
    if (
      diffX < 20 &&
      diffX > -20 &&
      diffY < 20 &&
      diffY > -20 &&
      points.length > 1
    ) {
      if (touches > 0) {
        setEditorPos({ x: touches[0].clientX, y: touches[0].clientY });
      }
      setEditorPos({ x: clientX, y: clientY });
      return true;
    } else {
      return false;
    }
  };

  const getImage = () => {
    if (image === "") {
      setCaptured(true);
    } else {
      setCaptured(false);
      setImage("");
      setPoints([]);
      setDone(false);
      setAnnotation("");
      setEditorPos({});
    }
  };

  const captureImage = (image) => {
    setImage(image);
    setDate(new Date());
    console.log(image);
  };

  const onAnnotationChange = (e) => {
    setAnnotation(e.target.value);
  };
  const onSubmitAnnotation = () => {
    setEditorPos({});
  };

  const onSubmit = () => {
    if (done) {
      const img = new Image();
      img.src = image;

      navigator.geolocation.getCurrentPosition((position) => {
        const payload = {
          file_url: image,
          width: img.width,
          height: img.height,
          date_captured: date,
          gps_location: {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
          },
          objects: [
            {
              annotation: annotation,
              polygons: [
                {
                  coordonnees: points,
                },
              ],
            },
          ],
        };
        setShowToast(true);
        fetch("https://i-mark.herokuapp.com/images", {
          method: "POST",
          body: payload,
        })
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log(payload);
        notifications.showNotification({
          title: "Annotation saved to database succesfully!",
          color: "green",
        });
      });
    } else {
      notifications.showNotification({
        title: "Capture and add annotation to submit!",
        color: "red",
      });
    }
  };

  return (
    <div className="App">
      <div className="container">
        <div className="imageControl">
          <div className="capture">
            <button onClick={getImage} className="btn captureBtn">
              {image != "" ? "Retake" : "Capture"}
            </button>
          </div>
          <div className="submit">
            <button onClick={onSubmit} className="btn submitBtn">
              Submit
            </button>
          </div>
        </div>
        {image === "" ? (
          <WebCamContainer captured={captured} captureImage={captureImage} />
        ) : (
          <canvas
            className="canvas"
            tabIndex="1"
            ref={canvasRef}
            style={{
              color: "black",
              backgroundImage: `url(${image})`,
              backgroundSize: "contain",
              backgroundRepeat: "no-repeat",
            }}
            width="700"
            height="400"
            onMouseDown={onMouseDown}
            onMouseUp={onMouseUp}
            onTouchStart={onMouseDown}
            onTouchEnd={onMouseUp}
          />
        )}
        <Editor
          onAnnotationChange={onAnnotationChange}
          onSubmitAnnotation={onSubmitAnnotation}
          value={annotation}
          pos={editorPos}
        />
        {Object.keys}
      </div>
    </div>
  );
}

export default App;
