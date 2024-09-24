@echo off
set JAVA_FILE=ZtrolixLibsDownloader.java
set CLASS_FILES=ZtrolixLibsDownloader*.class
set MANIFEST_FILE=manifest.txt
set JAR_FILE=ZLibsInstaller.jar
set FLATLAF_JAR=flatlaf-3.5.1.jar

echo Compiling %JAVA_FILE%...
javac -cp %FLATLAF_JAR% -Xlint:deprecation %JAVA_FILE%
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Creating JAR file %JAR_FILE%...
echo Main-Class: ZtrolixLibsDownloader > %MANIFEST_FILE%
jar cfm %JAR_FILE% %MANIFEST_FILE% %CLASS_FILES%
if %errorlevel% neq 0 (
    echo Failed to create JAR file!
    pause
    exit /b %errorlevel%
)

echo Running %JAR_FILE%...
java -cp "%JAR_FILE%;%FLATLAF_JAR%" ZtrolixLibsDownloader
if %errorlevel% neq 0 (
    echo Failed to run JAR file!
    pause
    exit /b %errorlevel%
)

echo Cleaning up...
del %CLASS_FILES%
del %MANIFEST_FILE%
echo Done!
pause