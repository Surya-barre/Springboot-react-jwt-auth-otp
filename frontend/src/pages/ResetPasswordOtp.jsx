import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const  ResetPasswordOtp = () => {
  const [otp, setOtp] = useState("");
  const navigate = useNavigate();

  const submit = (e) => {
    e.preventDefault();

    navigate("/new-password");
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h3 className="auth-title">Email Verify OTP</h3>

        <p className="text-center">Enter the 6-digit code sent to your email</p>

        <form onSubmit={submit}>
          <input
            className="form-control auth-input text-center"
            placeholder="Enter OTP"
            onChange={(e) => setOtp(e.target.value)}
          />

          <button className="auth-btn">Verify email</button>
        </form>
      </div>
    </div>
  );
};

export default ResetPasswordOtp;
