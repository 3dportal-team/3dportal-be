FROM openjdk:11-jre
WORKDIR /app
RUN useradd -ms /bin/bash portal
USER portal

COPY target/dependency /app/dependency
COPY target/gateway-api-*.jar /app/

ENTRYPOINT ["java", "-cp", "./*:./dependency/*", "org.springframework.boot.loader.JarLauncher"]