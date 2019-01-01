#!/bin/sh
docker run --add-host=db:192.168.0.13 \
			-e "spring.datasource.url=jdbc:postgresql://db:5432/expenses" \
			-p 8080:8080 expenses-app