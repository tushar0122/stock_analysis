import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import stockService from '../../services/stockService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import StockCard from './StockCard';
import AddStockModal from './AddStockModal';
import '../../styles/stocks.css';

const StockList = () => {
  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [isAddStockModalOpen, setIsAddStockModalOpen] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');

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

  const handleAddStockSuccess = () => {
    setSuccessMessage('Stock added successfully!');
    setPage(0);
    fetchStocks();
    setTimeout(() => setSuccessMessage(''), 5000);
  };

  const handleOpenAddStockModal = () => {
    setIsAddStockModalOpen(true);
  };

  const handleCloseAddStockModal = () => {
    setIsAddStockModalOpen(false);
  };

  if (loading) return <Loading message="Loading stocks..." />;

  return (
    <div className="stocks-container">
      <div className="stocks-header">
        <div className="stocks-title-section">
          <h1>Stock List</h1>
          <button 
            className="btn-add-stock"
            onClick={handleOpenAddStockModal}
            title="Add a new stock"
          >
            + Add Stock
          </button>
        </div>
        <input
          type="text"
          placeholder="Search by name or symbol..."
          value={searchTerm}
          onChange={handleSearch}
          className="search-input"
        />
      </div>

      {successMessage && (
        <div className="success-message">
          {successMessage}
          <button 
            onClick={() => setSuccessMessage('')}
            className="close-btn"
          >
            ✕
          </button>
        </div>
      )}

      <ErrorMessage message={error} onClose={() => setError('')} />

      <AddStockModal 
        isOpen={isAddStockModalOpen}
        onClose={handleCloseAddStockModal}
        onSuccess={handleAddStockSuccess}
      />

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
