# appteste17

App para cadastro de Produto, Forneedor e Notas fiscais via REST usando Quarkus;

Usando PanacheEntityBase para persistir os dados;

## Executando o aplicativo no modo dev

```shell script
./mvnw quarkus:dev
```

## Oque foi usado
- Java 17
- REST ([guide](https://quarkus.io/guides/rest))
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation))
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache))
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource))

-  Documentação Swagger - Apos executar o projeto entrar na URL para ver os End-points (http://localhost:8080/swagger-ui/#/)


