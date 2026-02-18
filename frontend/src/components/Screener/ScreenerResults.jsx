import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/screenerresults.css';

const ScreenerResults = ({ stocks }) => {
  return (
    <div className="screener-results">
      <h2>Results ({stocks.length} stocks)</h2>

      {stocks.length === 0 ? (
        <p className="no-results">No stocks match your criteria</p>
      ) : (
        <div className="results-table">
          <table>
            <thead>
              <tr>
                <th>Symbol</th>
                <th>Name</th>
                <th>Sector</th>
                <th>Industry</th>
                <th>Exchange</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {stocks.map(stock => (
                <tr key={stock.id}>
                  <td><strong>{stock.symbol}</strong></td>
                  <td>{stock.name}</td>
                  <td>{stock.sector || '-'}</td>
                  <td>{stock.industry || '-'}</td>
                  <td>{stock.exchange || '-'}</td>
                  <td>
                    <Link to={`/stocks/${stock.id}`} className="btn-view">
                      View
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ScreenerResults;
