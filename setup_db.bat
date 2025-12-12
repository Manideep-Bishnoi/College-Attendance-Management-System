@echo off
echo [INFO] Setting up database...
echo Please enter your MySQL root password when prompted.
mysql -u root -p < database_setup.sql
if %errorlevel% neq 0 (
    echo [ERROR] Database setup failed. Make sure 'mysql' is in your system PATH.
    echo If it fails, you can manually import 'database_setup.sql' using MySQL Workbench.
) else (
    echo [SUCCESS] Database setup completed!
)
pause
