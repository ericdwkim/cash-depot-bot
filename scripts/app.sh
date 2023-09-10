#!/bin/bash
# Display a message indicating the script is starting
echo "Running CashDepot script..."

# Display the current working directory
echo "Current working directory: $(pwd)"

# Path to Gradlew
GRADLEW_PATH="/Users/ekim/workspace/personal/cash_depot_bot/gradlew"
echo "Using gradlew in: "$GRADLEW_PATH""

# Path to Java executable
JAVA_EXEC="/usr/bin/java"
echo "Using java.exe in: "$JAVA_EXEC""

# Path to the generated JAR file
JAR_PATH="/Users/ekim/workspace/personal/cash_depot_bot/build/libs/cash_depot_bot-0.1.0.jar"
echo "Using main uber JAR in: "$JAR_PATH""

# Run Gradle clean and build tasks
echo "Running Gradle clean and build..."
cd /Users/ekim/workspace/personal/cash_depot_bot
"$GRADLEW_PATH" clean build shadowJar

# Check if the build was successful
if [ $? -eq 0 ]; then
  # Run the Java application
  echo "Running the Java application..."
  "$JAVA_EXEC" -jar "$JAR_PATH"

  # Display a message indicating the script has finished
  echo "CashDepot script completed."
else
  echo "Gradle build failed. Exiting..."
  exit 1
fi

# Uncomment the line below to add a pause (optional)
read -p "Press Enter to exit..."
