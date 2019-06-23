ANTLR4=java -jar ../../build/antlr-4.7.2-complete.jar
GRUN=java org.antlr.v4.gui.TestRig

all: run

.ONESHELL:
run:
	@# common
	@export CLASSPATH=".:../../build/antlr-4.7.2-complete.jar:$CLASSPATH"
	@# expr process (build and run)
	@cd expr
	@$(ANTLR4) expr.g4
	@javac expr*.java
	@read -r -p "Qual o caminho para o arquivo X++ que possui a expressão aritmética? (default é ./expr_file.xpp) " EXPR;
	@$(GRUN) expr expr $$EXPR -gui
	@# decl process (build and run)
	@cd ../decl
	@$(ANTLR4) decl.g4
	@javac decl*.java
	@read -r -p "Qual o caminho para o arquivo X++ que possui a declaração de variável? (default é ./decl_file.xpp) " DECL;
	@$(GRUN) decl decl $$DECL -gui
	@cd ..

clean:
	@rm -f ./decl/*.class ./decl/*.tokens ./decl/*.java ./expr/*.class ./expr/*.tokens ./expr/*.java