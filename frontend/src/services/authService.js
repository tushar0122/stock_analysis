import api from './api';

const authService = {
  login: (username, password) => {
    return api.post('/auth/login', { username, password });
  },

  register: (username, password) => {
    return api.post('/auth/register', { username, password });
  },

  verify: () => {
    return api.get('/auth/verify');
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  getToken: () => {
    return localStorage.getItem('token');
  },

  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },

  getUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  setUser: (user, token) => {
    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('token', token);
  }
};

export default authService;
