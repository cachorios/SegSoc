server.port=8085
# This is a workaround for https://github.com/vaadin/spring/issues/381
spring.servlet.multipart.enabled = false

server.compression.enabled=true
server.compression.mime-types=application/json,application/xml,text/html,text/xml,text/plain,application/javascript,text/css
security.basic.enabled=false
server.tomcat.uri-encoding=UTF-8
spring.jackson.serialization.write_dates_as_timestamps=false

# Comment out if using anything else than H2 (e.g. MySQL or PostgreSQL)
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect



spring.jpa.show-sql = true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto= update
#none, validate, update, create, and create-drop

spring.jpa.properties.javax.persistence.schema-generation.create-source=metadata
spring.jpa.properties.javax.persistence.schema-generation.scripts.action=create
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=create.sql

# Uncomment if using PostgreSQL
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
spring.datasource.url=jdbc:postgresql://localhost:5432/dejos
spring.datasource.username=postgres
spring.datasource.password=postgres