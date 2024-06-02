@echo off
REM Set the Java file and manifest file names
set JAVA_FILE=ZtrolixLibsDownloader.java
set CLASS_FILES=ZtrolixLibsDownloader*.class
set MANIFEST_FILE=manifest.txt
set JAR_FILE=ZtrolixLibsDownloader.jar

REM Compile the Java file with deprecation warnings
echo Compiling %JAVA_FILE%...
javac -Xlint:deprecation %JAVA_FILE%
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

REM Create the JAR file
echo Creating JAR file %JAR_FILE%...
echo Main-Class: ZtrolixLibsDownloader > %MANIFEST_FILE%
REM Include all .class files, including inner classes
jar cfm %JAR_FILE% %MANIFEST_FILE% %CLASS_FILES%
if %errorlevel% neq 0 (
    echo Failed to create JAR file!
    pause
    exit /b %errorlevel%
)

REM Run the JAR file
echo Running %JAR_FILE%...
java -jar %JAR_FILE%
if %errorlevel% neq 0 (
    echo Failed to run JAR file!
    pause
    exit /b %errorlevel%
)

REM Clean up
echo Cleaning up...
del %CLASS_FILES%
del %MANIFEST_FILE%
echo Done!
pause