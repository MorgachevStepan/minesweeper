# ��� ������� �����

1. �������� ���� `.env` � �������� ���������� ������� � ����������� ���������. ���� ���� �� ������ �������� � �����������, �� �������� � `.gitignore`.
������ ���������� `.env` ��������� � ����� `env-example`:
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

2. �������� Docker ����� � ��������� ���������� � ������� Docker Compose. ��� ����� � �������� ���������� ������� ���������:
    ```
   docker-compose up --build
    ```