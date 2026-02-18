import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import stockService from '../../services/stockService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import StockCard from './StockCard';
import '../../styles/stocks.css';

const StockList = () => {
  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchStocks();
  }, [page, searchTerm]);

  const fetchStocks = async () => {
    setLoading(true);
    setError('');

    try {
      let response;
      if (searchTerm) {
        response = await stockService.searchStocks(searchTerm, page);
      } else {
        response = await stockService.getAllStocks(page);
      }

      setStocks(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (err) {
      setError('Failed to fetch stocks');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
    setPage(0);
  };

  if (loading) return <Loading message="Loading stocks..." />;

  return (
    <div className="stocks-container">
      <div className="stocks-header">
        <h1>Stock List</h1>
        <input
          type="text"
          placeholder="Search by name or symbol..."
          value={searchTerm}
          onChange={handleSearch}
          className="search-input"
        />
      </div>

      <ErrorMessage message={error} onClose={() => setError('')} />

      <div className="stocks-grid">
        {stocks.length > 0 ? (
          stocks.map((stock) => (
            <StockCard key={stock.id} stock={stock} />
          ))
        ) : (
          <p className="no-results">No stocks found</p>
        )}
      </div>

      <div className="pagination">
        <button
          onClick={() => setPage(Math.max(0, page - 1))}
          disabled={page === 0}
          className="btn-pagination"
        >
          Previous
        </button>
        <span>Page {page + 1} of {totalPages}</span>
        <button
          onClick={() => setPage(page + 1)}
          disabled={page >= totalPages - 1}
          className="btn-pagination"
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default StockList;
