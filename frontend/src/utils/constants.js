export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

export const PERIOD_TYPES = {
  YEARLY: 'YEARLY',
  QUARTERLY: 'QUARTERLY'
};

export const RATIO_CATEGORIES = {
  VALUATION: 'VALUATION',
  RETURN: 'RETURN',
  SOLVENCY: 'SOLVENCY',
  EFFICIENCY: 'EFFICIENCY',
  GROWTH: 'GROWTH'
};

export const RATIO_FIELDS = {
  VALUATION: [
    { key: 'pe', label: 'P/E Ratio' },
    { key: 'pb', label: 'P/B Ratio' },
    { key: 'ps', label: 'P/S Ratio' },
    { key: 'peg', label: 'PEG Ratio' },
    { key: 'evEbitda', label: 'EV/EBITDA' },
    { key: 'dividendYield', label: 'Dividend Yield %' }
  ],
  RETURN: [
    { key: 'roe', label: 'ROE %' },
    { key: 'roa', label: 'ROA %' },
    { key: 'roic', label: 'ROIC %' },
    { key: 'roce', label: 'ROCE %' }
  ],
  SOLVENCY: [
    { key: 'debtEquity', label: 'Debt/Equity' },
    { key: 'debtAssets', label: 'Debt/Assets' },
    { key: 'interestCoverage', label: 'Interest Coverage' },
    { key: 'currentRatio', label: 'Current Ratio' }
  ],
  EFFICIENCY: [
    { key: 'assetTurnover', label: 'Asset Turnover' },
    { key: 'receivablesTurnover', label: 'Receivables Turnover' },
    { key: 'inventoryTurnover', label: 'Inventory Turnover' }
  ],
  GROWTH: [
    { key: 'revenueGrowth', label: 'Revenue Growth %' },
    { key: 'profitGrowth', label: 'Profit Growth %' },
    { key: 'epsGrowth', label: 'EPS Growth %' },
    { key: 'bookValueGrowth', label: 'Book Value Growth %' }
  ]
};

export const YEARS = Array.from({ length: 10 }, (_, i) => new Date().getFullYear() - i);

export const QUARTERS = [
  { value: 1, label: 'Q1' },
  { value: 2, label: 'Q2' },
  { value: 3, label: 'Q3' },
  { value: 4, label: 'Q4' }
];

export const COMPARISON_OPERATORS = [
  { value: '>', label: '>' },
  { value: '<', label: '<' },
  { value: '=', label: '=' },
  { value: '>=', label: '>=' },
  { value: '<=', label: '<=' }
];

export const LOGICAL_OPERATORS = [
  { value: 'AND', label: 'AND' },
  { value: 'OR', label: 'OR' }
];
