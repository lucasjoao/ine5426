all: run

.ONESHELL:
run:
	@cd ../asem2/
	@read -r -p "Qual o caminho para o arquivo X++ que deve ser compilado? " FILENAME;
	java -jar target/compiladorxpp-1.0.jar -i $$FILENAME -p
	@cd ../gci2/

test:
	@cd ../asem2/
	@echo "TODO"
	@echo "-----------------------"
	@cat ../fontes/to_test/tipo00.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo00.xpp -p
	@cd ../gci2/

build:
	@cd ../asem2/
	@mvn clean install
	@cd ../gci2/