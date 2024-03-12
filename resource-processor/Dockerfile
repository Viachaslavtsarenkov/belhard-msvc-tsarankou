FROM openjdk:17-oracle as extractor
WORKDIR /project-processor-service
COPY build/libs/app.jar /project-processor-service/
RUN java -Djarmode=layertools -jar app.jar extract

FROM  openjdk:17-oracle
WORKDIR /app
COPY --from=extractor /project-processor-service/dependencies/. /app/.
COPY --from=extractor /project-processor-service/spring-boot-loader/. /app/.
COPY --from=extractor /project-processor-service/snapshot-dependencies/. /app/.
COPY --from=extractor /project-processor-service/application/. /app/.
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]

