all: run

.ONESHELL:
run:
	@cd ../asem2/
	@read -r -p "Qual o caminho para o arquivo X++ que deve ser compilado? " FILENAME;
	java -jar target/compiladorxpp-1.0.jar -i $$FILENAME -p
	@cd ../gci2/

test:
	@cd ../asem2/
	@echo "EXEMPLOS DE GERAÇÃO DE CÓDIGO INTERMEDIÁRIO"
	@echo "-----------------------"
	@cat ../fontes/to_test/gci00.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/gci00.xpp -p
	@echo "-----------------------"
	@cat ../fontes/to_test/gci01.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/gci01.xpp -p
	@echo "-----------------------"
	@cat ../fontes/to_test/gci02.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/gci02.xpp -p
	@cd ../gci2/

build:
	@cd ../asem2/
	@mvn clean install
	@cd ../gci2/