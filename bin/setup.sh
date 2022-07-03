#!/bin/bash
# setup.sh
# @param $1=db_url, $2=db_username, $3=db_password, $4=jwt_secret

# apt update, install dependency
sudo apt-get update
sudo apt-get install default-jdk xmlstarlet tomcat9 git -y

# set config and build
git config --global user.email anjm1020@gmail.com
git config --global user.name jaemin
git pull origin develop
cd
cd team1-imageboard/bin/
sudo chmod 755 *.sh
cd
cd team1-imageboard/src/main/resources/
sudo echo spring.datasource.url=$1 >> application.properties
sudo echo spring.datasource.username=$2 >> application.properties
sudo echo spring.datasource.password=$3 >> application.properties
sudo echo jwt.secret=$4 >> application.properties
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