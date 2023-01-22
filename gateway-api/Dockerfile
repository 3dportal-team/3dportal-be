FROM openjdk:11-jre
WORKDIR /app

COPY target/dependency /app/dependency
COPY target/gateway-api-*.jar /app/

ENTRYPOINT ["java", "-cp", "./*:./dependency/*", "org.springframework.boot.loader.JarLauncher"]