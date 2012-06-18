@ECHO OFF
FOR /F "usebackq delims=" %%i IN (`lein classpath`) DO SET CPATH=%%i
java -cp %CPATH% org.h2.tools.Server