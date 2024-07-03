# Use an official Tomcat runtime as a parent image
FROM tomcat:9.0

# The maintainer of the image
LABEL maintainer="mohan18.welcome@gmail.com"

# Copy the WAR file to the webapps directory of Tomcat
COPY target/demo-webapp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/demo-webapp.war

# Expose the port on which Tomcat is running
EXPOSE 8080

# Command to run the Tomcat server
CMD ["catalina.sh", "run"]
