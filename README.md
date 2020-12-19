# Secret Santa [![Build Status](https://travis-ci.org/MrTimeey/secret-santa.svg?branch=master)](https://travis-ci.org/MrTimeey/secret-santa)

The-Secret-Santa should help groups to organize their secret-santa groups while they can't meet in person. 
Besides that it's a learning project for various technologies.


## Local development
For local development you need to execute the following steps.
### Backend
1. Start mongo db via docker-compose
```shell
docker-compose --file docker-compose.yml --file docker-compose-local.yml up -d mongo-database
```
3. Run maven clean install
```shell
mvn clean install
```
4. Start application
```shell
java -Dspring.profiles.active=local -jar  secret-santa-service/target/secret-santa-service-0.1.0-SNAPSHOT.jar
```

### Frontend
1. Install dependencies
```shell
npm --prefix secret-santa-ui/ install
```
2. Start application
```shell
npm --prefix secret-santa-ui/ run serve
```
