FROM  --platform=linux/amd64 amazoncorretto:20.0.2-alpine
# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory of the host to the container's working directory
COPY target/gitcolab-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application listens on (change this if your application uses a different port)
#EXPOSE ${PORT:-8080}

# Command to run your Spring Boot application using the JAR file
CMD ["java", "-jar", "app.jar"]