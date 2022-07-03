#!/bin/bash
# setup.sh

# apt update, install dependency
sudo apt-get update
sudo apt-get install default-jdk xmlstarlet tomcat9 git -y

# make war file
git config --global user.email anjm1020@gmail.com
git config --global user.name jaemin
git clone https://github.com/BiBimBapXOpenStack/team1-imageboard.git
cd team1-imageboard
./gradlew build -x check

# place war file to tomcat directory
cd ~/../../var/lib/tomcat9/webapps
sudo rm -rf ROOT
sudo rm -rf ROOT.war
cd ~/team1-imageboard/build/libs
sudo mv imageBoard.war ~/../../var/lib/tomcat9/webapps/ROOT.war

# configure tomcat port
cd ~/../../etc/tomcat9
sudo xmlstarlet ed --inplace --update "/Server/Service/Connector/@port" -v 80 server.xml

# tomcat start
sudo systemctl restart tomcat9