import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/navbar.css';

const Navbar = () => {

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          📈 Stock Analysis Platform
        </Link>

        <div className="navbar-menu">
          <Link to="/stocks" className="navbar-link">Stocks</Link>
          <Link to="/screener" className="navbar-link">Screener</Link>
          <Link to="/watchlist" className="navbar-link">Watchlist</Link>
          <Link to="/data-entry" className="navbar-link">Data Entry</Link>
          <Link to="/bulk-upload" className="navbar-link">Bulk Upload</Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
