import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout/Layout';
import ProtectedRoute from './components/Common/ProtectedRoute';

// Pages
import HomePage from './pages/HomePage';
import NotFound from './pages/NotFound';

// Auth Components
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';

// Stock Components
import StockList from './components/Stock/StockList';
import StockDetail from './components/Stock/StockDetail';

// Data Entry Components
import ManualEntry from './components/DataEntry/ManualEntry';
import BulkUpload from './components/DataEntry/BulkUpload';

// Screener Components
import StockScreener from './components/Screener/StockScreener';

// Watchlist Components
import WatchlistView from './components/Watchlist/WatchlistView';

// Comparison Component
import StockComparison from './components/Comparison/StockComparison';

import './styles/App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route element={<Layout />}>
          <Route path="/" element={<HomePage />} />

          <Route path="/stocks" element={<StockList />} />
          <Route path="/stocks/:id" element={<StockDetail />} />

          <Route
            path="/screener"
            element={
              <ProtectedRoute>
                <StockScreener />
              </ProtectedRoute>
            }
          />

          <Route
            path="/watchlist"
            element={
              <ProtectedRoute>
                <WatchlistView />
              </ProtectedRoute>
            }
          />

          <Route
            path="/comparison"
            element={
              <ProtectedRoute>
                <StockComparison />
              </ProtectedRoute>
            }
          />

          <Route
            path="/data-entry"
            element={
              <ProtectedRoute requiredRole="ADMIN">
                <ManualEntry />
              </ProtectedRoute>
            }
          />

          <Route
            path="/bulk-upload"
            element={
              <ProtectedRoute requiredRole="ADMIN">
                <BulkUpload />
              </ProtectedRoute>
            }
          />

          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
