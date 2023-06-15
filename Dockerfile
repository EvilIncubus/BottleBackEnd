FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY /target/untitled-1.0-SNAPSHOT.jar app.jar
RUN mkdir resources
COPY /resources/* resources/
RUN ls -l resources/
EXPOSE 8080
ENTRYPOINT ["java", "-Dfile.upload-dir=resources", "-Dresources.static-locations=file:/app/resources/", "-jar","app.jar"]
#workdir
#in working dir create folder with static and crete script copy static files, in entrypoint set where is files