FROM gradle:latest as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon classLib:assemble classLibDTO:assemble stub:assemble database:assemble dataContract:assemble api:assemble

FROM openjdk:latest

COPY --from=BUILD /src/api/build/libs/api.jar /bin/runner/run.jar
WORKDIR /bin/runner

EXPOSE 1705
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","run.jar"]
