How to run ACEM as a user (not as a developer) with Windows
===========================================================

Note: these are just the "how to launch" steps, the "how to download" and "how to configure" are yet to be written.

1. Open the file explorer (Windows key + E)
2. Go to the folder where you installed the Neo4J database, presumably "ACEM"
3. Go to folder neo4j-community-X.Y.Z\bin
4. Double click on "Neo4j", two empty black windows open, one should close automatically after a few seconds and the other one should remain opened (leave this window open until you have finished using ACEM)
5. Go to the folder where you installed Apache Tomcat 7, presumably "ACEM"
6. Go to folder apache-tomcat-X.Y.Z\bin
7. Double click on "startup" and wait until you see "Startup time: XXXX ms" (can take about one minute)
8. Launch a web browser (e.g. Firefox)
9. Go to http://localhost:8080/ACEM-web-jsf-servlet