-- V1__Initial_Schema.sql

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stocks (
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
    stock_id BIGINT NOT NULL REFERENCES stocks(id) ON DELETE CASCADE,
    period_type VARCHAR(20) NOT NULL,
    year INTEGER NOT NULL,
    quarter INTEGER,
    pe DOUBLE PRECISION,
    pb DOUBLE PRECISION,
    ps DOUBLE PRECISION,
    peg DOUBLE PRECISION,
    ev_ebitda DOUBLE PRECISION,
    dividend_yield DOUBLE PRECISION,
    roe DOUBLE PRECISION,
    roa DOUBLE PRECISION,
    roic DOUBLE PRECISION,
    roce DOUBLE PRECISION,
    debt_equity DOUBLE PRECISION,
    debt_assets DOUBLE PRECISION,
    interest_coverage DOUBLE PRECISION,
    current_ratio DOUBLE PRECISION,
    asset_turnover DOUBLE PRECISION,
    receivables_turnover DOUBLE PRECISION,
    inventory_turnover DOUBLE PRECISION,
    revenue_growth DOUBLE PRECISION,
    earnings_growth DOUBLE PRECISION,
    book_value_growth DOUBLE PRECISION,
    is_draft BOOLEAN NOT NULL DEFAULT true,
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(stock_id, period_type, year, quarter)
);

CREATE TABLE watchlists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE watchlist_items (
    id BIGSERIAL PRIMARY KEY,
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
