@echo off
rem Change to current directory
cd /d %~dp0

cls

rem [�ϵ����]According to the plugin configuration, the tomcat7 runs in Maven JVM environment, so appending JPDA debug options to MAVEN_OPTS will work.
set MAVEN_OPTS=%MAVEN_OPTS% -Xms256m -Xmx768m -XX:PermSize=128M -XX:MaxNewSize=512m -XX:MaxPermSize=512m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=38788

rem set MAVEN_OPTS=%MAVEN_OPTS% -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=18080 -Dcom.sun.management.jmxremote.authenticate=false -Djava.net.preferIPv4Stack=true

:START
cls
rem Run the [tomcat7:run] in Parent POM.xml directory. Now reloading changes from child projects without restalling is possible.
title web-performance
call mvn clean tomcat7:run -Dmaven.test.skip=true 
echo.
rem set /p var=Press ENTER to restart, OR click on the right top red button of the window.
set /p var=Press ENTER to restart, OR click on the right top red button of the window.
GOTO :START

:ERR
echo.
echo. EIM Project Installation has failed. See above for details.
pause>nul
