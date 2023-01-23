FROM openjdk:17
WORKDIR app/

RUN mkdir -m777 -p {images/person/photo,images/film}

VOLUME /app/images

COPY ./target/Co-Film-ServiceV3-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "Co-Film-ServiceV3-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]