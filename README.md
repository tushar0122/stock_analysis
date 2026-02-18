# Stock Analysis & Screening Platform

A complete full-stack application for analyzing, filtering, and comparing stocks using comprehensive financial ratios. Built with Spring Boot Java backend and React frontend.

## Features

- **📊 Stock Database**: Browse and manage stock information
- **🔍 Advanced Stock Screener**: Filter stocks by multiple financial ratios with AND/OR logic
- **📈 Financial Ratios**: Valuation, Return, Solvency, Efficiency, and Growth ratios
- **⭐ Watchlists**: Create and manage custom stock watchlists
- **📋 Data Management**: Manual entry and bulk CSV upload of financial data
- **🔄 Stock Comparison**: Compare up to 4 stocks side by side
- **🔐 JWT Authentication**: Secure access with role-based permissions (ADMIN/USER)
- **📱 Responsive Design**: Works on desktop and mobile devices

## Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Token)
- **Migration**: Flyway
- **Build**: Maven

### Frontend
- **Framework**: React 18
- **Build Tool**: Vite
- **HTTP Client**: Axios
- **Routing**: React Router v6
- **Styling**: CSS3

## Project Structure

```
stock-analysis-platform/
├── backend/
│   ├── src/main/java/com/stockanalysis/
│   │   ├── config/           # Security & CORS configuration
│   │   ├── entity/           # JPA entities
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── repository/       # Data access layer
│   │   ├── service/          # Business logic
│   │   ├── controller/       # REST API endpoints
│   │   ├── security/         # Authentication & Authorization
│   │   ├── exception/        # Custom exceptions & handlers
│   │   └── util/             # Utility classes
│   └── src/main/resources/
│       ├── application.yml   # Configuration
│       └── db/migration/     # Database migrations
│
├── frontend/
│   ├── src/
│   │   ├── components/       # React components
│   │   ├── pages/            # Page components
│   │   ├── services/         # API service layer
│   │   ├── styles/           # CSS files
│   │   ├── utils/            # Utility functions
│   │   ├── App.jsx           # Root component
│   │   └── index.jsx         # Entry point
│   ├── public/               # Static files
│   └── package.json          # Dependencies
```

## Prerequisites

- Java 17 or higher
- Node.js 18+ and npm
- PostgreSQL 12+
- Git

## Installation & Setup

### 1. Database Setup

```bash
# Create PostgreSQL database
createdb stock_analysis_db

# Or using psql:
psql -U postgres -c "CREATE DATABASE stock_analysis_db;"
```

Update `backend/src/main/resources/application.yml` with your PostgreSQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/stock_analysis_db
    username: postgres  # your username
    password: password  # your password
```

### 2. Backend Setup

```bash
cd backend

# Build the project
mvn clean package

# Or run directly
mvn spring-boot:run
```

The backend will start on `http://localhost:8080/api`

Database migrations will run automatically on startup.

### 3. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start on `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /auth/login` - Login user
- `POST /auth/register` - Register new user
- `GET /auth/verify` - Verify token

### Stocks
- `GET /stocks` - Get all stocks (paginated)
- `GET /stocks/search?q=term` - Search stocks
- `GET /stocks/{id}` - Get stock details
- `GET /stocks/symbol/{symbol}` - Get by symbol
- `POST /stocks` - Create stock (ADMIN)
- `PUT /stocks/{id}` - Update stock (ADMIN)
- `DELETE /stocks/{id}` - Delete stock (ADMIN)

### Financial Data
- `GET /financial-data/stock/{stockId}` - Get all financial data for stock
- `GET /financial-data/stock/{stockId}/period/{periodType}` - Get by period type
- `POST /financial-data` - Create/update financial data (ADMIN)
- `DELETE /financial-data/{id}` - Delete financial data (ADMIN)

### Screener
- `POST /screener/filter` - Apply filters to stocks

### Watchlists
- `GET /watchlists` - Get user's watchlists
- `POST /watchlists` - Create watchlist
- `GET /watchlists/{id}` - Get watchlist details
- `POST /watchlists/{id}/stocks/{stockId}` - Add stock to watchlist
- `DELETE /watchlists/{id}/stocks/{stockId}` - Remove stock from watchlist
- `DELETE /watchlists/{id}` - Delete watchlist

### Upload
- `POST /upload/financial-data` - Bulk upload CSV (ADMIN)

## Default Credentials

For testing purposes, two default users are created:

| Username | Password | Role |
|----------|----------|------|
| admin    | password | ADMIN |
| user     | password | USER |

**Note**: Change these credentials in production!

## CSV Upload Format

Header Row:
```
stock_symbol,period_type,year,quarter,pe,pb,ps,peg,roe,roa,roic,debt_equity,debt_assets,interest_coverage,asset_turnover,receivables_turnover,inventory_turnover,revenue_growth,earnings_growth,book_value_growth
```

