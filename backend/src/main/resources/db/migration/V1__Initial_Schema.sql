-- V1__Initial_Schema.sql

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stocks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    symbol VARCHAR(20) NOT NULL UNIQUE,
    sector VARCHAR(100),
    industry VARCHAR(100),
    exchange VARCHAR(50),
    market_cap VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE financial_data (
    id SERIAL PRIMARY KEY,
    stock_id BIGINT NOT NULL REFERENCES stocks(id) ON DELETE CASCADE,
    period_type VARCHAR(20) NOT NULL,
    year INTEGER NOT NULL,
    quarter INTEGER,
    pe DECIMAL(10, 2),
    pb DECIMAL(10, 2),
    ps DECIMAL(10, 2),
    peg DECIMAL(10, 2),
    roe DECIMAL(10, 2),
    roa DECIMAL(10, 2),
    roic DECIMAL(10, 2),
    debt_equity DECIMAL(10, 2),
    debt_assets DECIMAL(10, 2),
    interest_coverage DECIMAL(10, 2),
    asset_turnover DECIMAL(10, 2),
    receivables_turnover DECIMAL(10, 2),
    inventory_turnover DECIMAL(10, 2),
    revenue_growth DECIMAL(10, 2),
    earnings_growth DECIMAL(10, 2),
    book_value_growth DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(stock_id, period_type, year, quarter)
);

CREATE TABLE watchlists (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE watchlist_items (
    id SERIAL PRIMARY KEY,
    watchlist_id BIGINT NOT NULL REFERENCES watchlists(id) ON DELETE CASCADE,
    stock_id BIGINT NOT NULL REFERENCES stocks(id) ON DELETE CASCADE,
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(watchlist_id, stock_id)
);

CREATE INDEX idx_financial_data_stock_id ON financial_data(stock_id);
CREATE INDEX idx_financial_data_period_type ON financial_data(period_type);
CREATE INDEX idx_stocks_symbol ON stocks(symbol);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_watchlists_user_id ON watchlists(user_id);
CREATE INDEX idx_watchlist_items_watchlist_id ON watchlist_items(watchlist_id);
CREATE INDEX idx_watchlist_items_stock_id ON watchlist_items(stock_id);
