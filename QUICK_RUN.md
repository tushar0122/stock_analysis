# 🚀 QUICK LOCAL EXECUTION GUIDE

## ⏱️ 5-MINUTE SETUP

### Prerequisites Check
```bash
java -version          # Should be Java 17+
node --version         # Should be Node 18+
npm --version          # Should be npm 9+
psql --version         # Should be PostgreSQL 12+
```

### Step 1: PostgreSQL Setup (30 seconds)

**Windows/Mac/Linux:**
```bash
createdb stock_analysis_db
```

**Verify:**
```bash
psql -U postgres -d stock_analysis_db -c "SELECT 1;"
```

**With Docker (if you prefer):**
```bash
docker run -d --name pg_stock -e POSTGRES_DB=stock_analysis_db -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:15
```

### Step 2: Backend (2 minutes)

**Terminal 1:**
```bash
cd backend
mvn spring-boot:run
```

**Wait for:**
```
Started StockAnalysisApplication in X.XX seconds
```

**Backend ready at:** `http://localhost:8080/api`

### Step 3: Frontend (1 minute)

**Terminal 2:**
```bash
cd frontend
npm install
npm run dev
```

**Frontend ready at:** `http://localhost:3000`

### Step 4: ACCESS APPLICATION (30 seconds)

1. Open browser: `http://localhost:3000`
2. **Login**:
   - Username: `admin`
   - Password: `password`
3. **Start exploring!**

---

## ✅ Verify Everything Works

**Backend Health Check:**
```bash
curl http://localhost:8080/api/stocks
# Should return: {"content": [... stocks ...], "totalPages": 1}
```

**Frontend Check:**
- Should load without errors
- Navbar visible
- Can navigate to different pages
- Login/logout works

---

## 🎯 QUICK TEST SCENARIOS

### Scenario 1: View Stocks (1 min)
1. Logged in? ✅
2. Click "Stocks" nav
3. See 10 stocks? ✅
4. Search "INFY"? ✅
5. Click stock? See details? ✅

### Scenario 2: Enter Data (3 min)
1. Click "Data Entry" (Admin only) ✅
2. Select "INFY" stock ✅
3. Select "Yearly" ✅
4. Select year "2024" ✅
5. Enter "P/E" = 28.5 ✅
6. Click "Save"? ✅

### Scenario 3: Bulk Upload (2 min)
1. Click "Bulk Upload" ✅
2. Click "Download Template" ✅
3. Open CSV file ✅
4. Add one line of data ✅
5. Upload file ✅
6. See "1 Success" message? ✅

### Scenario 4: Screener (2 min)
1. Click "Screener" ✅
2. ROE > 15 ✅
3. Click "Apply Filters" ✅
4. See results? ✅
5. Click "View" on a stock ✅

### Scenario 5: Watchlist (1 min)
1. Click "Watchlist" ✅
2. "+ New Watchlist" ✅
3. Name: "My Favorites" ✅
4. Create ✅
5. Add stocks? ✅

---

## 🐛 TROUBLESHOOTING

### "Cannot connect to database"
```bash
# Check PostgreSQL running
psql -U postgres -c "\l"

# Create database
createdb stock_analysis_db
```

### "Port 8080 already in use"
**Change port in** `backend/src/main/resources/application.yml`:
```yaml
server:
  port: 8081  # Use 8081 instead
```

### "Port 3000 already in use"
**Update frontend in** `frontend/vite.config.js`:
```javascript
server: {
  port: 3001,  // Use 3001 instead
}
```

### "npm install fails"
```bash
rm -rf node_modules package-lock.json
npm install
```

### "CORS error"
**Press F12 in browser**, reload. Should work if both backend & frontend running.

---

## 📊 DATABASE INITIALIZATION

**Database migrations run automatically:**

1. **V1__Initial_Schema.sql**
   - Creates all tables
   - Creates indexes
   - Creates constraints

2. **V2__Insert_Default_Data.sql**
   - Inserts 10 stocks
   - Inserts sample financial data
   - Creates demo user watchlists

