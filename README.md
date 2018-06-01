## Requisitos
    - Java 8+
    - Maven
    - Servidor Postgres

## Executando
- Dentro da pasta config, copie *app.config.TEMPLATE* para *app.config*
- Altere os dados de conexão com o banco postgres em app.config
- Em um terminal, execute:
    ```
        mvn compile exec:java
    ```

- Caso tenha apenas o jar, execute:
    ```
        java -cp <path do .jar> com.mycompany.app.App "path=<caminho_conf>"
    ```

## Estrutura
### API
    - /api/monthly-report?month=3&year=2018
    - /api/monthly-report-ranked?month=3&year=2018
### Client