#!/bin/bash

echo "Starting Car Rental System Backend..."
echo "======================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java 21 or higher."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "Error: Java 21 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

# Check if PostgreSQL is running
if ! command -v psql &> /dev/null; then
    echo "Warning: PostgreSQL client not found. Make sure PostgreSQL is installed and running."
fi

echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Starting Spring Boot application..."
echo ""

# Start the application
java -jar target/backend-0.0.1-SNAPSHOT.jar

