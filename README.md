# IRC-Client
* Name: Brandon Briseno & Alfonso Castanos
* SID: 1932124 & 2283681
* Class: CPSC 353-02 (Dr. Fahy)
* Protocol: https://tools.ietf.org/html/rfc2812#section-3.1.5

# Program Description
This is an Internet Relay Chat client program which should follow the following protocol:
*  1)Get identifity data
*  2)Attempt connection to IRC Server
*  3)Establish socket connection
*  4)Send server data
*  5)Wait for PING msg from server to the client
*  6)PONG msg back to the server from the client
*  7)Create/join chatroom
*  8)Self promotion
*  9)Listen for commands
*  10)/quit on command or kick
Currently, we are working on two parts: the bot client and the chat client. The webclient listed below is currently only for debugging and prototyping. The final result will hopefully be able to be tested completely in console.

## Instructions:
1) Compile: ```javac -classpath pircbot.jar;. *.java```
2) Run: ```java -classpath pircbot.jar;. BotMain```
3) Test: https://client00.chat.mibbit.com/?channel=%23TeamSameTeam&server=irc.synirc.net
 * type: ```time```


## Checkstyles:
```java -jar checkstyle-8.8-all.jar -c google_checks.xml *.java```
