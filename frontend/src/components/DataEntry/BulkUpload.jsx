import React, { useState } from 'react';
import api from '../../services/api';
import ErrorMessage from '../Common/ErrorMessage';
import '../../styles/bulkupload.css';

const BulkUpload = () => {
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [response, setResponse] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setResponse(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setResponse(null);

    if (!file) {
      setError('Please select a file');
      return;
    }

    setLoading(true);

    const formData = new FormData();
    formData.append('file', file);

    try {
      const uploadResponse = await api.post('/upload/financial-data', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });

      setResponse(uploadResponse.data);
      setFile(null);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to upload file');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bulk-upload-container">
      <h1>Bulk Upload Financial Data</h1>

      <div className="upload-instructions">
        <h2>CSV Format</h2>
        <p>Your CSV file should have the following columns:</p>
        <code>stock_symbol, period_type, year, quarter, pe, pb, ps, peg, roe, roa, roic, debt_equity, debt_assets, interest_coverage, asset_turnover, receivables_turnover, inventory_turnover, revenue_growth, earnings_growth, book_value_growth</code>
        <p>Download a sample CSV template:</p>
        <button className="btn-secondary" onClick={() => downloadTemplate()}>Download Template</button>
      </div>

      <ErrorMessage message={error} onClose={() => setError('')} />

      <form onSubmit={handleSubmit} className="upload-form">
        <div className="form-group file-input">
          <label>Select CSV File</label>
          <input
            type="file"
            accept=".csv"
            onChange={handleFileChange}
            required
          />
          {file && <p className="file-name">{file.name}</p>}
        </div>

        <button type="submit" disabled={loading} className="btn-primary">
          {loading ? 'Uploading...' : 'Upload File'}
        </button>
      </form>

      {response && (
        <div className="upload-response">
          <h2>Upload Results</h2>
          <div className="response-stats">
            <div className="stat">
              <label>Total Rows:</label>
              <span>{response.totalRows}</span>
            </div>
            <div className="stat">
              <label>Success:</label>
              <span className="success">{response.successCount}</span>
            </div>
            <div className="stat">
              <label>Failed:</label>
              <span className="error">{response.failedCount}</span>
            </div>
          </div>

          {response.errors && response.errors.length > 0 && (
            <div className="errors-list">
              <h3>Errors:</h3>
              <ul>
                {response.errors.map((err, idx) => (
                  <li key={idx}>Row {err.rowNumber}: {err.error}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

const downloadTemplate = () => {
  const csvContent = `stock_symbol,period_type,year,quarter,pe,pb,ps,peg,roe,roa,roic,debt_equity,debt_assets,interest_coverage,asset_turnover,receivables_turnover,inventory_turnover,revenue_growth,earnings_growth,book_value_growth
INFY,YEARLY,2024,,28.5,4.2,3.1,2.1,18.5,12.3,16.8,0.05,0.02,125,1.85,4.2,6.1,8.5,9.2,5.3
TCS,YEARLY,2024,,30.2,4.5,3.3,2.2,19.5,13.2,17.5,0.03,0.01,135,1.95,4.5,6.5,6.8,7.5,5.5`;

  const element = document.createElement('a');
  element.setAttribute('href', 'data:text/csv;charset=utf-8,' + encodeURIComponent(csvContent));
  element.setAttribute('download', 'template.csv');
  element.style.display = 'none';
  document.body.appendChild(element);
  element.click();
  document.body.removeChild(element);
};

export default BulkUpload;
