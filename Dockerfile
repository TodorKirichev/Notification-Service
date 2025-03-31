FROM amazoncorretto:21

COPY target/emailNotification-svc-*.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]