all: run

.ONESHELL:
run:
	@read -r -p "Qual o nome do arquivo X++ que deve ser compilado? " FILENAME;
	java -jar target/compiladorxpp-1.0.jar -i $$FILENAME

build:
	@mvn clean install