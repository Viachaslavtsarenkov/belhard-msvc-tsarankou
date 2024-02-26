# belhard-msvc-tsarankou

### Steps for starting project
* clone repository
* create PosgreSQL DB in Docker:
  

docker run --name resourcesDB -p 7002:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resources -d postgres

docker run --name audioDB -p 7003:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=audio -d postgres
* start project in IDE
* Module resource works on port 8080
* Module song works on port 8081
