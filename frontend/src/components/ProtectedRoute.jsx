import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import API from "../services/api";

const ProtectedRoute = ({ children }) => {
  const [auth, setAuth] = useState(null);

  useEffect(() => {
    API.get("/isAuthenticated")
      .then((res) => {
        setAuth(res.data);
      })
      .catch(() => {
        setAuth(false);
      });
  }, []);

  if (auth === null) {
    return <p>Loading...</p>;
  }

  if (auth === false) {
    return <Navigate to="/" />;
  }

  return children;
};

export default ProtectedRoute;