3. **V3__Add_Extended_Ratios_And_Draft_Status.sql**
   - Adds new ratio columns (EV/EBITDA, ROCE, etc.)
   - Adds draft/publish workflow
   - Creates audit_logs & saved_screeners tables

---

## 🔐 DEFAULT USERS

| Username | Password | Role |
|----------|----------|------|
| admin | password | ADMIN |
| user | password | USER |

**First time:**
- Use `admin` account (full access)
- Try admin-only features (Data Entry, Bulk Upload)
- Switch to `user` account to see read-only view

---

## 📝 SAMPLE DATA

Pre-loaded **10 stocks**:
1. INFY (Infosys)
2. TCS (Tata Consultancy)
3. HDFCBANK (HDFC Bank)
4. SBIN (SBI)
5. RELIANCE (Reliance Industries)
6. HINDUNILVR (HUL)
7. WIPRO (Wipro)
8. MARUTI (Maruti Suzuki)
9. BHARTIARTL (Bharti Airtel)
10. ITC (ITC Limited)

**Financial data:** 2022-2024 (yearly)

---

## 🎨 WHAT YOU CAN DO

### As ADMIN:
```
✅ View/Create/Edit/Delete Stocks
✅ Manual financial data entry (all 25 ratios)
✅ Bulk CSV upload with validation
✅ View audit logs
✅ Save screeners
✅ All USER features
```

### As USER:
```
✅ Browse stocks & search
✅ View financial details & trends
✅ Use advanced screener
✅ Compare stocks (up to 4)
✅ Create & manage watchlists
✅ Load saved screeners
❌ Cannot modify data
```

---

## 🚀 COMMANDS QUICK REFERENCE

```bash
# Database
createdb stock_analysis_db
psql -U postgres -d stock_analysis_db

# Backend
cd backend
mvn clean package           # Build JAR
mvn spring-boot:run        # Run directly
java -jar target/...jar    # Run built JAR

# Frontend
cd frontend
npm install                # First time
npm run dev               # Development
npm run build             # Production build
npm run preview           # Preview production build

# Kill port processes (if needed)
lsof -i :8080 | grep LISTEN | awk '{print $2}' | xargs kill -9
lsof -i :3000 | grep LISTEN | awk '{print $2}' | xargs kill -9
lsof -i :5432 | grep LISTEN | awk '{print $2}' | xargs kill -9
```

---

## ✨ KEY FILES TO CHECK

**If things go wrong, check these:**

| Issue | File | Check |
|-------|------|-------|
| DB Connection | `application.yml` | Username/password match |
| API not responding | `SecurityConfig.java` | Endpoints are permitted |
| Frontend can't reach API | `.env` | VITE_API_URL is correct |
| Login fails | `application.yml` | JWT secret configured |
| Port conflicts | `application.yml`, `vite.config.js` | Update ports |

---

## 📞 QUICK SUPPORT

**Everything should work out of the box.**

If not:
1. Check Prerequisites ✅
2. Check Logs (console output)
3. Try stopping/restarting services
4. Clear browser cache (Ctrl+Shift+Del)
5. Check if ports are available

---

## 🎯 SUCCESS INDICATORS

You'll know it's working when:

✅ **Backend starts:**
```
Started StockAnalysisApplication in X.XXX seconds
Listening on port 8080
```

✅ **Frontend loads:**
- Loads without errors
- Shows login page
- Can click to different sections

✅ **Login works:**
- admin/password logs in
- Redirects to stock list
- Shows 10 stocks

✅ **Data operations work:**
- Can view stock details
- Can apply screeners
- Can create watchlists

---

## 🎉 YOU'RE DONE!

Your complete stock analysis platform is running locally with PostgreSQL.

**Next steps:**
1. Browse stock data
2. Try manual entry
3. Upload CSV file
4. Use screener
5. Create watchlist
6. Explore all features

Enjoy! 📈

---

**Version**: 1.0
**Last Updated**: January 2024
**Status**: Ready to Use
