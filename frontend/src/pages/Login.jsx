import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import API from "../services/api";

const Login = () => {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submit = async (e) => {
    e.preventDefault();

    try {
      await API.post("/login", form);
      navigate("/dashboard");
    } catch {
      alert("Invalid credentials");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h3 className="auth-title">Login</h3>

        <form onSubmit={submit}>
          <input
            className="form-control auth-input"
            placeholder="Enter email"
            name="email"
            onChange={handleChange}
          />

          <input
            type="password"
            className="form-control auth-input"
            placeholder="Password"
            name="password"
            onChange={handleChange}
          />

          <p className="auth-link">
            <Link to="/forgot">Forgot password?</Link>
          </p>

          <button className="auth-btn">Login</button>

          <p className="text-center mt-3">
            Don't have an account?
            <Link to="/register"> Sign up</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Login;
