grammar decl;


// Sintax

decl : vardecl ;

vardecl : tipo IDENT col mvardecl ;

tipo : INTCONSTANT | IDENT ;

col : ( '[' INTCONSTANT ']' )* ;

mvardecl : virgula mvardecl | ;

virgula : ',' IDENT col ;

// Lexer Rules

IDENT : [a-zA-Z][a-zA-Z0-9]* ;

INTCONSTANT : [0-9]+ ;

WHITESPACE : [ \t]+ -> skip ;
