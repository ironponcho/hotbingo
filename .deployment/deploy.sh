# shellcheck disable=SC2164

echo ---------------------
echo --- build app.jar ---
echo ---------------------
cd ..
mvn clean install -DskipTests
echo --------------------------------------
echo --- Copy app.jar to S3-Artifactory ---
echo --------------------------------------
aws s3 cp /home/jonas/.m2/repository/technology/scholz/backend/0.0.1-SNAPSHOT/backend-0.0.1-SNAPSHOT.jar s3://scholz.technology/bingo/
cd .deployment
echo --------------------------
echo --- Run app.jar on EC2 ---
echo --------------------------
chmod 400 UbuntuServer1804_EC2.pem
ssh -i UbuntuServer1804_EC2.pem ubuntu@ec2-3-123-153-255.eu-central-1.compute.amazonaws.com
echo Downloading app.jar from s3
curl https://s3.eu-central-1.amazonaws.com/scholz.technology/bingo/app.jar --output app.jar
echo Starting App
sudo kill -9 $(sudo lsof -t -i:8080)
java -jar app.jar >> log.txt & disown
exit
