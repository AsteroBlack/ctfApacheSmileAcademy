# Stage 1 : Construction de l'application
FROM maven:3.6.3-openjdk-8 AS build

LABEL AUTHOR="TIGOLI FREDERIC"
LABEL VERSION="1.0"

WORKDIR /api
COPY pom.xml ./

COPY src ./src

# Télécharger les dépendances et construire le package
RUN mvn clean install 

# Stage 2 : Déploiement sur Tomcat
FROM tomcat:9.0.89-jdk8

LABEL AUTHOR="TIGOLI FREDERIC"
LABEL VERSION="1.0"

WORKDIR /usr/local/tomcat/webapps/

COPY --from=build /api/target/*.war ./lycoris-api.war


CMD ["catalina.sh", "run"]

