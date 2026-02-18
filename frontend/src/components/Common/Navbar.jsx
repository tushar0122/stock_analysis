import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import authService from '../services/authService';
import '../styles/navbar.css';

const Navbar = () => {
  const navigate = useNavigate();
  const user = authService.getUser();
  const isAuthenticated = authService.isAuthenticated();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          📈 Stock Analysis Platform
        </Link>

        <div className="navbar-menu">
          {isAuthenticated ? (
            <>
              <Link to="/stocks" className="navbar-link">Stocks</Link>
              <Link to="/screener" className="navbar-link">Screener</Link>
              <Link to="/watchlist" className="navbar-link">Watchlist</Link>
              {user?.role === 'ADMIN' && (
                <>
                  <Link to="/data-entry" className="navbar-link">Data Entry</Link>
                  <Link to="/bulk-upload" className="navbar-link">Bulk Upload</Link>
                </>
              )}
              <span className="navbar-user">
                {user?.username}
                {user?.role === 'ADMIN' && <span className="badge">ADMIN</span>}
              </span>
              <button onClick={handleLogout} className="navbar-logout">Logout</button>
            </>
          ) : (
            <>
              <Link to="/login" className="navbar-link">Login</Link>
              <Link to="/register" className="navbar-link">Register</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
