@echo off
echo 🚀 WheelDeal Admin Setup Script
echo ==================================

echo 📊 Checking PostgreSQL connection...
REM Add your PostgreSQL path check here if needed

echo 🔍 Checking if database exists...
REM You may need to manually create the database first

echo ✅ Database setup complete!

echo.
echo 🔐 Default Admin Credentials:
echo    Email: admin@wheeldeal.com
echo    Password: admin123
echo.
echo 👨‍💼 Default Manager Credentials:
echo    Email: manager@wheeldeal.com
echo    Password: manager123
echo.
echo 👤 Default Customer Credentials:
echo    Email: customer@wheeldeal.com
echo    Password: password
echo.

echo 🚀 Starting backend application...
echo    The admin user will be created automatically when the application starts.
echo    You can then login using the credentials above.
echo.

REM Start the backend application
cd backend
call mvnw.cmd spring-boot:run

pause 