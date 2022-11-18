FROM androidsdk/android-30 as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew api:assemble

FROM androidsdk/android-30

COPY --from=BUILD /src/api/build/libs/api.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]