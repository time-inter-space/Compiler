grammar Mx;

program : (classDef | funcDef | varDef)* EOF;

classDef : 'class' Identifier '{'
    (varDef | funcInClassDef)* '}' ';';
funcDef : type Identifier '('
    ((varType Identifier ',')* varType Identifier)?
    ')' suite;
funcExpr : Identifier '('
    ((expression ',')* expression)? ')';
funcInClassDef : funcDef | (Identifier '(' ')' suite);
varDef : varType (initExpr ',')* initExpr ';';
initExpr : Identifier ('=' expression)?;

suite : '{' statement* '}';

statement
    : suite                                                #block
    | varDef                                               #varDefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                        #ifStmt
    | While '(' expression ')' statement                   #whileStmt
    | For '(' forInitialization forCondition expression? ')'
        statement                                          #forStmt
    | Return expression? ';'                               #returnStmt
    | Break ';'                                            #breakStmt
    | Continue ';'                                         #continueStmt
    | expression ';'                                       #pureExprStmt
    | ';'                                                  #emptyStmt
    ;

forInitialization : varDef | expression? ';';
forCondition : expression? ';';

expression
    : primary                                              #atomExpr
    | New varType ('[' expression ']')+ ('[' ']')*         #newArrayExpr
    | New varType ('(' ')')?                               #newClassExpr
    | funcExpr                                             #functionExpr
    | '(' expression ')'                                   #parenExpr
    | expression '[' expression ']'                        #bracketExpr
    | expression '.' classMember                           #dotExpr
    | expression ('++' | '--')                             #postExpr
    | <assoc = right> ('++' | '--' | '!' | '~' | '-')
        expression                                         #preExpr
    | expression ('*' | '/' | '%') expression              #binaryExpr
    | expression ('+' | '-') expression                    #binaryExpr
    | expression ('<<' | '>>') expression                  #binaryExpr
    | expression ('<' | '<=' | '>' | '>=') expression      #binaryExpr
    | expression ('==' | '!=') expression                  #binaryExpr
    | expression '&' expression                            #binaryExpr
    | expression '^' expression                            #binaryExpr
    | expression '|' expression                            #binaryExpr
    | expression '&&' expression                           #binaryExpr
    | expression '||' expression                           #binaryExpr
    | <assoc = right> expression '?'
        expression ':' expression                          #ternaryExpr
    | <assoc = right> expression '=' expression            #assignExpr
    ;

primary
    : literal
    | Identifier
    ;

classMember : funcExpr | Identifier;

literal
    : True
    | False
    | DecimalInteger
    | StringLiteral
    | Null
    | This
    ;

type : varType | Void;

varType
    : Bool ('[' ']')*
    | Int ('[' ']')*
    | String ('[' ']')*
    | Identifier ('[' ']')*
    ;

Void : 'void';
Bool : 'bool';
Int : 'int';
String : 'string';
New : 'new';
Class : 'class';
Null : 'null';
True : 'true';
False : 'false';
This : 'this';
If : 'if';
Else : 'else';
For : 'for';
While : 'while';
Break : 'break';
Continue : 'continue';
Return : 'return';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
LeftShift : '<<';
RightShift : '>>';

Plus : '+';
Minus : '-';
PlusPlus : '++';
MinusMinus : '--';
Multiple : '*';
Division : '/';
Modulo : '%';

And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Caret : '^';
Not : '!';
Tilde : '~';

Assign : '=';
Equal : '==';
NotEqual : '!=';

Dot : '.';
Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';

Identifier : [a-zA-Z] [a-zA-Z_0-9]*;

DecimalInteger
    : [1-9] [0-9]*
    | '0'
    ;

StringLiteral : '"' ('\\n' | '\\\\' | '\\"'
    | [\u0020-\u0021] | [\u0023-\u007E])* '"';

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;
