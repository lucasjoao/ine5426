all: run

.ONESHELL:
run:
	@read -r -p "Qual o caminho para o arquivo X++ que deve ser compilado? " FILENAME;
	java -jar target/compiladorxpp-1.0.jar -i $$FILENAME

test:
	@echo "TESTES VERIFICAÇÃO TIPO"
	@cat ../fontes/to_test/tipo00.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo00.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/tipo01.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo01.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/tipo02.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo02.xpp

build:
	@mvn clean install