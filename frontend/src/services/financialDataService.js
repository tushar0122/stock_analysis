import api from './api';

const financialDataService = {
  getById: (id) => {
    return api.get(`/financial-data/${id}`);
  },

  getByStockId: (stockId) => {
    return api.get(`/financial-data/stock/${stockId}`);
  },

  getByStockAndPeriodType: (stockId, periodType) => {
    return api.get(`/financial-data/stock/${stockId}/period/${periodType}`);
  },

  createOrUpdate: (financialData) => {
    return api.post('/financial-data', financialData);
  },

  delete: (id) => {
    return api.delete(`/financial-data/${id}`);
  }
};

export default financialDataService;
