FROM java:8-jre
ADD ./target/rssReader-1.0.0.jar /app/
CMD ["java", "-Xmx500m", "-jar", "/app/rssReader-1.0.0.jar"]


#ARG JAR_FILE
#RUN mkdir -p /apps
#COPY ./target/rssReader-1.0.0.jar /app/
#COPY ./entrypoint.sh /apps/entrypoint.sh
#
#RUN chmod +x /apps/entrypoint.sh
#CMD ["/apps/entrypoint.sh"]