#!/bin/bash

echo "🚀 WheelDeal Admin Setup Script"
echo "=================================="

# Check if PostgreSQL is running
echo "📊 Checking PostgreSQL connection..."
if ! pg_isready -h localhost -p 5432 -U postgres; then
    echo "❌ PostgreSQL is not running. Please start PostgreSQL first."
    exit 1
fi

# Check if database exists
echo "🔍 Checking if database exists..."
if ! psql -h localhost -U postgres -lqt | cut -d \| -f 1 | grep -qw carrental; then
    echo "📝 Creating database..."
    psql -h localhost -U postgres -c "CREATE DATABASE carrental;"
    psql -h localhost -U postgres -c "CREATE USER carrental WITH PASSWORD 'carrental123';"
    psql -h localhost -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE carrental TO carrental;"
fi

echo "✅ Database setup complete!"

echo ""
echo "🔐 Default Admin Credentials:"
echo "   Email: admin@wheeldeal.com"
echo "   Password: admin123"
echo ""
echo "👨‍💼 Default Manager Credentials:"
echo "   Email: manager@wheeldeal.com"
echo "   Password: manager123"
echo ""
echo "👤 Default Customer Credentials:"
echo "   Email: customer@wheeldeal.com"
echo "   Password: password"
echo ""

echo "🚀 Starting backend application..."
echo "   The admin user will be created automatically when the application starts."
echo "   You can then login using the credentials above."
echo ""

# Start the backend application
cd backend
./mvnw spring-boot:run 