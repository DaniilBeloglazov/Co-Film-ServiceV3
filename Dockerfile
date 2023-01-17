FROM openjdk:17
WORKDIR app/
RUN mkdir -p -m777 {/app/images/person/photo,/app/images/film}

VOLUME /app/images

COPY ./target/Co-Film-ServiceV3-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "Co-Film-ServiceV3-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker-db"]