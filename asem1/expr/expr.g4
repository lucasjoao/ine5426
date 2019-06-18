grammar expr;


// Sintax

expr : numexpression ;

numexpression : term terms ;

term : unaryexpr unaryexprs ;

terms : (SIGNAL term terms)* ;

unaryexpr : ( SIGNAL factor | factor ) ;

unaryexprs : ( OPERATOR unaryexpr unaryexprs)* ;

factor : ( INTCONSTANT | IDENT col | '(' numexpression ')' ) ;

col : ( '[' INTCONSTANT ']' )* ;

// Lexer Rules

IDENT : [a-zA-Z][a-zA-Z0-9]* ;

INTCONSTANT : [0-9]+ ;

WHITESPACE : [ \t]+ -> skip ;

OPERATOR : ( '*' | '/' | '%' ) ;

SIGNAL : ( '+' | '-' ) ;
