@echo off
@title Nital Server - Created by Thomas Nappo
java -Xmx512m -Xms512m -cp lib/*;bin; us.nital.Server
pause
exit