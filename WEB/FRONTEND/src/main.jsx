import React from "react";
import ReactDOM from "react-dom/client";
import { NotificationsProvider } from "@mantine/notifications";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

import App from "./App";
import Register from "./components/Register";
import Login from "./components/Login";
import "./index.css";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <NotificationsProvider>
      <App />
    </NotificationsProvider>
  </React.StrictMode>
);
