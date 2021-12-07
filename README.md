# JavaChallenge
App accepts POST messages containing fields "id" as integer and "message" as string. 
App returns total number of words from all received messages. New entries with a non-unique id will be ignored. 

To deploy: Copy "WordCountProject.war" to webapps directory of server.

To use: using POSTMAN or similar tool, access http://localhost:8080/WordCountProject/AppServlet as base url. 
Add post message as desired.
