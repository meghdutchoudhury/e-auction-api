FROM adoptopenjdk/openjdk11:alpine-jre
RUN apk --no-cache add curl
COPY build/libs/e-auction-query-api-1.0.0.jar e-auction-query-api-1.0.0.jar
ENTRYPOINT ["java","-jar","/e-auction-query-api-1.0.0.jar"]