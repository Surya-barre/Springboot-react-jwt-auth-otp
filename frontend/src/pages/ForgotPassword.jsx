import React, { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();

    await API.post(`/send-reset-otp?email=${email}`);

    navigate("/ResetPasswordOtp");
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h3 className="auth-title">Reset Password</h3>

        <p className="text-center">Enter your registered email address</p>

        <form onSubmit={submit}>
          <input
            className="form-control auth-input"
            placeholder="Enter email"
            onChange={(e) => setEmail(e.target.value)}
          />

          <button className="auth-btn">Submit</button>
        </form>
      </div>
    </div>
  );
};

export default ForgotPassword;
