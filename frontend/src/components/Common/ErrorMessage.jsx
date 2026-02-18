import React from 'react';
import '../styles/error.css';

const ErrorMessage = ({ message, onClose }) => {
  if (!message) return null;

  return (
    <div className="error-message">
      <p>{message}</p>
      {onClose && <button onClick={onClose} className="error-close">×</button>}
    </div>
  );
};

export default ErrorMessage;
