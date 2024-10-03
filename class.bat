@echo off
title Class Converter
cls

:start
set /p "id=Class: "
echo Converting File...

set "classname=%id:.class=%"
javap -c %id% 2> error.log | findstr /V "Compiled from" > %classname%.java

if %errorlevel% neq 0 (
    echo An error occurred during conversion. Check the details below:
    type error.log
) else (
    echo Converted File!
    del error.log
)

pause
cls
goto start