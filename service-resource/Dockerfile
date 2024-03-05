FROM openjdk:17-oracle as extractor
WORKDIR /project-resource-service
COPY build/libs/app.jar /project-resource-service/

RUN java -Djarmode=layertools -jar app.jar extract
FROM  openjdk:17-oracle
WORKDIR /app
COPY --from=extractor /project-resource-service/dependencies/. /app/.
COPY --from=extractor /project-resource-service/spring-boot-loader/. /app/.
COPY --from=extractor /project-resource-service/snapshot-dependencies/. /app/.
COPY --from=extractor /project-resource-service/application/. /app/.
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]

