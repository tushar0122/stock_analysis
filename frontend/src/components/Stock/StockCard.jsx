import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/stockcard.css';

const StockCard = ({ stock }) => {
  return (
    <Link to={`/stocks/${stock.id}`} className="stock-card-link">
      <div className="stock-card">
        <div className="stock-card-header">
          <h3>{stock.symbol}</h3>
          <p className="stock-card-name">{stock.name}</p>
        </div>
        <div className="stock-card-info">
          {stock.sector && <p><strong>Sector:</strong> {stock.sector}</p>}
          {stock.industry && <p><strong>Industry:</strong> {stock.industry}</p>}
          {stock.exchange && <p><strong>Exchange:</strong> {stock.exchange}</p>}
          {stock.marketCap && <p><strong>Market Cap:</strong> {stock.marketCap}</p>}
        </div>
      </div>
    </Link>
  );
};

export default StockCard;
