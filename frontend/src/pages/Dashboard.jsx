import React, { useEffect, useState } from "react";
import API from "../services/api";
import hero from "../assets/react.svg";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const Dashboard = () => {
  const navigate = useNavigate();

  const [profile, setProfile] = useState({});
  const [verified, setVerified] = useState(false);
  const [showMenu, setShowMenu] = useState(false);

  useEffect(() => {
    API.get("/api/profile/profile2").then((res) => {
      setProfile(res.data);
      setVerified(res.data.isAccountVerified);
    });
  }, []);

  const verifyEmail = async () => {
    try {
      await API.post("/send-otp");

      toast.success("OTP sent to your email");

      navigate("/otp");
    } catch (err) {
      toast.error("Failed to send OTP");
    }
  };

  const logout = async () => {
    await API.post("/logout");

    navigate("/");
    window.location.reload();
  };

  return (
    <div className="home-container">
      <nav className="navbar px-5 py-3">
        <h3 className="logo">
          <span className="logo-icon">✔</span> Authify
        </h3>

        <div style={{ position: "relative" }}>
          <div className="avatar" onClick={() => setShowMenu(!showMenu)}>
            {profile.name?.charAt(0)}
          </div>

          {showMenu && (
            <div className="dropdown-menu-custom">
              {!verified && (
                <p onClick={verifyEmail} className="menu-item">
                  Verify Email
                </p>
              )}

              <p onClick={logout} className="menu-item logout">
                Logout
              </p>
            </div>
          )}
        </div>
      </nav>

      <div className="hero text-center">
        <img src={hero} className="hero-img" />

        <p className="sub-title">Hey {profile.name} 👋</p>

        <h1 className="main-title">Welcome to our product</h1>

        <p className="description">
          Let's start with a quick product tour and you can setup the
          authentication in no time!
        </p>

        <button className="start-btn">Get Started</button>
      </div>
    </div>
  );
};

export default Dashboard;
