all: run

.ONESHELL:
run:
	@read -r -p "Qual o caminho para o arquivo X++ que deve ser compilado? " FILENAME;
	java -jar target/compiladorxpp-1.0.jar -i $$FILENAME

test:
	@echo "TESTES VERIFICAÇÃO TIPO"
	@echo "-----------------------"
	@cat ../fontes/to_test/tipo00.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo00.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/tipo01.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo01.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/tipo02.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/tipo02.xpp
	@echo "TESTES VERIFICAÇÃO BREAK"
	@echo "-----------------------"
	@cat ../fontes/to_test/break00.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/break00.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/break01.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/break01.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/break02.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/break02.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/break03.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/break03.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/break04.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/break04.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/break05.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/break05.xpp
	@echo "TESTES VERIFICAÇÃO NOME"
	@echo "-----------------------"
	@cat ../fontes/to_test/name00.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/name00.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/name01.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/name01.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/name02.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/name02.xpp
	@echo "-----------------------"
	@cat ../fontes/to_test/name03.xpp
	java -jar target/compiladorxpp-1.0.jar -i ../fontes/to_test/name03.xpp

build:
	@mvn clean install