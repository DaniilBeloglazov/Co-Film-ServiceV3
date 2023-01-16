After successfull deployment you can access [Swagger](http://localhost:5030/swagger-ui/index.html) for API information.
---
### 1. First of all:
#### Clone repository.
```
git clone https://github.com/DaniilBeloglazov/Co-Film-ServiceV3.git && cd Co-Film-ServiceV3
```
#### Make directories for serving static files.
```
cd / && mkdir -m777 -p {data/images/film,data/images/person,data/images/person/photo} && cd -
```
#### Upload Nginx configuration to your locally installed nginx.
```
sudo cp nginx.conf /etc/nginx/sites-enabled/static-serving.conf && sudo nginx -s reload
```
### 2. Deployment:
#### Local:
```
./mvnw spring-boot:run -Dspring.profiles.active=local -Dmaven.test.skip=true
```
#### Docker:
```
./mvnw package -Dspring.profiles.active=docker -Dmaven.test.skip=true && docker compose up
```

[//]: # (./mvnw package -Dspring.profiles.active=docker -Dmaven.test.skip)

[//]: # (java -jar application.jar --spring.profiles.active=local)

[//]: # ()
[//]: # (Nginx in docker example: http://localhost:5031/film/36c12a88-8080-472f-bb5f-e52d7a6702e7.jpeg)
