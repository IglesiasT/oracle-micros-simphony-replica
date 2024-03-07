FROM maven:3.8.5-openjdk-17 as base
WORKDIR /oracle-micros-simphony-replica

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
