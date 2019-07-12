# INE5426

Terceiro trabalho desenvolvido em Java 10 por Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito para a disciplina INE5426 da UFSC.

### Organização ###

Pasta asem2 possui o desenvolvimento de todo o projeto junto do makefile para a tarefa asem2.

Pasta gci2 possui somente o makefile para a tarefa gci2, que por sua vez executa o código do projeto (presente na pasta asem2) com outros argumentos.

Pasta fontes possui os programas solicitados e programas testes feitos na linguagem xpp.

### Build e execução ###

Requisitos:
- java 10
- gerenciador de dependências maven

Para compilar execute `make build` no diretório asem2 ou no diretório gci2.

Para executar o asem2 basta executar `make` no diretório asem2. Que por sua vez irá solicitar o caminho do programa fonte a ser compilado.

Para executar o gci2 basta executar `make` no diretório gci2. Que por sua vez irá solicitar o caminho do programa fonte a ter o código intermediário gerado.
