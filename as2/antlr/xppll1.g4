grammar xppll1;

// Sintax

xppll1 : program ;

program : classlist | ;

classlist : 'class' IDENT classdecl classlistepsilon ;

classlistepsilon : classlist | ;

classdecl : 'extends' IDENT classbody | classbody ;

classbody : '{' classbodydentro ;

classbodydentro : classlist vardecls constructdecls methoddecls '}' | vardecls constructdecls methoddecls '}' ;

vardecls : vardecl ';' vardecls | ;

constructdecls : constructdecl constructdecls | ;

methoddecls : methoddecl methoddecls | ;

type : 'int' | 'string' |  IDENT ;

vardecl : type IDENT col vardeclm ;

col : ('[' ']') * ;

vardeclm : virgula vardeclm | ;

virgula : ',' IDENT col ;

constructdecl : 'constructor' methodbody ;

methoddecl : type col IDENT methodbody ;

methodbody : '(' paramlist ')' statment ;

paramlist : type IDENT col parameteres | ;

parameteres : ',' type IDENT col parameteres | ;

statment : 'int' IDENT col vardeclm ';' | 'string' IDENT col vardeclm ';' | IDENT varoratribut | printstat ';' | readstat ';' | returnstat ';' | superstat ';' | ifstat | forstat | '{' statlist '}' | 'break' ';' | ';' ;

varoratribut : IDENT col vardeclm ';' | lvalue '=' atributstatss ';' ;

atribstat : IDENT lvalue '=' atributstatss ;

atributstatss : expression | alocexpression ;

printstat : 'print' expression ;

readstat : 'read' IDENT lvalue ;

returnstat : 'return' returnstatexpress ;

returnstatexpress : expression | ;

superstat : 'super' '(' arglist ')' ;

ifstat : 'if' '(' expression ')' statment | 'ifelse' '(' expression ')' statment 'else' statment ;

forstat : 'for' '(' dentrofor ')' statment ;

dentrofor : atribstat ';' dentroforexpress | ';' dentroforexpress ;

dentroforexpress : ';' dentroforatribs | expression ';' dentroforatribs ;

dentroforatribs : atribstat | ;

statlist : statment statlistas ;

statlistas : statlist | ;

lvalue : '[' expression ']' lvalue | '.' IDENT lvaluex | ;

lvaluex : '(' arglist ')' lvalue | lvalue ;

alocexpression : 'new' alocexpressionx ;

alocexpressionx : IDENT alocexpressionz | 'int' expressions | 'string' expressions ;

alocexpressionz : '(' arglist ')' | expressions ;

expressions : '[' expression ']' expressionsx ;

expressionsx : expressions | ;

expression : numexpression expressionz ;

expressionz : '<' expressiony | '>' expressiony | '==' numexpression | '!=' numexpression | ;

expressiony : numexpression | '=' numexpression ;

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
