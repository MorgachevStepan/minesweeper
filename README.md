# Для запуска нужно

1. Создайте файл `.env` в корневой директории проекта с переменными окружения. Этот файл не должен попадать в репозиторий, он добавлен в `.gitignore`.
Пример содержания `.env` находится в файле `env-example`:
   ```env
    APPLICATION_PORT=8080
    POSTGRES_HOST=localhost
    POSTGRES_USERNAME=postgres
    POSTGRES_PASSWORD=postgres
    POSTGRES_DATABASE=postgres
    POSTGRES_PORT=5432
    REDIS_HOST=localhost
    REDIS_PORT=6379
    REDIS_TTL=10m
   ```

2. Соберите Docker образ и запустите контейнеры с помощью Docker Compose. Для этого в корневой директории проекта выполните:
    ```
   docker-compose up --build
    ```