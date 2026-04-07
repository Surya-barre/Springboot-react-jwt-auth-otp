import React, { useState } from "react";
import API from "../services/api";
import { useNavigate, Link } from "react-router-dom";
import { toast } from "react-toastify";

const Register = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submit = async (e) => {
    e.preventDefault();

    try {
      await API.post("/api/profile/register", form);

      toast.success("Account created successfully!");

      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (err) {
      toast.error("Registration failed");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h3 className="auth-title">Create Account</h3>

        <form onSubmit={submit}>
          <input
            className="form-control auth-input"
            placeholder="Full Name"
            name="name"
            onChange={handleChange}
          />

          <input
            className="form-control auth-input"
            placeholder="Email Id"
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

          <button className="auth-btn">Sign Up</button>

          <p className="text-center mt-3">
            Already have an account?
            <Link to="/login"> Login here</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Register;
