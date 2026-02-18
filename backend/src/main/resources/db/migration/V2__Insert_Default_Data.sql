-- V2__Insert_Default_Data.sql

-- Insert default users
INSERT INTO users (username, email, password, role, active) VALUES
('admin', 'admin@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy73B86', 'ADMIN', true),
('user', 'user@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy73B86', 'USER', true);

-- Insert sample stocks
INSERT INTO stocks (name, symbol, sector, industry, exchange, market_cap) VALUES
('Infosys Limited', 'INFY', 'Information Technology', 'IT Services', 'NSE', '750000 Cr'),
('Tata Consultancy Services', 'TCS', 'Information Technology', 'IT Services', 'NSE', '1300000 Cr'),
('HDFC Bank', 'HDFCBANK', 'Financials', 'Banking', 'NSE', '1400000 Cr'),
('State Bank of India', 'SBIN', 'Financials', 'Banking', 'NSE', '650000 Cr'),
('Reliance Industries', 'RELIANCE', 'Energy', 'Oil & Gas', 'NSE', '2000000 Cr'),
('Hindustan Unilever', 'HINDUNILVR', 'Consumer Staples', 'FMCG', 'NSE', '550000 Cr'),
('Wipro', 'WIPRO', 'Information Technology', 'IT Services', 'NSE', '380000 Cr'),
('Maruti Suzuki', 'MARUTI', 'Consumer Discretionary', 'Automobiles', 'NSE', '180000 Cr'),
('Bharti Airtel', 'BHARTIARTL', 'Telecommunications', 'Telecom', 'NSE', '450000 Cr'),
('ITC', 'ITC', 'Consumer Staples', 'Diversified', 'NSE', '320000 Cr');

-- Insert sample financial data for INFY
INSERT INTO financial_data (stock_id, period_type, year, quarter, pe, pb, ps, peg, roe, roa, roic, debt_equity, debt_assets, interest_coverage, asset_turnover, receivables_turnover, inventory_turnover, revenue_growth, earnings_growth, book_value_growth)
VALUES
(1, 'YEARLY', 2024, NULL, 28.5, 4.2, 3.1, 2.1, 18.5, 12.3, 16.8, 0.05, 0.02, 125.0, 1.85, 4.2, 6.1, 8.5, 9.2, 5.3),
(1, 'YEARLY', 2023, NULL, 26.3, 4.0, 2.9, 2.0, 17.8, 11.9, 16.2, 0.06, 0.03, 120.0, 1.80, 4.1, 6.0, 7.2, 8.1, 5.1),
(1, 'YEARLY', 2022, NULL, 24.1, 3.8, 2.7, 1.9, 17.0, 11.5, 15.5, 0.07, 0.04, 115.0, 1.75, 4.0, 5.9, 6.0, 7.0, 4.9);

-- Insert sample financial data for TCS
INSERT INTO financial_data (stock_id, period_type, year, quarter, pe, pb, ps, peg, roe, roa, roic, debt_equity, debt_assets, interest_coverage, asset_turnover, receivables_turnover, inventory_turnover, revenue_growth, earnings_growth, book_value_growth)
VALUES
(2, 'YEARLY', 2024, NULL, 30.2, 4.5, 3.3, 2.2, 19.5, 13.2, 17.5, 0.03, 0.01, 135.0, 1.95, 4.5, 6.5, 6.8, 7.5, 5.5),
(2, 'YEARLY', 2023, NULL, 28.5, 4.2, 3.1, 2.1, 18.8, 12.8, 16.9, 0.04, 0.02, 130.0, 1.90, 4.4, 6.4, 5.5, 6.2, 5.2);

-- Insert sample financial data for HDFCBANK
INSERT INTO financial_data (stock_id, period_type, year, quarter, pe, pb, ps, peg, roe, roa, roic, debt_equity, debt_assets, interest_coverage, asset_turnover, receivables_turnover, inventory_turnover, revenue_growth, earnings_growth, book_value_growth)
VALUES
(3, 'YEARLY', 2024, NULL, 22.5, 2.8, 4.2, 1.8, 15.2, 1.2, 14.5, 8.5, 0.85, 2.5, 0.15, 3.2, 1.8, 12.5, 14.2, 8.5),
(3, 'YEARLY', 2023, NULL, 21.2, 2.6, 4.0, 1.7, 14.8, 1.1, 14.0, 8.3, 0.84, 2.4, 0.14, 3.1, 1.7, 10.2, 12.5, 8.1);

-- Insert sample financial data for SBIN
INSERT INTO financial_data (stock_id, period_type, year, quarter, pe, pb, ps, peg, roe, roa, roic, debt_equity, debt_assets, interest_coverage, asset_turnover, receivables_turnover, inventory_turnover, revenue_growth, earnings_growth, book_value_growth)
VALUES
(4, 'YEARLY', 2024, NULL, 20.5, 2.5, 3.8, 1.6, 14.5, 1.0, 13.8, 9.0, 0.88, 2.3, 0.13, 3.0, 1.6, 11.5, 13.5, 8.0),
(4, 'YEARLY', 2023, NULL, 19.8, 2.4, 3.6, 1.5, 14.0, 0.95, 13.2, 8.8, 0.86, 2.2, 0.12, 2.9, 1.5, 9.8, 11.8, 7.6);

-- Insert sample financial data for RELIANCE
INSERT INTO financial_data (stock_id, period_type, year, quarter, pe, pb, ps, peg, roe, roa, roic, debt_equity, debt_assets, interest_coverage, asset_turnover, receivables_turnover, inventory_turnover, revenue_growth, earnings_growth, book_value_growth)
VALUES
(5, 'YEARLY', 2024, NULL, 25.5, 3.2, 1.5, 2.0, 12.8, 4.5, 11.5, 0.45, 0.30, 4.5, 0.85, 8.5, 12.5, 5.2, 6.5, 4.8),
(5, 'YEARLY', 2023, NULL, 24.0, 3.0, 1.4, 1.9, 12.2, 4.2, 11.0, 0.48, 0.32, 4.3, 0.82, 8.2, 12.0, 3.5, 4.8, 4.5);

-- Insert sample watchlist for admin user
INSERT INTO watchlists (user_id, name, description) VALUES
(1, 'Top Tech Stocks', 'Best performing technology stocks'),
(1, 'Banking Sector', 'Banks and financial institutions');

-- Insert sample watchlist items
INSERT INTO watchlist_items (watchlist_id, stock_id) VALUES
(1, 1), (1, 2), (1, 7), (2, 3), (2, 4);
