export const formatNumber = (value, decimals = 2) => {
  if (value === null || value === undefined) return '-';
  return parseFloat(value).toFixed(decimals);
};

export const formatCurrency = (value) => {
  if (value === null || value === undefined) return '-';
  return new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR'
  }).format(value);
};

export const formatPercentage = (value) => {
  if (value === null || value === undefined) return '-';
  return `${parseFloat(value).toFixed(2)}%`;
};

export const formatMarketCap = (value) => {
  if (!value) return '-';
  return value.toString();
};

export const getColorClass = (value, lowerIsBetter = false) => {
  if (value === null || value === undefined) return '';

  const numValue = parseFloat(value);

  if (lowerIsBetter) {
    if (numValue < 0) return 'positive';
    if (numValue > 0) return 'negative';
  } else {
    if (numValue > 0) return 'positive';
    if (numValue < 0) return 'negative';
  }

  return 'neutral';
};

export const parseCSVData = (data) => {
  const lines = data.trim().split('\n');
  const headers = lines[0].split(',');
  const rows = [];

  for (let i = 1; i < lines.length; i++) {
    const obj = {};
    const line = lines[i].split(',');

    for (let j = 0; j < headers.length; j++) {
      obj[headers[j].trim()] = line[j]?.trim() || '';
    }

    rows.push(obj);
  }

  return rows;
};
