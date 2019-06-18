grammar decl;


// Sintax

decl : IDENT ;

// Lexer Rules

IDENT : [a-zA-Z][a-zA-Z0-9]* ;

INTCONSTANT : [0-9]+ ;

WHITESPACE : [ \t]+ -> skip ;
