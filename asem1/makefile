ANTLR4=java -jar ../build/antlr-4.7.2-complete.jar
JAVAC_WITH_ANTLR4=javac -cp ../build/antlr-4.7.2-complete.jar
GRUN=java -cp ../build/antlr-4.7.2-complete.jar org.antlr.v4.gui.TestRig

all: run

.ONESHELL:
run:
	@read -r -p "Qual expressão aritmética deve ser usada? " EXPR;
	@echo $$EXPR > expr_file;
	@$(GRUN) expr expr_file
	@read -r -p "Qual declaração de variável deve ser usada? " DECL;
	@echo $$DECL > decl_file
	@$(GRUN) decl decl_file
	@rm expr_file
	@rm decl_file

build:
	@$(ANTLR4) ./decl/decl.g4
	@$(JAVAC_WITH_ANTLR4) ./decl/decl*.java
	@$(ANTLR4) ./expr/expr.g4
	@$(JAVAC_WITH_ANTLR4) ./expr/expr*.java

clean:
	@rm -f ./decl/*.class ./decl/*.tokens ./decl/*.java ./expr/*.class ./expr/*.tokens ./expr/*.java