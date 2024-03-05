FROM openjdk:17-oracle as extractor
WORKDIR /project-song-service
COPY build/libs/app.jar /project-song-service/
RUN java -Djarmode=layertools -jar app.jar extract

FROM  openjdk:17-oracle
WORKDIR /app
COPY --from=extractor /project-song-service/dependencies/. /app/.
COPY --from=extractor /project-song-service/spring-boot-loader/. /app/.
COPY --from=extractor /project-song-service/snapshot-dependencies/. /app/.
COPY --from=extractor /project-song-service/application/. /app/.
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]

