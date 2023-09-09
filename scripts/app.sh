#!/bin/bash


# Display the current working directory
echo "Current working directory: $(pwd)"

# Path to Java executable (WSL-style)

JAVA_EXEC="/usr/bin/java"

JAR_PATH="/Users/ekim/workspace/personal/cash_depot_bot/CashDepot.jar"

SELENIUM_JARS_PATH="/Users/ekim/workspace/selenium-java-4.12.1/*"


# Get parent dir from jar path
PROJECT_DIR="$(dirname "$JAR_PATH")"

echo "Changing to project dir as working directory"
# shellcheck disable=SC2164
cd "$PROJECT_DIR"

# Change dirs
#echo "Changing to src dir as working directory"

#SRC_DIR="$PROJECT_DIR/src"
# shellcheck disable=SC2164
#cd "$SRC_DIR"

# Confirm changed to correct working dir prior to execution
echo "Current working directory: $(pwd)"

# Display a message indicating the script is starting
echo "Running CashDepot script..."

# Run the Java application with the Selenium WebDriver JARs in the classpath
"$JAVA_EXEC" -cp "$SELENIUM_JARS_PATH" -jar "$JAR_PATH"

# Display a message indicating the script has finished
echo "CashDepot script completed."

# Uncomment the line below to add a pause (optional)
read -p "Press Enter to exit..."

