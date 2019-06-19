Árvores geradas com ferramenta ANTLR

Para realizar a configuração do ANTLR basta seguir os seguintes passos:

$ cd /usr/local/lib
$ sudo curl -O https://www.antlr.org/download/antlr-4.7.2-complete.jar
$ export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"
$ alias antlr4='java -jar /usr/local/lib/antlr-4.7.2-complete.jar'
$ alias grun='java org.antlr.v4.gui.TestRig'


Para compilar e executar a árvore sintaxe T de expressões aritméticas:

1. Entrar na pasta Arvore EXPR
2. Digitar os seguintes comandos:
$ antlr4 expr.g4 
$ javac expr*.java
$ grun expr expr -gui
    Após esse comando digitar no próprio terminal uma entrada. Exemplo: a * b / (c + d[4]) 
    Apertar Enter e Ctrl+D

Para compilar e executar a árvore sintaxe T de declaração de variáveis:

1. Entrar na pasta Arvore DECL
2. Digitar os seguintes comandos:
$ antlr4 decl.g4 
$ javac decl*.java
$ grun decl decl -gui
    Após esse comando digitar no próprio terminal uma entrada. Exemplo: int M[4][3]
    Apertar Enter e Ctrl+D



