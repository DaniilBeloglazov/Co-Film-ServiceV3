First of all:
* ```mkdir -m777 -p {data/images/film,data/images/person,data/images/person/photo}```
### Local deployment:
* ```./mvnw spring-boot:run -Dspring.profiles.active=local -Dmaven.test.skip=true```
### Containerized deployment:
* ```./mvnw package -Dspring.profiles.active=docker -Dmaven.test.skip=true```
* ```docker compose up```

[//]: # (./mvnw package -Dspring.profiles.active=docker -Dmaven.test.skip)

[//]: # (java -jar application.jar --spring.profiles.active=local)

[//]: # ()
[//]: # (Nginx in docker example: http://localhost:5031/film/36c12a88-8080-472f-bb5f-e52d7a6702e7.jpeg)