import React, { useState } from "react";
import axios from "axios";

const VerifyOtp = ({ email, sessionId }) => {
  const [otp, setOtp] = useState("");

  const handleVerify = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(
        "http://localhost:8080/api/user/verify",
        {
          email,
          sessionId,
          otp: Number(otp),
        },
        { withCredentials: true } // ✅ REQUIRED NOW
      );

      alert("✅ " + res.data);
      window.location.href = "/login";
    } catch (err) {
      alert("❌ " + (err.response?.data || "Verification failed"));
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div
        className="card shadow-lg p-4 text-center"
        style={{ width: "400px", borderRadius: "15px" }}
      >
        <h2 className="mb-3 fw-bold">Verify OTP</h2>

        <p className="text-muted mb-4">
          OTP has been sent to <br />
          <b className="text-dark">{email}</b>
        </p>

        <form onSubmit={handleVerify}>
          <div className="mb-4">
            <input
              type="number"
              className="form-control form-control-lg text-center fw-semibold"
              placeholder="Enter 6-digit OTP"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              required
              style={{ borderRadius: "12px", letterSpacing: "3px" }}
            />
          </div>

          <button
            type="submit"
            className="btn btn-success w-100 py-2 fw-semibold fs-5 shadow"
            style={{ borderRadius: "12px" }}
          >
            Verify ✅
          </button>
        </form>

        <p className="mt-3 text-muted" style={{ fontSize: "14px" }}>
          Didn’t receive OTP?
          <a href="#" className="text-primary fw-semibold ms-1">
            Resend
          </a>
        </p>
      </div>
    </div>
  );
};

export default VerifyOtp;
