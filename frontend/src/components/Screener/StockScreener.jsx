import React, { useState } from 'react';
import screenerService from '../../services/screenerService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import FilterBuilder from './FilterBuilder';
import ScreenerResults from './ScreenerResults';
import '../../styles/screener.css';

const StockScreener = () => {
  const [filters, setFilters] = useState({
    operator: 'AND',
    conditions: [{ field: 'roe', comparison: '>', value: '15', period: 'current' }]
  });
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searched, setSearched] = useState(false);

  const handleApplyFilters = async () => {
    setLoading(true);
    setError('');

    try {
      const response = await screenerService.applyFilters(filters);
      setResults(response.data);
      setSearched(true);
    } catch (err) {
      setError('Failed to apply filters');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="screener-container">
      <h1>Stock Screener</h1>

      <ErrorMessage message={error} onClose={() => setError('')} />

      <div className="screener-layout">
        <div className="filter-panel">
          <FilterBuilder filters={filters} setFilters={setFilters} />
          <button onClick={handleApplyFilters} disabled={loading} className="btn-primary btn-large">
            {loading ? 'Filtering...' : 'Apply Filters'}
          </button>
        </div>

        <div className="results-panel">
          {loading && <Loading message="Filtering stocks..." />}
          {searched && <ScreenerResults stocks={results} />}
          {searched && results.length === 0 && !loading && <p className="no-results">No stocks match the selected criteria</p>}
        </div>
      </div>
    </div>
  );
};

export default StockScreener;
