# API de Controle Financeiro

RESTful API com Arquitetura Limpa.

## Tecnologias utilizadas

- [Java 17](https://www.oracle.com/java/)

- [Spring Boot](https://spring.io/projects/spring-boot)

- [Spring Data](https://spring.io/projects/spring-data)

- [Maven](http://maven.apache.org/)

- [OpenAPI](https://springdoc.org/)

- [JUnit 5](https://junit.org/junit5/)

- [Mockito](https://site.mockito.org/)

## Setup

- Instalar Docker e Docker Compose

- Executar o comando

```bash
$ docker-compose up -d 
```

## Executar os testes

```bash    
$ mvn test
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
    - Grafana [http://localhost:3000/](http://localhost:3000/) 
      - User: admin
      - Password: admin
      - `Importar Dashboard` [Dashboard](data/grafana-spring-dashboard.json)
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
