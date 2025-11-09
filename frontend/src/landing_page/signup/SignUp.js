import React, { useState } from "react";
import axios from "axios";
import VerifyOtp from "./VerifyOtp";

const Signup = () => {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [sessionId, setSessionId] = useState(null);
  const [showOtpPage, setShowOtpPage] = useState(false);

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(
        "http://localhost:8080/api/user/signup",
        { email:email.toLowerCase(), username, password },
        { headers: { "Content-Type": "application/json" } }
      );

      setSessionId(res.data.sessionId);
      setShowOtpPage(true);
    } catch (err) {
      console.log("Signup Error:", err.response);
      alert(
        err.response?.data?.message ||
          err.response?.data ||
          "Something went wrong"
      );
    }
  };

  if (showOtpPage) {
    return <VerifyOtp email={email} sessionId={sessionId} />;
  }

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div
        className="card shadow-lg p-4"
        style={{ width: "420px", borderRadius: "15px" }}
      >
        <h2 className="text-center mb-4 fw-bold">Create Account</h2>

        <form onSubmit={handleSignup}>
          <div className="mb-3">
            <label className="form-label fw-semibold">Email</label>
            <input
              type="email"
              className="form-control form-control-lg"
              placeholder="Enter your Email"
              value={email}
              onChange={(e) => setEmail(e.target.value.toLowerCase())}
              required
              style={{ borderRadius: "12px" }}
            />
          </div>

          <div className="mb-3">
            <label className="form-label fw-semibold">Username</label>
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Choose a Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              style={{ borderRadius: "12px" }}
            />
          </div>

          <div className="mb-4">
            <label className="form-label fw-semibold">Password</label>
            <input
              type="password"
              className="form-control form-control-lg"
              placeholder="Create a Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              style={{ borderRadius: "12px" }}
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary w-100 py-2 fw-semibold fs-5 shadow"
            style={{ borderRadius: "12px" }}
          >
            Send OTP 🚀
          </button>
        </form>

        <p className="text-center mt-3">
          Already have an account?{" "}
          <a href="/login" className="fw-bold text-primary">
            Login
          </a>
        </p>
      </div>
    </div>
  );
};

export default Signup;
