all: run

.ONESHELL:
run:
	@read -r -p "Qual expressão aritmética deve ser usada? " > oi
	@$$EXPR >> expr_file
	@grun expr/expr expr expr_file
	@read -r -p "Qual declaração de variável deve ser usada? " DECL;
	@$$DECL >> decl_file
	@grun decl/decl decl decl_file
	@rm expr_file
	@rm decl_file

build:
	@cd decl/
	@java -jar ../../build/antlr-4.7.2-complete.jar decl.g4
	@javac -cp ../../build/antlr-4.7.2-complete.jar decl*.java
	@cd ../expr/
	@java -jar ../../build/antlr-4.7.2-complete.jar expr.g4
	@javac -cp ../../build/antlr-4.7.2-complete.jar expr*.java
	@cd ../
