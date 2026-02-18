-- V4__Add_Advanced_Screener_Support.sql

-- Create screener_rules table for storing individual rules
-- This enables flexible rule building with support for column-to-column comparisons
CREATE TABLE screener_rules (
    id BIGSERIAL PRIMARY KEY,
    saved_screener_id BIGINT REFERENCES saved_screeners(id) ON DELETE CASCADE,
    name VARCHAR(255),
    column_name VARCHAR(100) NOT NULL,
    operator VARCHAR(20) NOT NULL,
    comparison_type VARCHAR(20) NOT NULL, -- VALUE or COLUMN
    value DOUBLE PRECISION,
    comparison_column_name VARCHAR(100),
    comparison_period_offset INTEGER DEFAULT 0, -- For comparing across years (e.g., 0 = same year, -1 = previous year)
    logical_operator VARCHAR(10) DEFAULT 'AND', -- AND or OR with next rule
    position INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create column_metadata table for dynamic discovery of available columns
-- This allows the frontend to dynamically build filter options
CREATE TABLE column_metadata (
    id BIGSERIAL PRIMARY KEY,
    column_name VARCHAR(100) NOT NULL UNIQUE,
    table_name VARCHAR(100) NOT NULL,
    is_numeric BOOLEAN NOT NULL DEFAULT true,
    display_name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50), -- VALUATION, PROFITABILITY, SOLVENCY, EFFICIENCY, GROWTH, OTHER
    min_value DOUBLE PRECISION,
    max_value DOUBLE PRECISION,
    data_type VARCHAR(50), -- DOUBLE, INTEGER, STRING, etc.
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert metadata for all numeric columns in financial_data table
INSERT INTO column_metadata (column_name, table_name, is_numeric, display_name, description, category, data_type)
VALUES
    ('pe', 'financial_data', true, 'P/E Ratio', 'Price to Earnings Ratio', 'VALUATION', 'DOUBLE'),
    ('pb', 'financial_data', true, 'P/B Ratio', 'Price to Book Ratio', 'VALUATION', 'DOUBLE'),
    ('ps', 'financial_data', true, 'P/S Ratio', 'Price to Sales Ratio', 'VALUATION', 'DOUBLE'),
    ('peg', 'financial_data', true, 'PEG Ratio', 'Price/Earnings to Growth Ratio', 'VALUATION', 'DOUBLE'),
    ('ev_ebitda', 'financial_data', true, 'EV/EBITDA', 'Enterprise Value to EBITDA', 'VALUATION', 'DOUBLE'),
    ('dividend_yield', 'financial_data', true, 'Dividend Yield', 'Annual Dividend Yield (%)', 'VALUATION', 'DOUBLE'),
    ('roe', 'financial_data', true, 'ROE', 'Return on Equity (%)', 'PROFITABILITY', 'DOUBLE'),
    ('roa', 'financial_data', true, 'ROA', 'Return on Assets (%)', 'PROFITABILITY', 'DOUBLE'),
    ('roic', 'financial_data', true, 'ROIC', 'Return on Invested Capital (%)', 'PROFITABILITY', 'DOUBLE'),
    ('roce', 'financial_data', true, 'ROCE', 'Return on Capital Employed (%)', 'PROFITABILITY', 'DOUBLE'),
    ('debt_equity', 'financial_data', true, 'Debt to Equity', 'Debt to Equity Ratio', 'SOLVENCY', 'DOUBLE'),
    ('debt_assets', 'financial_data', true, 'Debt to Assets', 'Debt to Assets Ratio', 'SOLVENCY', 'DOUBLE'),
    ('interest_coverage', 'financial_data', true, 'Interest Coverage', 'Interest Coverage Ratio', 'SOLVENCY', 'DOUBLE'),
    ('current_ratio', 'financial_data', true, 'Current Ratio', 'Current Ratio (Short-term Liquidity)', 'SOLVENCY', 'DOUBLE'),
    ('asset_turnover', 'financial_data', true, 'Asset Turnover', 'Asset Turnover Ratio', 'EFFICIENCY', 'DOUBLE'),
    ('receivables_turnover', 'financial_data', true, 'Receivables Turnover', 'Receivables Turnover Ratio', 'EFFICIENCY', 'DOUBLE'),
    ('inventory_turnover', 'financial_data', true, 'Inventory Turnover', 'Inventory Turnover Ratio', 'EFFICIENCY', 'DOUBLE'),
    ('revenue_growth', 'financial_data', true, 'Revenue Growth', 'Revenue Growth (%)', 'GROWTH', 'DOUBLE'),
    ('earnings_growth', 'financial_data', true, 'Earnings Growth', 'Earnings Growth (%)', 'GROWTH', 'DOUBLE'),
    ('book_value_growth', 'financial_data', true, 'Book Value Growth', 'Book Value Growth (%)', 'GROWTH', 'DOUBLE'),
    ('year', 'financial_data', true, 'Year', 'Financial Year', 'OTHER', 'INTEGER'),
    ('quarter', 'financial_data', true, 'Quarter', 'Quarter (1-4)', 'OTHER', 'INTEGER');

-- Create indexes for performance
CREATE INDEX idx_screener_rules_saved_screener ON screener_rules(saved_screener_id);
CREATE INDEX idx_screener_rules_column ON screener_rules(column_name);
CREATE INDEX idx_column_metadata_name ON column_metadata(column_name);
CREATE INDEX idx_column_metadata_numeric ON column_metadata(is_numeric);
