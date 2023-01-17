After successfull deployment you can access [Swagger-UI](http://localhost:5030/swagger-ui/index.html) for API information.
---
### 1. First of all:
#### Clone repository.
```
git clone https://github.com/DaniilBeloglazov/Co-Film-ServiceV3.git && cd Co-Film-ServiceV3
```
#### Make directories for serving static files.
```
sudo mkdir -m777 -p {~/images/film,~/images/person,~/images/person/photo}
```
#### Upload Nginx configuration to your locally installed nginx.
```
sudo cp nginx.conf /etc/nginx/sites-enabled/static-serving.conf && sudo nginx -s reload
```
### 2. Deployment:
#### Local:
1. Build & Run Jar file
```
./mvnw spring-boot:run -Dspring.profiles.active=local -Dmaven.test.skip=true
```
#### Docker:
1. Remove local nginx configuration to avoid errors with port binding.
```
sudo rm -f /etc/nginx/sites-enabled/static-serving.conf && sudo nginx -s reload
```
2. Build Jar file and run application inside Docker
```
./mvnw package -Dspring.profiles.active=docker -Dmaven.test.skip=true && docker compose up
```

[//]: # (./mvnw package -Dspring.profiles.active=docker -Dmaven.test.skip)

[//]: # (java -jar application.jar --spring.profiles.active=local)

[//]: # ()
[//]: # (Nginx in docker example: http://localhost:5031/film/36c12a88-8080-472f-bb5f-e52d7a6702e7.jpeg)
