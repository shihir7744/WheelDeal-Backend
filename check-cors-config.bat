@echo off
echo ========================================
echo    Backend CORS Configuration Check
echo    (For Backend Repository)
echo ========================================
echo.

echo PROBLEM: Frontend requests not reaching backend
echo SYMPTOM: No backend HTTP logs
echo.

echo STEP 1: Check CORS configuration files
echo =====================================
echo.

echo Checking application-prod.properties...
if exist "src\main\resources\application-prod.properties" (
    echo ✅ application-prod.properties exists
    echo.
    echo CORS configuration:
    findstr /C:"cors.allowed-origins" "src\main\resources\application-prod.properties"
    echo.
) else (
    echo ❌ application-prod.properties NOT found
    echo This file is required for production CORS configuration
)

echo Checking CorsConfig.java...
if exist "src\main\java\com\carrental\backend\config\CorsConfig.java" (
    echo ✅ CorsConfig.java exists
    echo.
    echo CORS annotation:
    findstr /C:"@Value.*cors.allowed-origins" "src\main\java\com\carrental\backend\config\CorsConfig.java"
    echo.
) else (
    echo ❌ CorsConfig.java NOT found
    echo This file is required for CORS configuration
)

echo STEP 2: Check environment variables
echo ===================================
echo.

echo Checking railway.env file...
if exist "railway.env" (
    echo ✅ railway.env exists
    echo.
    echo Current environment variables:
    type railway.env
    echo.
) else (
    echo ❌ railway.env NOT found
    echo This file should contain your Railway environment variables
)

echo STEP 3: Verify CORS configuration
echo ==================================
echo.

echo To verify CORS is working:
echo 1. Check if your backend is running on Railway
echo 2. Verify the CORS_ALLOWED_ORIGINS environment variable is set
echo 3. Ensure it includes your frontend URL
echo.

echo Expected CORS configuration:
echo - cors.allowed-origins should include your frontend URL
echo - CORS_ALLOWED_ORIGINS should be set in Railway
echo - Frontend URL: https://wheeldeal-frontend-production.up.railway.app
echo.

echo STEP 4: Next steps
echo ===================
echo.

echo If CORS configuration looks correct:
echo 1. Commit and push to your backend repository
echo 2. Deploy to Railway: railway up
echo 3. Check Railway logs for CORS errors
echo.

echo If CORS configuration is missing:
echo 1. Add missing CORS configuration
echo 2. Set CORS_ALLOWED_ORIGINS in Railway
echo 3. Redeploy backend
echo.

echo STEP 5: Test connectivity
echo ==========================
echo.

echo After fixing CORS, test with:
echo 1. Try to login from frontend
echo 2. Check if backend receives HTTP requests
echo 3. Look for CORS errors in browser console
echo.

pause
