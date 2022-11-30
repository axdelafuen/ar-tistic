FROM androidsdk/android-30 as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew database:assemble

FROM androidsdk/android-30

COPY --from=BUILD /src/database/build/libs/database.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]