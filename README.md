# API de Controle Financeiro


>Utilizando Clean Architecture e DDD 


## Tecnologias utilizadas

- [Java 17](https://www.oracle.com/java/)

- [Spring Boot](https://spring.io/projects/spring-boot)

- [Spring Data](https://spring.io/projects/spring-data)

- [Maven](http://maven.apache.org/)

- [OpenAPI](https://springdoc.org/)

- [JUnit 5](https://junit.org/junit5/)

- [Mockito](https://site.mockito.org/)

## Executar os testes

```bash    
mvn test
```

## Executar Local
```bash
docker-compose up mysql prometheus grafana
```

>Possível executar pela classe [Main.java](infrastructure/src/main/java/br/com/almir/infrastructure/Main.java) ou via maven

```bash
mvn -pl infrastructure spring-boot:run
```

## Executar via Docker

```bash
docker-compose up --build 
```

## Acessar a aplicação

- Após subir a aplicação, acessar os endpoints
  em [http://localhost:8080/api](http://localhost:8080/api)

- O Swagger pode ser acessado
  em [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
- Necessario utlizar header para autenticação:
  - api-key: aXRhw7o=


Exemplo:

```bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/balance?initDate=2022-02-15&endDate=2022-02-19' \
  -H 'accept: application/json' \
  -H 'api-key: aXRhw7o='
  ```

## Monitoração

- #### Prometheus e Grafana
    - Grafana [http://localhost:3000/](http://localhost:3000/d/sOae4vCnk/spring-boot-statistics?orgId=1&refresh=5s) 
      - User: admin
      - Password: admin
      - `Caso não mostrar o Dashboard importar o json` [Dashboard.json](data/grafana-spring-dashboard.json)
    - Prometheus [http://localhost:9090/graph](http://localhost:9090/graph)
  


## Banco de dados

- MySql
  - Database: 
    - controle_financeiro
  - User: 
    - root 
  - Password: 
    - root

#### Para visualizar os dados

```bash    
docker exec -it mysql_financeiro bash
```

```bash 
mysql -u root -p
```
