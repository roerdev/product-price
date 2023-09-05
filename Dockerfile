FROM maven:3.8.1-openjdk-11-slim as maven
WORKDIR /app
COPY ./product-price/pom.xml /app/
COPY ./product-price/src /app/src
RUN mvn clean package

FROM openjdk:11.0.2-jre-slim
ENV TZ='Europe/Paris'
WORKDIR /app
COPY --from=maven /app/target/*.jar /app/
EXPOSE 8080
CMD find *.jar -exec java -jar $JAVA_OPTS -XshowSettings:vm {} ';'
