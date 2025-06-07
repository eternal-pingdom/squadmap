FROM eclipse-temurin:21-jdk

COPY target/*-runner.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

CMD ["sh", "-c", "sleep 10 && java -jar /app.jar"]
