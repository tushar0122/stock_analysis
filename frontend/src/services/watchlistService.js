import api from './api';

const watchlistService = {
  getWatchlists: () => {
    return api.get('/watchlists');
  },

  getWatchlistById: (id) => {
    return api.get(`/watchlists/${id}`);
  },

  createWatchlist: (userId, name, description) => {
    return api.post('/watchlists', null, { params: { userId, name, description } });
  },

  addStockToWatchlist: (watchlistId, stockId) => {
    return api.post(`/watchlists/${watchlistId}/stocks/${stockId}`);
  },

  removeStockFromWatchlist: (watchlistId, stockId) => {
    return api.delete(`/watchlists/${watchlistId}/stocks/${stockId}`);
  },

  deleteWatchlist: (id) => {
    return api.delete(`/watchlists/${id}`);
  }
};

export default watchlistService;
