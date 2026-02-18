import React, { useState, useEffect } from 'react';
import watchlistService from '../../services/watchlistService';
import authService from '../../services/authService';
import Loading from '../Common/Loading';
import ErrorMessage from '../Common/ErrorMessage';
import '../../styles/watchlist.css';

const WatchlistView = () => {
  const [watchlists, setWatchlists] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [newWatchlistName, setNewWatchlistName] = useState('');
  const [showCreate, setShowCreate] = useState(false);

  useEffect(() => {
    fetchWatchlists();
  }, []);

  const fetchWatchlists = async () => {
    setLoading(true);
    setError('');

    try {
      const user = authService.getUser();
      const response = await watchlistService.getWatchlists();
      setWatchlists(response.data);
    } catch (err) {
      setError('Failed to load watchlists');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateWatchlist = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const user = authService.getUser();
      await watchlistService.createWatchlist(user.id, newWatchlistName, '');
      setNewWatchlistName('');
      setShowCreate(false);
      fetchWatchlists();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create watchlist');
    }
  };

  const handleDeleteWatchlist = async (id) => {
    if (window.confirm('Are you sure you want to delete this watchlist?')) {
      try {
        await watchlistService.deleteWatchlist(id);
        fetchWatchlists();
      } catch (err) {
        setError('Failed to delete watchlist');
      }
    }
  };

  if (loading) return <Loading message="Loading watchlists..." />;

  return (
    <div className="watchlist-container">
      <div className="watchlist-header">
        <h1>My Watchlists</h1>
        <button onClick={() => setShowCreate(!showCreate)} className="btn-primary">
          {showCreate ? 'Cancel' : '+ New Watchlist'}
        </button>
      </div>

      <ErrorMessage message={error} onClose={() => setError('')} />

      {showCreate && (
        <form onSubmit={handleCreateWatchlist} className="create-watchlist-form">
          <input
            type="text"
            value={newWatchlistName}
            onChange={(e) => setNewWatchlistName(e.target.value)}
            placeholder="Watchlist name"
            required
          />
          <button type="submit" className="btn-primary">Create</button>
        </form>
      )}

      <div className="watchlists-grid">
        {watchlists.length > 0 ? (
          watchlists.map(watchlist => (
            <div key={watchlist.id} className="watchlist-card">
              <h3>{watchlist.name}</h3>
              <p className="stock-count">{watchlist.stocks.size} stocks</p>
              <div className="stocks-list">
                {Array.from(watchlist.stocks).map(stock => (
                  <span key={stock.id} className="stock-badge">{stock.symbol}</span>
                ))}
              </div>
              <div className="card-actions">
                <button onClick={() => handleDeleteWatchlist(watchlist.id)} className="btn-danger">
                  Delete
                </button>
              </div>
            </div>
          ))
        ) : (
          <p className="no-results">No watchlists yet</p>
        )}
      </div>
    </div>
  );
};

export default WatchlistView;
