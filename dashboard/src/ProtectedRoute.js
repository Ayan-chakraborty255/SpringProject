import { useEffect, useState } from "react";
import axios from "axios";

export default function ProtectedRoute({ children }) {
  const [checked, setChecked] = useState(false);
  const [authorized, setAuthorized] = useState(false);

  useEffect(() => {
    axios.get("http://localhost:8080/api/user/me", { withCredentials: true })
      .then(() => setAuthorized(true))
      .catch(() => {
        // ✅ If not logged in → go back to Login App
        window.location.href = "http://localhost:8082/login";
      })
      .finally(() => setChecked(true));
  }, []);

  if (!checked) return null;  // Wait until backend check completes
  if (!authorized) return null; // Prevent flickering

  return children;
}
