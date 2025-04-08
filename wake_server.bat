@echo off
setlocal enabledelayedexpansion

set URL=https://transport-system-backend.onrender.com/api/auth/signin
set DATA={"username":"ts_wakener","password":"ts_wakener"}

:retry
echo Sending request to server...

curl -s -o nul -X POST -H "Content-Type: application/json" -d "%DATA%" "%URL%"
if %errorlevel% equ 0 (
    echo Got response from server. Exiting.
    goto end
)

echo No response from server yet. Retrying in 10 seconds...
timeout /t 10 > nul
goto retry

:end