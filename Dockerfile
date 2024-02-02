FROM openjdk:17
COPY ./build/libs/auction-0.0.1-SNAPSHOT.jar auction.jar
ENTRYPOINT ["java", "-jar", "auction.jar"]
