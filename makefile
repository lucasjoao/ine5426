all: run

.ONESHELL:
run:
	@read -r -p "Qual o nome do arquivo X++ que deve ser compilado? " FILENAME;
	java -jar trabalho1.jar i $$FILENAME