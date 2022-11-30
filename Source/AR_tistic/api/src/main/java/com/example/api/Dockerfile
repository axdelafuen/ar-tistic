FROM gradle:latest as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew classLib:assemble
RUN ./gradlew classLibDTO:assemble
RUN ./gradlew database:assemble
RUN ./gradlew dataContract:assemble
RUN ./gradlew api:assemble

FROM openjdk:latest

COPY --from=BUILD /src/api/build/libs/api.jar /bin/runner/run.jar
WORKDIR /bin/runner

EXPOSE 1705
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","run.jar"]