FROM gradle:latest as BUILD

COPY build.gradle gradle.properties settings.gradle /src
WORKDIR /src
RUN ./gradlew --no-daemon classLib:assemble
RUN ./gradlew --no-daemon classLibDTO:assemble
RUN ./gradlew --no-daemon stub:assemble
RUN ./gradlew --no-daemon database:assemble
RUN ./gradlew --no-daemon dataContract:assemble
RUN ./gradlew --no-daemon api:assemble

FROM openjdk:latest

COPY --from=BUILD /src/api/build/libs/api.jar /bin/runner/run.jar
WORKDIR /bin/runner

EXPOSE 1705
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","run.jar"]
