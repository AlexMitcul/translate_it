DC_PATH="./docker-compose.yml"

build:
	docker-compose -f ${DC_PATH} build

up:
	docker-compose -f ${DC_PATH}  up -d

down:
	docker-compose -f ${DC_PATH} down

rebuild:
	make down
	make build
	make up

.PHONY: build up down rebuild