Example Row:
```
INFY,YEARLY,2024,,28.5,4.2,3.1,2.1,18.5,12.3,16.8,0.05,0.02,125,1.85,4.2,6.1,8.5,9.2,5.3
```

For quarterly data, include the quarter (1-4):
```
INFY,QUARTERLY,2024,1,28.5,4.2,3.1,2.1,18.5,12.3,16.8,0.05,0.02,125,1.85,4.2,6.1,8.5,9.2,5.3
```

## Screener Usage

Example filter request:
```json
{
  "operator": "AND",
  "conditions": [
    {
      "field": "roe",
      "comparison": ">",
      "value": "15"
    },
    {
      "field": "pe",
      "comparison": "<",
      "value": "30"
    }
  ]
}
```

Supported comparison operators: `>`, `<`, `=`, `>=`, `<=`

Logical operators: `AND`, `OR`

## Key Features Explained

### Manual Data Entry
Admin users can manually enter financial data for stocks through an intuitive form. Data is automatically validated and saved.

### Bulk Upload
Admins can upload CSV files containing multiple financial data records. The system validates each row and reports errors with row numbers.

### Stock Screener
Users can filter stocks using multiple conditions combined with AND/OR logic. Example: "ROE > 15% AND P/E < 30"

### Watchlists
Users can create multiple watchlists and add/remove stocks quickly from the detail page.

### Stock Comparison
Compare up to 4 stocks side by side, viewing key metrics like P/E, ROE, ROA, and Debt/Equity ratios.

## Database Schema

### Users Table
- id, username, email, password, role, active, created_at, updated_at

### Stocks Table
- id, name, symbol, sector, industry, exchange, market_cap, created_at, updated_at

### Financial Data Table
- id, stock_id, period_type, year, quarter
- Financial ratios (pe, pb, ps, peg, roe, roa, roic, debt_equity, etc.)
- created_at, updated_at
- Unique constraint on (stock_id, period_type, year, quarter)

### Watchlists Table
- id, user_id, name, description, created_at, updated_at

### Watchlist Items Table
- id, watchlist_id, stock_id, added_at
- Unique constraint on (watchlist_id, stock_id)

## Configuration

### Backend Configuration (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/stock_analysis_db
    username: postgres
    password: password

jwt:
  secret: your-super-secret-key-change-this-in-production
  expiration: 86400000  # 24 hours in milliseconds
```

### Frontend Configuration (.env)
```
VITE_API_URL=http://localhost:8080/api
VITE_APP_NAME=Stock Analysis Platform
```

## Security Features

- **JWT Authentication**: Secure token-based authentication
- **Role-Based Access Control**: ADMIN and USER roles with different permissions
- **Password Encryption**: BCrypt hashing for passwords
- **CORS Configuration**: Restricted cross-origin requests
- **Input Validation**: Server-side validation of all inputs
- **SQL Injection Prevention**: Using JPA parameterized queries

## Error Handling

The API returns standard HTTP status codes:
- `200 OK` - Successful request
- `201 Created` - Resource created
- `400 Bad Request` - Invalid input
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `409 Conflict` - Duplicate record
- `500 Internal Server Error` - Server error

Error response format:
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "message": "Error description",
  "error": "Error type"
}
```

## Performance Optimization

- Database indexing on frequently queried columns
- Pagination for large datasets
- Efficient SQL queries without N+1 problems
- Lazy loading for related entities
- Frontend code splitting and lazy route loading

## Future Enhancements

- Real-time stock price updates
- Technical indicators and charting
- Portfolio management
- Price alerts
- Export functionality (PDF, Excel)
- Mobile app
- AI-powered stock recommendations
- Historical data analytics
- News integration

## Development

### Running Tests

Backend:
```bash
cd backend
mvn test
```

Frontend:
```bash
cd frontend
npm run test
```

### Building for Production

Backend:
```bash
cd backend
mvn clean package -DskipTests
```

Frontend:
```bash
cd frontend
npm run build
```

## Troubleshooting

### Database Connection Error
- Ensure PostgreSQL is running
- Check credentials in application.yml
- Verify database exists: `psql -l`

### Port Already in Use
- Backend (8080): `lsof -i :8080` and kill the process
- Frontend (3000): `lsof -i :3000` and kill the process

### CORS Errors
- Ensure frontend URL is in CORS configuration
- Check browser console for specific error

### JWT Token Expired
- Tokens expire after 24 hours
- User needs to login again to get new token

## License

MIT License - feel free to use this project for learning and development.

## Support

For issues or questions, please check the documentation or create an issue in the repository.

## Contributors

Created as a comprehensive full-stack project template.

---

**Last Updated**: January 2024
**Version**: 1.0.0
