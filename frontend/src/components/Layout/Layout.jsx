import React from 'react';
import Navbar from '../Common/Navbar';
import '../styles/layout.css';

const Layout = ({ children }) => {
  return (
    <div className="layout">
      <Navbar />
      <main className="main-content">
        {children}
      </main>
      <footer className="footer">
        <p>&copy; 2024 Stock Analysis Platform. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Layout;
