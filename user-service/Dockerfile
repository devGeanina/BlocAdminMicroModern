FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

#Uncomment if you want to run the cassandra-schema.sh to create the keyspace and table instead of using the docker-compose plus cassandra-init.sh file
#FROM cassandra:latest

#ENV CASSANDRA_KEYSPACE blocadmin
#ENV CASSANDRA_LISTEN_ADDRESS cassandra
#ENV TABLE users

#COPY cassandra-schema.sh /cassandra-schema.sh
#ENTRYPOINT ["bash","/cassandra-schema.sh"]
#CMD ["cassandra", "-f"]
