@echo off 
echo DP0: %~dp0
echo current path:
cd
java8 -jar %~dp0\message-send-1.0-SNAPSHOT.jar %*