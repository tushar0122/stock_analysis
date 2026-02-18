import React, { useState, useEffect } from 'react';
import stockService from '../../services/stockService';
import financialDataService from '../../services/financialDataService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import { PERIOD_TYPES, QUARTERS, YEARS } from '../../utils/constants';
import '../../styles/dataentry.css';

const ManualEntry = () => {
  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const [formData, setFormData] = useState({
    stockId: '',
    periodType: PERIOD_TYPES.YEARLY,
    year: new Date().getFullYear(),
    quarter: 1,
    pe: '',
    pb: '',
    ps: '',
    peg: '',
    roe: '',
    roa: '',
    roic: '',
    debtEquity: '',
    debtAssets: '',
    interestCoverage: '',
    assetTurnover: '',
    receivablesTurnover: '',
    inventoryTurnover: '',
    revenueGrowth: '',
    earningsGrowth: '',
    bookValueGrowth: ''
  });

  useEffect(() => {
    fetchStocks();
  }, []);

  const fetchStocks = async () => {
    try {
      const response = await stockService.getAllStocks(0, 100);
      setStocks(response.data.content);
    } catch (err) {
      setError('Failed to load stocks');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      const dataToSend = {
        stockId: parseInt(formData.stockId),
        periodType: formData.periodType,
        year: parseInt(formData.year),
        quarter: formData.periodType === PERIOD_TYPES.QUARTERLY ? parseInt(formData.quarter) : null,
        pe: formData.pe ? parseFloat(formData.pe) : null,
        pb: formData.pb ? parseFloat(formData.pb) : null,
        ps: formData.ps ? parseFloat(formData.ps) : null,
        peg: formData.peg ? parseFloat(formData.peg) : null,
        roe: formData.roe ? parseFloat(formData.roe) : null,
        roa: formData.roa ? parseFloat(formData.roa) : null,
        roic: formData.roic ? parseFloat(formData.roic) : null,
        debtEquity: formData.debtEquity ? parseFloat(formData.debtEquity) : null,
        debtAssets: formData.debtAssets ? parseFloat(formData.debtAssets) : null,
        interestCoverage: formData.interestCoverage ? parseFloat(formData.interestCoverage) : null,
        assetTurnover: formData.assetTurnover ? parseFloat(formData.assetTurnover) : null,
        receivablesTurnover: formData.receivablesTurnover ? parseFloat(formData.receivablesTurnover) : null,
        inventoryTurnover: formData.inventoryTurnover ? parseFloat(formData.inventoryTurnover) : null,
        revenueGrowth: formData.revenueGrowth ? parseFloat(formData.revenueGrowth) : null,
        earningsGrowth: formData.earningsGrowth ? parseFloat(formData.earningsGrowth) : null,
        bookValueGrowth: formData.bookValueGrowth ? parseFloat(formData.bookValueGrowth) : null
      };

      await financialDataService.createOrUpdate(dataToSend);
      setSuccess('Financial data saved successfully!');
      setFormData({ ...formData, pe: '', pb: '', ps: '', peg: '', roe: '', roa: '', roic: '', debtEquity: '', debtAssets: '', interestCoverage: '', assetTurnover: '', receivablesTurnover: '', inventoryTurnover: '', revenueGrowth: '', earningsGrowth: '', bookValueGrowth: '' });
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save financial data');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="manual-entry-container">
      <h1>Manual Financial Data Entry</h1>

      <ErrorMessage message={error} onClose={() => setError('')} />
      {success && <div style={{color: 'green', padding: '10px', backgroundColor: '#f0f0f0', borderRadius: '4px'}}>{success}</div>}

      <form onSubmit={handleSubmit} className="entry-form">
        <div className="form-row">
          <div className="form-group">
            <label>Stock *</label>
            <select
              name="stockId"
              value={formData.stockId}
              onChange={handleChange}
              required
            >
              <option value="">Select Stock</option>
              {stocks.map(stock => (
                <option key={stock.id} value={stock.id}>{stock.name} ({stock.symbol})</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Period Type *</label>
            <select name="periodType" value={formData.periodType} onChange={handleChange}>
              <option value={PERIOD_TYPES.YEARLY}>Yearly</option>
              <option value={PERIOD_TYPES.QUARTERLY}>Quarterly</option>
            </select>
          </div>

          <div className="form-group">
            <label>Year *</label>
            <select name="year" value={formData.year} onChange={handleChange}>
              {YEARS.map(year => (
                <option key={year} value={year}>{year}</option>
              ))}
            </select>
          </div>

          {formData.periodType === PERIOD_TYPES.QUARTERLY && (
            <div className="form-group">
              <label>Quarter *</label>
              <select name="quarter" value={formData.quarter} onChange={handleChange}>
                {QUARTERS.map(q => (
                  <option key={q.value} value={q.value}>{q.label}</option>
                ))}
              </select>
            </div>
          )}
        </div>

        <div className="form-section-title">Valuation Ratios</div>
        <div className="form-row">
          <div className="form-group">
            <label>P/E Ratio</label>
            <input type="number" name="pe" value={formData.pe} onChange={handleChange} step="0.01" placeholder="e.g., 25.5" />
          </div>
          <div className="form-group">
            <label>P/B Ratio</label>
            <input type="number" name="pb" value={formData.pb} onChange={handleChange} step="0.01" placeholder="e.g., 4.2" />
          </div>
          <div className="form-group">
            <label>P/S Ratio</label>
            <input type="number" name="ps" value={formData.ps} onChange={handleChange} step="0.01" placeholder="e.g., 3.1" />
          </div>
          <div className="form-group">
            <label>PEG Ratio</label>
            <input type="number" name="peg" value={formData.peg} onChange={handleChange} step="0.01" placeholder="e.g., 2.1" />
          </div>
        </div>

        <div className="form-section-title">Return Ratios</div>
        <div className="form-row">
          <div className="form-group">
            <label>ROE %</label>
            <input type="number" name="roe" value={formData.roe} onChange={handleChange} step="0.01" placeholder="e.g., 18.5" />
          </div>
          <div className="form-group">
            <label>ROA %</label>
            <input type="number" name="roa" value={formData.roa} onChange={handleChange} step="0.01" placeholder="e.g., 12.3" />
          </div>
          <div className="form-group">
            <label>ROIC %</label>
            <input type="number" name="roic" value={formData.roic} onChange={handleChange} step="0.01" placeholder="e.g., 16.8" />
          </div>
        </div>

        <div className="form-section-title">Solvency Ratios</div>
        <div className="form-row">
          <div className="form-group">
            <label>Debt/Equity</label>
            <input type="number" name="debtEquity" value={formData.debtEquity} onChange={handleChange} step="0.01" placeholder="e.g., 0.05" />
          </div>
          <div className="form-group">
            <label>Debt/Assets</label>
            <input type="number" name="debtAssets" value={formData.debtAssets} onChange={handleChange} step="0.01" placeholder="e.g., 0.02" />
          </div>
          <div className="form-group">
            <label>Interest Coverage</label>
            <input type="number" name="interestCoverage" value={formData.interestCoverage} onChange={handleChange} step="0.01" placeholder="e.g., 125" />
          </div>
        </div>

        <div className="form-section-title">Efficiency Ratios</div>
        <div className="form-row">
          <div className="form-group">
            <label>Asset Turnover</label>
            <input type="number" name="assetTurnover" value={formData.assetTurnover} onChange={handleChange} step="0.01" placeholder="e.g., 1.85" />
          </div>
          <div className="form-group">
            <label>Receivables Turnover</label>
            <input type="number" name="receivablesTurnover" value={formData.receivablesTurnover} onChange={handleChange} step="0.01" placeholder="e.g., 4.2" />
          </div>
          <div className="form-group">
            <label>Inventory Turnover</label>
            <input type="number" name="inventoryTurnover" value={formData.inventoryTurnover} onChange={handleChange} step="0.01" placeholder="e.g., 6.1" />
          </div>
        </div>

        <div className="form-section-title">Growth Ratios (%)</div>
        <div className="form-row">
          <div className="form-group">
            <label>Revenue Growth %</label>
            <input type="number" name="revenueGrowth" value={formData.revenueGrowth} onChange={handleChange} step="0.01" placeholder="e.g., 8.5" />
          </div>
          <div className="form-group">
            <label>Earnings Growth %</label>
            <input type="number" name="earningsGrowth" value={formData.earningsGrowth} onChange={handleChange} step="0.01" placeholder="e.g., 9.2" />
          </div>
          <div className="form-group">
            <label>Book Value Growth %</label>
            <input type="number" name="bookValueGrowth" value={formData.bookValueGrowth} onChange={handleChange} step="0.01" placeholder="e.g., 5.3" />
          </div>
        </div>

        <button type="submit" disabled={loading} className="btn-primary">
          {loading ? 'Saving...' : 'Save Financial Data'}
        </button>
      </form>
    </div>
  );
};

export default ManualEntry;
