FROM openjdk:14-alpine
COPY build/libs/*.jar TourGuideApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TourGuideApplication.jar"]