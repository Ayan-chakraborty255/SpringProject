import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const LogIn = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(
        "http://localhost:8080/api/user/login",
        { email:email.toLowerCase(), password },
        { withCredentials: true }
      );

      alert("✅ Login successful!");

      // Redirect without token in URL
      window.location.replace("http://localhost:8081/");
    } catch (err) {
      alert(
        "❌ " + (err.response?.data?.message || "Invalid Email or Password")
      );
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div
        className="card shadow-lg p-4"
        style={{ width: "400px", borderRadius: "15px" }}
      >
        <h2 className="text-center fw-bold mb-4">Login</h2>

        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label fw-semibold">Email</label>
            <input
              type="email"
              className="form-control form-control-lg"
              placeholder="Enter your Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              style={{ borderRadius: "12px" }}
            />
          </div>

          <div className="mb-4">
            <label className="form-label fw-semibold">Password</label>
            <input
              type="password"
              className="form-control form-control-lg"
              placeholder="Enter Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              style={{ borderRadius: "12px" }}
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary w-100 py-2 fs-5 fw-semibold shadow"
            style={{ borderRadius: "12px" }}
          >
            Login
          </button>
        </form>

        <p className="text-center mt-3">
          New user?{" "}
          <a href="/signup" className="fw-semibold text-primary">
            Create an account
          </a>
        </p>
      </div>
    </div>
  );
};

export default LogIn;
