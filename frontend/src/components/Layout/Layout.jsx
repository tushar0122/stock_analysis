import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '../Common/Navbar';
import '../../styles/layout.css';

const Layout = () => {
  return (
    <div className="layout">
      <Navbar />

      <main className="main-content">
        <Outlet />
      </main>

      <footer className="footer">
        <p>&copy; 2024 Stock Analysis Platform. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Layout;
