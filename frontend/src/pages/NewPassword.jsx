import React, { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

const NewPassword = () => {
  const [form, setForm] = useState({
    email: "",
    otp: "",
    newPassword: "",
  });

  const navigate = useNavigate();

  const change = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submit = async (e) => {
    e.preventDefault();

    await API.post("/reset-password", form);

    alert("Password updated");

    navigate("/login");
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h3 className="auth-title">Create New Password</h3>

        <form onSubmit={submit}>
          <input
            className="form-control auth-input"
            placeholder="Email"
            name="email"
            onChange={change}
          />

          <input
            className="form-control auth-input"
            placeholder="OTP"
            name="otp"
            onChange={change}
          />

          <input
            type="password"
            className="form-control auth-input"
            placeholder="New Password"
            name="newPassword"
            onChange={change}
          />

          <button className="auth-btn">Update Password</button>
        </form>
      </div>
    </div>
  );
};

export default NewPassword;
