@echo off
set JAVA_FILE=ZtrolixLibsDownloader.java
set CLASS_FILES=ZtrolixLibsDownloader*.class
set MANIFEST_FILE=manifest.txt
set JAR_FILE=ZtrolixLibsInstaller.jar

echo Compiling %JAVA_FILE%...
javac -Xlint:deprecation %JAVA_FILE%
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
java -jar %JAR_FILE%
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