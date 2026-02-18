import React from 'react';
import AddStock from './AddStock';
import '../../styles/modal.css';

const AddStockModal = ({ isOpen, onClose, onSuccess }) => {
  if (!isOpen) {
    return null;
  }

  const handleSuccess = () => {
    onSuccess();
    onClose();
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>Add New Stock</h2>
          <button 
            className="modal-close-btn"
            onClick={onClose}
            type="button"
            aria-label="Close modal"
          >
            ✕
          </button>
        </div>

        <div className="modal-body">
          <AddStock 
            onSuccess={handleSuccess}
            onClose={onClose}
          />
        </div>
      </div>
    </div>
  );
};

export default AddStockModal;
