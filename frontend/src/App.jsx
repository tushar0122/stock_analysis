import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout/Layout';


// Pages
import HomePage from './pages/HomePage';
import NotFound from './pages/NotFound';

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
        <Route element={<Layout />}>
          <Route path="/" element={<HomePage />} />

          <Route path="/stocks" element={<StockList />} />
          <Route path="/stocks/:id" element={<StockDetail />} />

          <Route
            path="/screener"
            element={<StockScreener />}
          />

          <Route
            path="/watchlist"
            element={<WatchlistView />}
          />

          <Route
            path="/comparison"
            element={<StockComparison />}
          />

          <Route
            path="/data-entry"
            element={<ManualEntry />}
          />

          <Route
            path="/bulk-upload"
            element={<BulkUpload />}
          />

          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
