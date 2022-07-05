#!/bin/bash
# setup.sh
# @param $1=db_url, $2=db_username, $3=db_password, $4=jwt_secret

echo ===========================================
echo 1. Install Dependency
echo ===========================================
sudo apt-get update
sudo apt-get install default-jdk xmlstarlet tomcat9 git -y

echo ===========================================
echo 2. Git Configure, Pull
echo ===========================================
git config --global user.email anjm1020@gmail.com
git config --global user.name jaemin
cd ~/team1-imageboard/
git fetch --all
git reset --hard origin/develop
git pull origin develop

echo ===========================================
echo 3. Make Spring Configuration file
echo ===========================================
cd ~/team1-imageboard/src/main/resources/
if [ -f application.properties ]
then  
  rm application.properties
fi
sudo echo spring.datasource.url=$1 >> application.properties
sudo echo spring.datasource.username=$2 >> application.properties
sudo echo spring.datasource.password=$3 >> application.properties
sudo echo jwt.secret=$4 >> application.properties
cat application.properties

echo ===========================================
echo 4. Build
echo ===========================================
cd ~/team1-imageboard/
./gradlew build -x check

echo ===========================================
echo 5. Set war file to Tomcat
echo ===========================================
cd ~/../../var/lib/tomcat9/webapps
sudo rm -rf ROOT
sudo rm -rf ROOT.war
cd ~/team1-imageboard/build/libs
sudo mv imageBoard.war ~/../../var/lib/tomcat9/webapps/ROOT.war
cd ~/../../var/lib/tomcat9/webapps/
echo <webapps>
ls

echo ===========================================
echo 6. Set Tomcat Port
echo ===========================================
cd ~/../../etc/tomcat9
sudo xmlstarlet ed --inplace --update "/Server/Service/Connector/@port" -v 80 server.xml

echo ===========================================
echo 7. Tomcat Restart
echo ===========================================
sudo systemctl restart tomcat9
