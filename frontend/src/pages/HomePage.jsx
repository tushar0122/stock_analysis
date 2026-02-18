import React from 'react';
import { Link } from 'react-router-dom';
import authService from '../services/authService';
import '../styles/home.css';

const HomePage = () => {
  const isAuthenticated = authService.isAuthenticated();

  return (
    <div className="home-page">
      <section className="hero">
        <h1>📈 Stock Analysis & Screening Platform</h1>
        <p>Analyze, filter, and compare stocks using comprehensive financial ratios</p>

        {isAuthenticated ? (
          <div className="hero-actions">
            <Link to="/stocks" className="btn-primary">View Stocks</Link>
            <Link to="/screener" className="btn-secondary">Open Screener</Link>
          </div>
        ) : (
          <div className="hero-actions">
            <Link to="/login" className="btn-primary">Login</Link>
            <Link to="/register" className="btn-secondary">Register</Link>
          </div>
        )}
      </section>

      <section className="features">
        <h2>Features</h2>
        <div className="features-grid">
          <div className="feature">
            <h3>📊 Stock Database</h3>
            <p>Browse and view detailed financial data for thousands of stocks</p>
          </div>
          <div className="feature">
            <h3>🔍 Advanced Screener</h3>
            <p>Filter stocks using multiple financial ratios and conditions</p>
          </div>
          <div className="feature">
            <h3>📈 Financial Ratios</h3>
            <p>Valuation, Return, Solvency, Efficiency, and Growth ratios</p>
          </div>
          <div className="feature">
            <h3>⭐ Watchlists</h3>
            <p>Create and manage custom watchlists of your favorite stocks</p>
          </div>
          <div className="feature">
            <h3>📋 Data Management</h3>
            <p>Manual entry and bulk upload of financial data</p>
          </div>
          <div className="feature">
            <h3>🔐 Secure Access</h3>
            <p>JWT-based authentication with role-based access control</p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;
