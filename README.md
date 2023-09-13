# Cash Depot Bot

## Overview

Cash Depot Bot is a Java automation project that leverages Selenium WebDriver for web scraping. The main objective is to automatically fetch CSV files from a [designated website](https://webmon.cashdepotplus.com) and place them in a shared directory for accounting needs. The project uses Gradle and its wrapper (`gradlew`) for dependency management and build processes.

## Pre-requisites

- Java 19 SDK
- Git
- Optional: IntelliJ IDEA or any preferred Java IDE

## Setup & Execution

### Method 1: Using Gradle Wrapper from Command Line

#### 1. Clone the Repository
```bash
git clone git@github.com:ericdwkim/cash-depot-bot.git
```

#### 2. Navigate to Project Directory
```bash
cd cash_depot_bot
```

#### 3. Run Gradle Clean and Build
```bash
./gradlew clean build shadowJar
```
> **Note**: For verbose warning logs, append `--warning-mode all`.

#### 4. Execute the Application
```bash
java -jar build/libs/cash_depot_bot-0.1.0.jar
```
> **Note**: When the Uber JAR is created, it auto versions which is an indication that it has updated it from a regular JAR to include depedencies defined in `build.gradle`

### Method 2: Using IntelliJ IDEA

#### 1. Clone and Open Project
Clone the repo and open it using IntelliJ IDEA.

#### 2. Configure Build Settings
- Set SDK to Java 19
- Classpath: `cash_depot_bot.main`
- Main Class: `com.txb.cashdepot.CashDepotReportPull`
- Working Directory: `/Users/ekim/workspace/personal/cash_depot_bot`

#### 3. Run the Application
Use IntelliJ's run/debug configurations.

### Method 3: Shell Script Execution

#### 1. Clone Repository
```bash
git clone git@github.com:ericdwkim/cash-depot-bot.git
```

#### 2. Navigate to Project
```bash
cd cash_depot_bot
```

#### 3. Make Script Executable
```bash
chmod +x scripts/app.sh
```

#### 4. Run Script
```bash
./scripts/app.sh
```

## Final Thoughts
Choose your preferred method based on your workflow. The Gradle Wrapper streamlines dependency management and builds, IntelliJ offers robust IDE support, and the shell script simplifies execution.

## Platform-Specific Notes

### Windows vs Linux/Unix

- **ChromeDriver Pathing**
    - Windows: `c:\\chromedriver-win64\\` (Note: May require double backslashes)
    - Linux/Unix: `/opt/homebrew/bin/chromedriver`

- **Java Executable Path**
    - Windows: `JAVA_EXEC="/c/Program Files/Java/jdk-17/bin/java.exe"`
    - Mac: `JAVA_EXEC="/usr/bin/java"`

## Project Structure

- `builds`: Gradle build files
- `build/libs/`: Contains the Uber JAR
- `build/classes/java/main/com/txb/cashdepot`: Compiled source files

> **Note**: If you're confused about why the `Main-Class` in the MANIFEST doesn't match the `.class` file paths, it's because the `build.classes.java.main` directories are abstracted away during the initial build.

For any additional queries, consult the documentation or reach out to the development team.