FROM postgres:16.1

COPY init-scripts/ /docker-entrypoint-initdb.d/

ENV POSTGRES_USER: postgres
ENV POSTGRES_PASSWORD: postgres
ENV POSTGRES_DB: translate_db

EXPOSE 5432
