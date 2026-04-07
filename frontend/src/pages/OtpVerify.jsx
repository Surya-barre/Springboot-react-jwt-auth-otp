import React, { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const OtpVerify = () => {
  const navigate = useNavigate();
  const [otp, setOtp] = useState("");

  const submit = async (e) => {
    e.preventDefault();

    try {
      await API.post("/verify-otp", { otp });

      toast.success("Email verified successfully");

      navigate("/dashboard");
    } catch {
      toast.error("Invalid OTP");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h3 className="auth-title">Verify Email</h3>

        <p className="text-center mb-3">Enter OTP sent to your email</p>

        <form onSubmit={submit}>
          <input
            className="form-control auth-input text-center"
            placeholder="Enter OTP"
            onChange={(e) => setOtp(e.target.value)}
          />

          <button className="auth-btn">Verify</button>
        </form>
      </div>
    </div>
  );
};

export default OtpVerify;
