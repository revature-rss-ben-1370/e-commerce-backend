#Building stage, to build the jar file
#I require a Maven image to be able to build a maven project!

FROM maven:3.8.5-openjdk-8 AS MAVEN_BUILD_STAGE

#Copy over all the files inside of our directory!
COPY ./ ./

#Set environmental variables for test cases
ARG DB_PLATFORM=org.hibernate.dialect.H2Dialect
ARG DB_URL=jdbc:h2:mem:test;MODE=PostgreSQL
ARG DB_DRIVER=org.h2.Driver

# cleans the project and makes the shaded jar
RUN mvn clean package # -Dmaven.test.skip=true


#Distributable lightweight image for running the jar file
FROM openjdk

copy --from=MAVEN_BUILD_STAGE ./target/e-commerce-1.0.jar /workspace/e-commerce-1.0.jar

WORKDIR /workspace

EXPOSE 5000

ENTRYPOINT [ "java", "-jar", "e-commerce-1.0.jar" ]
