import { useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function CaptureToken() {
  const { search } = useLocation();

  useEffect(() => {
    const params = new URLSearchParams(search);
    const token = params.get("token");
    if (token) {
      localStorage.setItem("authToken", token);
      window.history.replaceState({}, document.title, "/"); // clean URL
    }
  }, [search]);

  return null;
}
