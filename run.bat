@echo off
setlocal

:: Check for lib folder
if not exist "lib" mkdir lib

:: Check for MySQL Connector
dir "lib\mysql-connector-*.jar" >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] MySQL Connector JAR not found in 'lib' folder!
    echo -----------------------------------------------------------
    echo 1. Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
    echo 2. Extract the ZIP.
    echo 3. Copy the 'mysql-connector-j-x.x.x.jar' file into the 'lib' folder of this project.
    echo -----------------------------------------------------------
    pause
    exit /b
)

:: Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

echo [INFO] Compiling...
javac -cp "lib\*" -d bin src\AttendanceManager.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation Failed!
    pause
    exit /b
)

echo [INFO] Running Attendance Manager...
java -cp "bin;lib\*" AttendanceManager
pause
