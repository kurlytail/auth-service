FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/auth-service
COPY ${DEPENDENCY} /app
ENTRYPOINT ["java","-cp","/app/WEB-INF/lib/*:/app/WEB-INF/classes/.","com.bst.auth.service.application.Application"]