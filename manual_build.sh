rm -rf ./docker
gradlew clean
gradlew build -x test
mkdir docker
cp ./build/libs/judsue-one.jar ./docker/app.jar
cd docker
jar -xf ./app.jar
cd ..

docker build -t oat431/judsuebe:latest .
docker push oat431/judsuebe:latest