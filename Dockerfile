# Stage 1: Build the application using Maven
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Deploy the application to Tomcat
FROM tomcat:9.0

# The maintainer of the image
LABEL maintainer="mohan18.welcome@gmail.com"

# Copy the WAR file to the webapps directory of Tomcat
COPY --from=build /app/target/demo-webapp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/demo-webapp.war

# Expose the port on which Tomcat is running
EXPOSE 8080

# Command to run the Tomcat server
CMD ["catalina.sh", "run"]
