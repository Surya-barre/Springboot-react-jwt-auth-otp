import { Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Register from "./pages/Register";
import ForgotPassword from "./pages/ForgotPassword";
// import OtpVerify from "./pages/OtpVerify";
import NewPassword from "./pages/NewPassword";

import ProtectedRoute from "./components/ProtectedRoute";

import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ResetPasswordOtp from "./pages/ResetPasswordOtp";
import OtpVerify from "./pages/OtpVerify";

function App() {
  return (
    <>
      {/* Toast Notifications */}
      <ToastContainer position="top-right" autoClose={3000} />

      {/* Routes */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot" element={<ForgotPassword />} />
        <Route path="/otp" element={<OtpVerify />} />
        <Route path="/ResetPasswordOtp" element={<ResetPasswordOtp />} />
        <Route path="/new-password" element={<NewPassword />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default App;
