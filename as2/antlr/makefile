ANTLR4=java -jar ../../build/antlr-4.7.2-complete.jar
GRUN=java org.antlr.v4.gui.TestRig

all: run

.ONESHELL:
run:
	@export CLASSPATH=".:../../build/antlr-4.7.2-complete.jar:$CLASSPATH"
	@$(ANTLR4) -visitor xppll1.g4
	@javac xppll1*.java
	@javac Main.java
	@read -r -p "Qual o nome do arquivo X++ que deve ser compilado? (default é ../../fontes/compileme_sem_erro.xpp) " FILENAME;
	@java Main $$FILENAME

clean:
	@rm -f *.class *.tokens *.java
