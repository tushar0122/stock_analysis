import api from './api';

const screenerService = {
  applyFilters: (filters) => {
    return api.post('/screener/filter', filters);
  }
};

export default screenerService;
