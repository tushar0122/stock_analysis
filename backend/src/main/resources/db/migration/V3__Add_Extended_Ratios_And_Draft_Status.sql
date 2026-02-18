-- V3__Add_Extended_Ratios_And_Draft_Status.sql

-- Add new columns to financial_data
ALTER TABLE financial_data ADD COLUMN ev_ebitda DECIMAL(10, 2);
ALTER TABLE financial_data ADD COLUMN dividend_yield DECIMAL(10, 2);
ALTER TABLE financial_data ADD COLUMN roce DECIMAL(10, 2);
ALTER TABLE financial_data ADD COLUMN current_ratio DECIMAL(10, 2);
ALTER TABLE financial_data ADD COLUMN profit_growth DECIMAL(10, 2);
ALTER TABLE financial_data ADD COLUMN eps_growth DECIMAL(10, 2);

-- Rename earnings_growth to be consistent
ALTER TABLE financial_data RENAME COLUMN earnings_growth TO book_value_growth_alt;

-- Add draft and publish tracking
ALTER TABLE financial_data ADD COLUMN is_draft BOOLEAN NOT NULL DEFAULT true;
ALTER TABLE financial_data ADD COLUMN published_at TIMESTAMP;

-- Create audit logs table
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    old_values TEXT,
    new_values TEXT,
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create saved screeners table
CREATE TABLE saved_screeners (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    filter_json TEXT NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_audit_logs_entity ON audit_logs(entity_type, entity_id);
CREATE INDEX idx_audit_logs_user_id ON audit_logs(user_id);
CREATE INDEX idx_saved_screeners_user_id ON saved_screeners(user_id);
CREATE INDEX idx_financial_data_draft ON financial_data(is_draft);
