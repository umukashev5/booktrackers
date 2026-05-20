# BookTracker

BookTracker — простое веб-приложение для отслеживания книг и прогресса чтения.

Проект создан как pet project для изучения Java, Spring Boot, PostgreSQL и веб-разработки.

## Возможности

- добавление книг
- редактирование книг
- удаление книг
- просмотр списка книг
- отслеживание количества прочитанных страниц
- автоматический расчёт оставшихся страниц и процента прогресса
- автоматическое обновление статуса книги по прогрессу

## Технологии

- Java
- Spring Boot
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Maven
- HTML / CSS

## Запуск проекта

1. Создать базу данных PostgreSQL:

```sql
CREATE DATABASE booktracker;

Настроить подключение к базе данных в файле:
src/main/resources/application.properties

Пример:

spring.datasource.url=jdbc:postgresql://localhost:5432/booktracker
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

server.port=9090
Запустить приложение:
mvn spring-boot:run
Открыть в браузере:
http://localhost:9090/books
Планы
поиск книг
фильтр по статусу
сортировка
отдельная страница книги
заметки и конспекты
статистика чтения
Статус проекта

MVP в разработке.