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

- Após subir a aplicação, acessar os endpoints em [http://localhost:8080/api](http://localhost:8080/api)

- O Swagger pode ser acessado em [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

## Banco de dados

- MySql
- Database: controle_financeiro
- user: root | password: root

#### Para visualizar os dados
```bash    
docker exec -it mysql_financeiro bash
```
```bash 
mysql -u root -p
```
