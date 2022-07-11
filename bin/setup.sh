#!/bin/bash
# setup.sh
# @param $1=db_url, $2=db_username, $3=db_password, $4=jwt_secret

REPO_DIR=/home/ubuntu/team1-imageboard
TOMCAT_WEBAPPS_DIR=/var/lib/tomcat9/webapps

echo "==========================================="
echo "1. Install Dependency"
echo "==========================================="
sudo apt-get update
sudo apt-get install default-jdk xmlstarlet tomcat9 git -y

echo "==========================================="
echo "2. Repository Check, Git Configure, Pull"
echo "==========================================="
[ -d team1-imageboard ] || git clone https://github.com/BiBimBapXOpenStack/team1-imageboard.git 
cd $REPO_DIR
git fetch --all
git reset --hard origin/develop
git pull origin develop

echo "==========================================="
echo 3. Make Spring Configuration file
echo "==========================================="
cd $REPO_DIR/src/main/resources/
[ -f application.properties ] && rm application.properties
sudo echo spring.datasource.url=$1 >> application.properties
sudo echo spring.datasource.username=$2 >> application.properties
sudo echo spring.datasource.password=$3 >> application.properties
sudo echo jwt.secret=$4 >> application.properties
cat application.properties

echo "==========================================="
echo "4. Build"
echo "==========================================="
cd $REPO_DIR
./gradlew build -x check

echo "==========================================="
echo "5. Set war file to Tomcat"
echo "==========================================="
cd $TOMCAT_WEBAPPS_DIR
sudo rm -rf ROOT
sudo rm -rf ROOT.war
cd $REPO_DIR/build/libs
sudo mv imageBoard.war $TOMCAT_WEBAPPS_DIR/ROOT.war
cd $TOMCAT_WEBAPPS_DIR
echo "Directory : tomcat9/webapps"
ls

echo "==========================================="
echo "6. Set Tomcat Port"
echo "==========================================="
cd /etc/tomcat9
sudo xmlstarlet ed --inplace --update "/Server/Service/Connector/@port" -v 80 server.xml
echo "Tomcat Port"
sudo xmlstarlet sel -T -t -v "/Server/Service/Connector/@port" server.xml
echo

echo "==========================================="
echo "7. Tomcat Restart"
echo "==========================================="
sudo systemctl restart tomcat9
echo "Success - Tomcat Restart"
