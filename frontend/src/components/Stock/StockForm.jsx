import React, { useState } from 'react';
import stockService from '../../services/stockService';
import ErrorMessage from '../Common/ErrorMessage';
import '../../styles/form.css';

const StockForm = ({ onSuccess, stock = null }) => {
  const [formData, setFormData] = useState(stock || {
    name: '',
    symbol: '',
    sector: '',
    industry: '',
    exchange: '',
    marketCap: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      if (stock?.id) {
        await stockService.updateStock(stock.id, formData);
      } else {
        await stockService.createStock(formData);
      }
      onSuccess();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save stock');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="stock-form">
      <ErrorMessage message={error} onClose={() => setError('')} />

      <div className="form-group">
        <label>Stock Name *</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
          placeholder="e.g., Infosys Limited"
        />
      </div>

      <div className="form-group">
        <label>Symbol *</label>
        <input
          type="text"
          name="symbol"
          value={formData.symbol}
          onChange={handleChange}
          required
          placeholder="e.g., INFY"
          disabled={!!stock}
        />
      </div>

      <div className="form-group">
        <label>Sector</label>
        <input
          type="text"
          name="sector"
          value={formData.sector}
          onChange={handleChange}
          placeholder="e.g., Information Technology"
        />
      </div>

      <div className="form-group">
        <label>Industry</label>
        <input
          type="text"
          name="industry"
          value={formData.industry}
          onChange={handleChange}
          placeholder="e.g., IT Services"
        />
      </div>

      <div className="form-group">
        <label>Exchange</label>
        <input
          type="text"
          name="exchange"
          value={formData.exchange}
          onChange={handleChange}
          placeholder="e.g., NSE"
        />
      </div>

      <div className="form-group">
        <label>Market Cap</label>
        <input
          type="text"
          name="marketCap"
          value={formData.marketCap}
          onChange={handleChange}
          placeholder="e.g., 750000 Cr"
        />
      </div>

      <button type="submit" disabled={loading} className="btn-primary">
        {loading ? 'Saving...' : (stock ? 'Update Stock' : 'Create Stock')}
      </button>
    </form>
  );
};

export default StockForm;
