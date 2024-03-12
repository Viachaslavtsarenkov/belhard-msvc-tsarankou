FROM openjdk:17-oracle as extractor
WORKDIR /project-discovery-service
COPY build/libs/app.jar /project-discovery-service/
RUN java -Djarmode=layertools -jar app.jar extract

FROM  openjdk:17-oracle
WORKDIR /app
COPY --from=extractor /project-discovery-service/dependencies/. /app/.
COPY --from=extractor /project-discovery-service/spring-boot-loader/. /app/.
COPY --from=extractor /project-discovery-service/snapshot-dependencies/. /app/.
COPY --from=extractor /project-discovery-service/application/. /app/.
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
