# Start with a base image containing Java runtime
FROM adoptopenjdk:11-jre-hotspot

# Add Maintainer Info
LABEL maintainer="keseljstrahinja@gmail.com"

# The application's jar file
ARG JAR_FILE=target/foodStoreSearch-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} foodStoreSearch-0.0.1-SNAPSHOT.jar

# Add the data folder to the container
COPY data src/main/resources/

COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh


ENTRYPOINT ["./wait-for-it.sh", "elasticsearch:9200", "--timeout=30", "--", "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "foodStoreSearch-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080