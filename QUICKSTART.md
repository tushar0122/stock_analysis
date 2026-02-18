# Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL 12+
- Git

### Step 1: Setup Database

```bash
# Create database
createdb stock_analysis_db

# Verify connection
psql -U postgres -d stock_analysis_db -c "SELECT 1"
```

### Step 2: Run Backend

```bash
cd backend
mvn spring-boot:run
```

Wait for: "Successfully started application"
Backend: http://localhost:8080/api

### Step 3: Run Frontend

In a new terminal:

```bash
cd frontend
npm install
npm run dev
```

Frontend: http://localhost:3000

### Step 4: Login

Use default credentials:
- **Username**: `admin`
- **Password**: `password`

## 📊 Features to Try

### 1. Browse Stocks
- Go to "Stocks" section
- See 10 pre-loaded sample stocks
- Search by name or symbol

### 2. View Stock Details
- Click on any stock
- See yearly financial data
- Toggle between Yearly/Quarterly

### 3. Stock Screener
- Go to "Screener"
- Filter by ratios (e.g., ROE > 15)
- Add multiple conditions with AND/OR
- Click "Apply Filters"

### 4. Manual Data Entry (Admin)
- Go to "Data Entry"
- Select stock and period
- Enter financial ratios
- Save data

### 5. Bulk Upload (Admin)
- Go to "Bulk Upload"
- Download sample CSV
- Upload with financial data
- See success/error count

### 6. Watchlists
- Create watchlist
- Add stocks
- Manage multiple lists

## 🔧 Configuration

### Backend Config
File: `backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/stock_analysis_db
    username: postgres  # Change this
    password: password  # Change this
```

### Frontend Config
File: `frontend/.env`

```env
VITE_API_URL=http://localhost:8080/api
```

## 📋 CSV Format for Bulk Upload

Header:
```
stock_symbol,period_type,year,quarter,pe,pb,ps,peg,roe,roa,roic,debt_equity,debt_assets,interest_coverage,asset_turnover,receivables_turnover,inventory_turnover,revenue_growth,earnings_growth,book_value_growth
```

Example:
```
INFY,YEARLY,2024,,28.5,4.2,3.1,2.1,18.5,12.3,16.8,0.05,0.02,125,1.85,4.2,6.1,8.5,9.2,5.3
```

## 🎨 What You Can Do

### As User
- ✅ View stocks and details
- ✅ Search stocks
- ✅ Use stock screener
- ✅ Create watchlists
- ✅ Compare stocks

### As Admin
- ✅ All user features plus:
- ✅ Create/edit/delete stocks
- ✅ Manual data entry
- ✅ Bulk CSV upload
- ✅ Manage all data

## 🐛 Common Issues

### "Port already in use"
```bash
# Kill process on port 8080
lsof -i :8080 | grep LISTEN | awk '{print $2}' | xargs kill -9

# Kill process on port 3000
lsof -i :3000 | grep LISTEN | awk '{print $2}' | xargs kill -9
```

### "Database connection failed"
- Ensure PostgreSQL is running
- Check username/password in application.yml
- Create database: `createdb stock_analysis_db`

### "CORS errors"
- Frontend port must be in CORS config
- Ensure API_URL in .env is correct

### "Token expired"
- Login again to get new token

## 📚 Next Steps

1. Read `README.md` for detailed documentation
2. Check `PROJECT_SUMMARY.md` for architecture
3. Explore code structure
4. Add more stocks via CSV
5. Customize for your needs

## 💡 Tips

- Sample data is pre-loaded
- Migrations run automatically
- Use browser DevTools to inspect API calls
- Check console logs for errors
- Admin user has all permissions

## 🔐 Security Notes

For production:
1. Change default credentials
2. Update JWT secret in application.yml
3. Enable HTTPS
4. Use environment variables
5. Restrict CORS origins
6. Update database password

## 📞 Need Help?

1. Check README.md for detailed docs
2. Review error messages
3. Check browser/server console logs
4. Verify database connection
5. Ensure ports are available

## 🎯 What's Included

✅ 100% functional application
✅ Pre-loaded sample data (10 stocks)
✅ JWT authentication & authorization
✅ CSV bulk upload capability
✅ Advanced filtering/screening
✅ Responsive UI design
✅ Database migrations
✅ Error handling
✅ Complete documentation

---

**Happy analyzing! 📈**
