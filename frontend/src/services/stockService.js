import api from './api';

const stockService = {
  getAllStocks: (page = 0, size = 20) => {
    return api.get('/stocks', { params: { page, size } });
  },

  searchStocks: (query, page = 0, size = 20) => {
    return api.get('/stocks/search', { params: { q: query, page, size } });
  },

  getStockById: (id) => {
    return api.get(`/stocks/${id}`);
  },

  getStockBySymbol: (symbol) => {
    return api.get(`/stocks/symbol/${symbol}`);
  },

  createStock: (stockData) => {
    return api.post('/stocks', stockData);
  },

  updateStock: (id, stockData) => {
    return api.put(`/stocks/${id}`, stockData);
  },

  deleteStock: (id) => {
    return api.delete(`/stocks/${id}`);
  }
};

export default stockService;
