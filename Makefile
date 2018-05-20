clean:
	rm -rf resources/js/*
	rm -rf resources/css/*

build: clean
	clj build.clj

up: clean build
	docker-compose up
