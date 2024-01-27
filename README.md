1. Start with f1Game-template.xlsx in src/main/resources
2. Update Player1 and Player2 details, and update race names/numbers as needed

**To run locally:** 
 - Copy the template to somewhere on your local machine
 - Update application.yml to point to the local game copy
 - Pass in season and race args as appropriate

**To run via Docker:**
 - Copy teh template to somewhere on your local machine
 - Mvn clean+install and build Docker image using Dockerfile
 - Mount your local game file to /tmp when executing Docker run