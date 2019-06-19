grammar xppll1;

// Sintax

xppll1 : program ;

program : classlist | ;

classlist : 'class' IDENT classdecl classlistb ;

classlistb : classlist | ;

classdecl : 'extends' IDENT classbody | classbody ;

classbody : '{' classbodyb ;

classbodyb : classlist vardecls constructdecls methoddecls '}' | vardecls constructdecls methoddecls '}' ;

vardecls : vardecl ';' vardecls | ;

constructdecls : constructdecl constructdecls | ;

methoddecls : methoddecl methoddecls | ;

type : 'int' | 'string' |  IDENT ;

vardecl : type IDENT brackets multivardecl ;

brackets : ('[' ']') * ;

multivardecl : vardeclcomma multivardecl | ;

vardeclcomma : ',' IDENT brackets ;

constructdecl : 'constructor' methodbody ;

methoddecl : type brackets IDENT methodbody ;

methodbody : '(' paramlist ')' statment ;

paramlist : type IDENT brackets multiparam | ;

multiparam : ',' type IDENT brackets multiparam | ;

statment : 'int' IDENT brackets multivardecl ';' | 'string' IDENT brackets multivardecl ';' | IDENT varoratrib | printstat ';' | readstat ';' | returnstat ';' | ifstat | forstat | '{' statlist '}' | 'break' ';' | ';' ;

varoratrib : IDENT   multivardecl ';' | lvalue '=' atribstatb ';' ;

atribstat : IDENT lvalue '=' atribstatb ;

atribstatb : expression | alocexpression ;

printstat : 'print' expression ;

readstat : 'read' IDENT lvalue ;

returnstat : 'return' returnstatb ;

returnstatb : expression | ;

superstat : 'super' '(' arglist ')' ;

ifstat : 'if' '(' expression ')' statment | 'if' '(' expression ')' statment 'else' statment ;

forstat : 'for' '(' insidefor ')' statment ;

insidefor : atribstat ';' insideforb | ';' insideforb ;

insideforb : ';' insideforc | expression ';' insideforc ;

insideforc : atribstat | ;

statlist : statment statlistb ;

statlistb : statlist | ;

lvalue : '[' expression ']' lvalue | '.' IDENT lvalueb | ;

lvalueb : '(' arglist ')' lvalue | lvalue ;

alocexpression : 'new' alocexpressionb ;

alocexpressionb : IDENT alocexpressionc | 'int' expressions | 'string' expressions ;

alocexpressionc : '(' arglist ')' | expressions ;

expressions : '[' expression ']' expressionsb ;

expressionsb : expressions | ;

expression : numexpression expressionb ;

expressionb : '<' expressionc | '>' expressionc | '==' numexpression | '!=' numexpression | ;

expressionc : numexpression | '=' numexpression ;

numexpression : term terms ;

terms : '+' term terms | '-' term terms | ;

term : unaryexpr unaryexprs ;

unaryexprs : '*' unaryexpr unaryexprs | '/' unaryexpr unaryexprs | '%' unaryexpr unaryexprs | ;

unaryexpr : '+' factor | '-' factor | factor ;

factor : INTCONSTANT | STRINGCONSTANT | 'null' | IDENT lvalue | '(' expression ')' ;

arglist : expression arglists | ;

arglists : ',' expression arglists | ;


// Lexer

IDENT : [a-zA-Z] [a-zA-Z0-9]* ;

INTCONSTANT : [0-9]+ ;

STRINGCONSTANT : '"' .*? '"' ;

WHITESPACE: [ \r\n\t]+ -> skip ;
