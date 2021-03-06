# Secret Santa [![CI-Build](https://github.com/MrTimeey/secret-santa/actions/workflows/ci-build.yml/badge.svg)](https://github.com/MrTimeey/secret-santa/actions/workflows/ci-build.yml) [![Publish Docker image](https://github.com/MrTimeey/secret-santa/actions/workflows/publish_docker_image.yml/badge.svg)](https://github.com/MrTimeey/secret-santa/actions/workflows/publish_docker_image.yml) 

The-Secret-Santa should help groups to organize their secret-santa groups while they can't meet in person. 
Besides that it's a learning project for various technologies.


## Local development
For local development you need to execute the following steps.
### Backend
1. Create '.env' file in root directory
```shell
MONGO_PASS=...
MONGO_ROOT_PASS=...
SPRING_MAIL_PASS=...
```
2. Start mongo db via docker-compose
```shell
docker-compose --file docker-compose.yml --file docker-compose-local.yml up -d mongo-database
```
3. Run maven clean install
```shell
mvn clean install
```
4. Start application
4.1 Plain start
```shell
java -Dspring.profiles.active=local -jar  secret-santa-service/target/secret-santa-service-0.1.0-SNAPSHOT.jar
```
4.2 Start with setting passwords
```shell
java -Dspring.profiles.active=local --MONGODB_PASSWORD=<pass> --MAIL_PASSWORD=6wE$hk=<pass>  -jar  secret-santa-service/target/secret-santa-service-0.1.0-SNAPSHOT.jar
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

### Backend standalone

1. Start backend standalone via docker-compose

```shell
docker-compose --file docker-compose.yml --file docker-compose-local.yml up secret-santa-service
```

## Maven profiles

| Profile       | Description   |
| ------------- |:-------------:|
| mutationTest  | Run mutation tests with [pitest](https://pitest.org/). Report is generated in target/pit-report. | 
