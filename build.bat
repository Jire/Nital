@echo off
@title Building Nital Server
javac -cp src;lib/netty-3.2.4.Final.jar -d bin/ src/us/nital/Server.java
pause
exit