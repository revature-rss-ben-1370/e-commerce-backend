#Distributable lightweight image for running the jar file
FROM openjdk:11-jre

WORKDIR /target

#EXPOSE 5000

ENTRYPOINT [ "java", "-jar", "e-commerce-1.0.jar" ]
