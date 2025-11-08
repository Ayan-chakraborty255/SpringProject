import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./index.css";
import Home from "./components/Home";
import ProtectedRoute from "./ProtectedRoute";
import CaptureToken from "./components/CaptureToken";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <CaptureToken />
      <Routes>

        {/* ✅ Protect the WHOLE dashboard app here */}
        <Route
          path="/*"
          element={
            <ProtectedRoute>
              <CaptureToken/>
              <Home />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
