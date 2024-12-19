# Use openjdk 17 as the base image
FROM openjdk:17

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Copy the built JAR file from your target directory into the container
COPY target/receiptprocessor-0.0.1-SNAPSHOT.jar receiptprocessor.jar

# Run the Spring Boot application using java -jar
ENTRYPOINT ["java", "-jar", "/receiptprocessor.jar"]
