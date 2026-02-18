import React, { useState, useEffect } from 'react';
import stockService from '../../services/stockService';
import financialDataService from '../../services/financialDataService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import { formatNumber } from '../../utils/formatters';
import '../../styles/comparison.css';

const StockComparison = () => {
  const [stocks, setStocks] = useState([]);
  const [selectedStocks, setSelectedStocks] = useState([]);
  const [comparisonData, setComparisonData] = useState([]);
  const [allStocks, setAllStocks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchAllStocks();
  }, []);

  const fetchAllStocks = async () => {
    try {
      const response = await stockService.getAllStocks(0, 100);
      setAllStocks(response.data.content);
    } catch (err) {
      setError('Failed to load stocks');
    }
  };

  const handleSelectStock = async (stock) => {
    if (selectedStocks.some(s => s.id === stock.id)) {
      setSelectedStocks(selectedStocks.filter(s => s.id !== stock.id));
    } else {
      if (selectedStocks.length < 4) {
        setSelectedStocks([...selectedStocks, stock]);
      } else {
        setError('You can compare up to 4 stocks only');
      }
    }
  };

  const handleCompare = async () => {
    setLoading(true);
    setError('');

    try {
      const dataPromises = selectedStocks.map(stock =>
        financialDataService.getByStockId(stock.id)
      );

      const allData = await Promise.all(dataPromises);
      setComparisonData(allData);
    } catch (err) {
      setError('Failed to load comparison data');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="comparison-container">
      <h1>Compare Stocks</h1>

      <ErrorMessage message={error} onClose={() => setError('')} />

      <div className="comparison-layout">
        <div className="stock-selector">
          <h2>Select Stocks (up to 4)</h2>
          <div className="stock-list">
            {allStocks.map(stock => (
              <label key={stock.id} className="stock-checkbox">
                <input
                  type="checkbox"
                  checked={selectedStocks.some(s => s.id === stock.id)}
                  onChange={() => handleSelectStock(stock)}
                />
                <span>{stock.symbol} - {stock.name}</span>
              </label>
            ))}
          </div>
        </div>

        <div className="selected-stocks">
          <h2>Selected ({selectedStocks.length}/4)</h2>
          <div className="selected-list">
            {selectedStocks.map(stock => (
              <div key={stock.id} className="selected-item">
                <span>{stock.symbol}</span>
                <button onClick={() => handleSelectStock(stock)} className="btn-remove">×</button>
              </div>
            ))}
          </div>
          <button
            onClick={handleCompare}
            disabled={selectedStocks.length === 0 || loading}
            className="btn-primary"
          >
            {loading ? 'Loading...' : 'Compare'}
          </button>
        </div>
      </div>

      {loading && <Loading message="Loading comparison data..." />}

      {comparisonData.length > 0 && (
        <div className="comparison-table">
          <h2>Comparison Results</h2>
          <table>
            <thead>
              <tr>
                <th>Metric</th>
                {selectedStocks.map(stock => (
                  <th key={stock.id}>{stock.symbol}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><strong>Latest P/E</strong></td>
                {comparisonData.map((data, idx) => (
                  <td key={idx}>{data.length > 0 ? formatNumber(data[0].pe) : '-'}</td>
                ))}
              </tr>
              <tr>
                <td><strong>Latest ROE</strong></td>
                {comparisonData.map((data, idx) => (
                  <td key={idx}>{data.length > 0 ? formatNumber(data[0].roe) : '-'}</td>
                ))}
              </tr>
              <tr>
                <td><strong>Latest ROA</strong></td>
                {comparisonData.map((data, idx) => (
                  <td key={idx}>{data.length > 0 ? formatNumber(data[0].roa) : '-'}</td>
                ))}
              </tr>
              <tr>
                <td><strong>Latest Debt/Equity</strong></td>
                {comparisonData.map((data, idx) => (
                  <td key={idx}>{data.length > 0 ? formatNumber(data[0].debtEquity, 3) : '-'}</td>
                ))}
              </tr>
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default StockComparison;
