import { useEffect } from "react";

export default function ProtectedRoute({ children }) {
  const token = localStorage.getItem("authToken");

  useEffect(() => {
    if (!token) {
      // different origin → use full redirect
      window.location.replace("http://localhost:8082/login");
    }
  }, [token]);

  if (!token) return null; // 
  return children;
}
