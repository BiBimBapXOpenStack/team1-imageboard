#!/bin/bash
# pull.sh

cd team1-imageboard
git fetch --all
git reset --hard origin/develop
git pull origin develop
../gradlew build -x check
cd ~/../../var/lib/tomcat9/webapps
sudo rm -rf ROOT
sudo rm -rf ROOT.war
cd ~/team1-imageboard/build/libs
sudo mv imageBoard.war ~/../../var/lib/tomcat9/webapps/ROOT.war
sudo systemctl restart togimcat9
