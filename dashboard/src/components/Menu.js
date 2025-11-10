import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

const Menu = () => {
  const [selectedMenu, setSelectedMenu] = useState(0);
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
  const [username, setUsername] = useState("...");

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/user/me", { withCredentials: true })
      .then((res) => {
        // If backend returns only email instead of username
        if (typeof res.data === "string") {
          setUsername(res.data);
        } else {
          setUsername(res.data.username || res.data.email);
        }
      })
      .catch(() => {
        setUsername("Guest");
      });
  }, []);

  const handleMenuClick = (index) => setSelectedMenu(index);
  const handleProfileClick = () =>
    setIsProfileDropdownOpen(!isProfileDropdownOpen);

  const logout = async () => {
    await axios.post(
      "http://localhost:8080/api/user/logout",
      {},
      { withCredentials: true }
    );
    window.location.href = "http://localhost:8081/login";
  };

  const menuClass = "menu";
  const activeMenuClass = "menu selected";

  return (
    <div className="menu-container">
      <img src="logo.png" style={{ width: "50px" }} alt="logo" />

      <div className="menus">
        <ul>
          <li>
            <Link
              to="/"
              onClick={() => handleMenuClick(0)}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <p className={selectedMenu === 0 ? activeMenuClass : menuClass}>
                Dashboard
              </p>
            </Link>
          </li>

          <li>
            <Link
              to="/orders"
              onClick={() => handleMenuClick(1)}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <p className={selectedMenu === 1 ? activeMenuClass : menuClass}>
                Orders
              </p>
            </Link>
          </li>

          <li>
            <Link
              to="/holdings"
              onClick={() => handleMenuClick(2)}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <p className={selectedMenu === 2 ? activeMenuClass : menuClass}>
                Holdings
              </p>
            </Link>
          </li>

          <li>
            <Link
              to="/positions"
              onClick={() => handleMenuClick(3)}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <p className={selectedMenu === 3 ? activeMenuClass : menuClass}>
                Positions
              </p>
            </Link>
          </li>

          <li>
            <Link
              to="/funds"
              onClick={() => handleMenuClick(4)}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <p className={selectedMenu === 4 ? activeMenuClass : menuClass}>
                Funds
              </p>
            </Link>
          </li>

          <li>
            <Link
              to="/apps"
              onClick={() => handleMenuClick(6)}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <p className={selectedMenu === 6 ? activeMenuClass : menuClass}>
                Apps
              </p>
            </Link>
          </li>
        </ul>

        <hr />

        {/* Profile Section */}
        <div className="profile" onClick={handleProfileClick}>
          <div className="avatar">
            {(username || "Guest").slice(0, 2).toUpperCase()}
          </div>
          <p className="username">{username}</p>
        </div>

        {/* Dropdown */}
        {isProfileDropdownOpen && (
          <div className="profile-dropdown">
            <ul>
              <li>Profile</li>
              <li>History</li>
              <li onClick={logout}>Logout</li>
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default Menu;
