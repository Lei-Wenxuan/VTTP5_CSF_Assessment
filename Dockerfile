FROM node:23 AS ngbuild

WORKDIR /src

RUN npm i -g @angular/cli

COPY client/*.json .
COPY client/public public
COPY client/src src

RUN npm i
RUN npm ci
RUN ng build

FROM eclipse-temurin:23-jdk AS javabuild

WORKDIR /src

COPY server/mvnw .
COPY server/pom.xml .
COPY server/.mvn .mvn
COPY server/src src

COPY --from=ngbuild /src/dist/client/browser/ src/main/resources/static

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=javabuild /src/target/server-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar