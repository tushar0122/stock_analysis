import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import stockService from '../../services/stockService';
import financialDataService from '../../services/financialDataService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import { PERIOD_TYPES } from '../../utils/constants';
import { formatNumber } from '../../utils/formatters';
import '../../styles/stockdetail.css';

const StockDetail = () => {
  const { id } = useParams();
  const [stock, setStock] = useState(null);
  const [financialData, setFinancialData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [periodFilter, setPeriodFilter] = useState(PERIOD_TYPES.YEARLY);

  useEffect(() => {
    fetchData();
  }, [id, periodFilter]);

  const fetchData = async () => {
    setLoading(true);
    setError('');

    try {
      const stockResponse = await stockService.getStockById(id);
      setStock(stockResponse.data);

      const finResponse = await financialDataService.getByStockAndPeriodType(id, periodFilter);
      setFinancialData(finResponse.data);
    } catch (err) {
      setError('Failed to fetch stock details');
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loading message="Loading stock details..." />;
  if (!stock) return <ErrorMessage message="Stock not found" />;

  return (
    <div className="stock-detail-container">
      <div className="stock-detail-header">
        <h1>{stock.name}</h1>
        <p className="stock-symbol">{stock.symbol}</p>
      </div>

      <ErrorMessage message={error} onClose={() => setError('')} />

      <div className="stock-info-grid">
        <div className="info-item">
          <label>Sector</label>
          <p>{stock.sector || '-'}</p>
        </div>
        <div className="info-item">
          <label>Industry</label>
          <p>{stock.industry || '-'}</p>
        </div>
        <div className="info-item">
          <label>Exchange</label>
          <p>{stock.exchange || '-'}</p>
        </div>
        <div className="info-item">
          <label>Market Cap</label>
          <p>{stock.marketCap || '-'}</p>
        </div>
      </div>

      <div className="financial-data-section">
        <h2>Financial Data</h2>

        <div className="period-filter">
          <button
            onClick={() => setPeriodFilter(PERIOD_TYPES.YEARLY)}
            className={`filter-btn ${periodFilter === PERIOD_TYPES.YEARLY ? 'active' : ''}`}
          >
            Yearly
          </button>
          <button
            onClick={() => setPeriodFilter(PERIOD_TYPES.QUARTERLY)}
            className={`filter-btn ${periodFilter === PERIOD_TYPES.QUARTERLY ? 'active' : ''}`}
          >
            Quarterly
          </button>
        </div>

        {financialData.length > 0 ? (
          <div className="financial-table">
            <table>
              <thead>
                <tr>
                  <th>Year/Quarter</th>
                  <th>P/E</th>
                  <th>ROE %</th>
                  <th>ROA %</th>
                  <th>Debt/Equity</th>
                  <th>Revenue Growth %</th>
                </tr>
              </thead>
              <tbody>
                {financialData.map((data) => (
                  <tr key={data.id}>
                    <td>
                      {periodFilter === PERIOD_TYPES.YEARLY
                        ? data.year
                        : `Q${data.quarter}-${data.year}`}
                    </td>
                    <td>{formatNumber(data.pe)}</td>
                    <td>{formatNumber(data.roe)}</td>
                    <td>{formatNumber(data.roa)}</td>
                    <td>{formatNumber(data.debtEquity, 3)}</td>
                    <td>{formatNumber(data.revenueGrowth)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <p className="no-data">No financial data available</p>
        )}
      </div>
    </div>
  );
};

export default StockDetail;
