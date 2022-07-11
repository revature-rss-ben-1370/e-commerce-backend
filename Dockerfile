#Distributable lightweight image for running the jar file
FROM openjdk:11-jre

# WORKDIR ./target
COPY /target/e-commerce-1.0.jar e-commerce-1.0.jar
#EXPOSE 5000

ENTRYPOINT [ "java", "-jar", "./target/e-commerce-1.0.jar" ]
