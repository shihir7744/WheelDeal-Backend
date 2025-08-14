@echo off
echo ========================================
echo    Test Backend Connectivity
echo ========================================
echo.

set /p BACKEND_URL="Enter your backend URL (e.g., https://wheeldeal-backend-production.up.railway.app): "

if "%BACKEND_URL%"=="" (
    echo Error: Backend URL cannot be empty
    pause
    exit /b 1
)

echo.
echo Testing backend connectivity...
echo.

echo 1. Testing health endpoint...
curl -v "%BACKEND_URL%/actuator/health"
echo.
echo.

echo 2. Testing CORS preflight...
curl -v -X OPTIONS "%BACKEND_URL%/api/auth/login" -H "Origin: https://wheeldeal-frontend-production.up.railway.app"
echo.
echo.

echo 3. Testing API endpoint...
curl -v "%BACKEND_URL%/api/auth/login" -H "Content-Type: application/json" -d "{\"email\":\"test@test.com\",\"password\":\"test\"}"
echo.
echo.

echo If you see CORS errors or connection refused:
echo - Backend might not be running
echo - CORS configuration might be wrong
echo - Network/firewall issues
echo.

echo If you see successful responses:
echo - Backend is accessible
echo - CORS is working
echo - Issue might be in frontend configuration
echo.

pause
