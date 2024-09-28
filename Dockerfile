FROM openjdk:17
COPY build/libs/etaskify.jar etaskify.jar
ENTRYPOINT ["java","-jar","etaskify.jar"]