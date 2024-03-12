FROM openjdk:17-oracle as extractor
WORKDIR /project-charlie-service
COPY build/libs/app.jar /project-charlie-service/
RUN java -Djarmode=layertools -jar app.jar extract

FROM  openjdk:17-oracle
WORKDIR /app
COPY --from=extractor /project-charlie-service/dependencies/. /app/.
COPY --from=extractor /project-charlie-service/spring-boot-loader/. /app/.
COPY --from=extractor /project-charlie-service/snapshot-dependencies/. /app/.
COPY --from=extractor /project-charlie-service/application/. /app/.
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
