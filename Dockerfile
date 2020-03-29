FROM java:8-jre

ADD ./target/all-1.0.0.jar /app/
CMD ["java", "-Xmx500m", "-jar", "/app/all-1.0.0.jar"]