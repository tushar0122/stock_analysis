import React, { useState } from 'react';
import stockService from '../../services/stockService';
import ErrorMessage from '../Common/ErrorMessage';
import '../../styles/form.css';

const AddStock = ({ onSuccess, onClose }) => {
  const [formData, setFormData] = useState({
    name: '',
    symbol: '',
    sector: '',
    industry: '',
    exchange: '',
    marketCap: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [touched, setTouched] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleBlur = (e) => {
    const { name } = e.target;
    setTouched(prev => ({ ...prev, [name]: true }));
  };

  const validateForm = () => {
    if (!formData.name.trim()) {
      setError('Stock name is required');
      return false;
    }
    if (!formData.symbol.trim()) {
      setError('Symbol is required');
      return false;
    }
    if (formData.symbol.trim().length > 10) {
      setError('Symbol must be 10 characters or less');
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!validateForm()) {
      return;
    }

    setLoading(true);

    try {
      // Convert empty strings to undefined for optional fields
      const submitData = {
        name: formData.name.trim(),
        symbol: formData.symbol.trim().toUpperCase(),
        sector: formData.sector.trim() || undefined,
        industry: formData.industry.trim() || undefined,
        exchange: formData.exchange.trim() || undefined,
        marketCap: formData.marketCap.trim() || undefined
      };

      await stockService.createStock(submitData);
      
      // Reset form and notify success
      setFormData({
        name: '',
        symbol: '',
        sector: '',
        industry: '',
        exchange: '',
        marketCap: ''
      });
      setTouched({});
      
      if (onSuccess) {
        onSuccess();
      }
      if (onClose) {
        onClose();
      }
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Failed to add stock';
      setError(errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="add-stock-form">
      {error && (
        <ErrorMessage message={error} onClose={() => setError('')} />
      )}

      <div className="form-group">
        <label htmlFor="name">
          Stock Name <span className="required">*</span>
        </label>
        <input
          id="name"
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          onBlur={handleBlur}
          placeholder="e.g., Infosys Limited"
          className={touched.name && !formData.name ? 'input-error' : ''}
          maxLength="100"
        />
        {touched.name && !formData.name && (
          <span className="error-text">Stock name is required</span>
        )}
      </div>

      <div className="form-group">
        <label htmlFor="symbol">
          Symbol <span className="required">*</span>
        </label>
        <input
          id="symbol"
          type="text"
          name="symbol"
          value={formData.symbol}
          onChange={handleChange}
          onBlur={handleBlur}
          placeholder="e.g., INFY"
          className={touched.symbol && !formData.symbol ? 'input-error' : ''}
          maxLength="10"
          style={{ textTransform: 'uppercase' }}
        />
        {touched.symbol && !formData.symbol && (
          <span className="error-text">Symbol is required</span>
        )}
        {formData.symbol && formData.symbol.length > 10 && (
          <span className="error-text">Symbol must be 10 characters or less</span>
        )}
      </div>

      <div className="form-row">
        <div className="form-group">
          <label htmlFor="sector">Sector</label>
          <input
            id="sector"
            type="text"
            name="sector"
            value={formData.sector}
            onChange={handleChange}
            placeholder="e.g., Information Technology"
            maxLength="50"
          />
        </div>

        <div className="form-group">
          <label htmlFor="industry">Industry</label>
          <input
            id="industry"
            type="text"
            name="industry"
            value={formData.industry}
            onChange={handleChange}
            placeholder="e.g., IT Services"
            maxLength="50"
          />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label htmlFor="exchange">Exchange</label>
          <input
            id="exchange"
            type="text"
            name="exchange"
            value={formData.exchange}
            onChange={handleChange}
            placeholder="e.g., NSE or BSE"
            maxLength="20"
          />
        </div>

        <div className="form-group">
          <label htmlFor="marketCap">Market Cap</label>
          <input
            id="marketCap"
            type="text"
            name="marketCap"
            value={formData.marketCap}
            onChange={handleChange}
            placeholder="e.g., 750000 Cr"
            maxLength="50"
          />
        </div>
      </div>

      <div className="form-actions">
        <button 
          type="submit" 
          disabled={loading} 
          className="btn-primary"
        >
          {loading ? 'Adding Stock...' : 'Add Stock'}
        </button>
        {onClose && (
          <button 
            type="button" 
            onClick={onClose}
            disabled={loading}
            className="btn-secondary"
          >
            Cancel
          </button>
        )}
      </div>
    </form>
  );
};

export default AddStock;
