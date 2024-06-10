package analysis;


//importaciones
import java_cup.runtime.Symbol;

%%

//codigo de usuario
%{
%}

%init{
    yyline = 1;
    yycolumn = 1;
%init}

//caracteristicas de jflex
%cup
%class scanner
%public
%line
%char
%column
%full
//%debug
%ignorecase

//simbolos del sistema
PAR_IZQ = "("
PAR_DER = ")"
LLAVE_IZQ = "{"
LLAVE_DER = "}"
CORCHETE_IZQ = "["
CORCHETE_DER = "]"
PUNTO_COMA = ";"
DOS_PUNTOS = ":"
GUION_BAJO = "_"
COMA = ","
MAS = "+"
MENOS = "-"
MULT = "*"
DIV = "/"
MOD = "%"
IGUAL = "="
MENOR_QUE = "<"
MAYOR_QUE = ">"
OR = "||"
AND = "&&"
XOR = "^"
NOT = "!"
DECIMAL = [0-9]+"."[0-9]+
ENTERO = [0-9]+
CARACTER = [\'](([\\]([n]|[\\]|[\"]|[t]|[\'])|[^\\\"\']{0,1}))[\']
CADENA = [\"](([\\]([n]|[\\]|[\"]|[t]|[\'])|[^\\\"\']))*[\"]
ID = [a-zA-Z][a-zA-Z0-9_]*
COMENTARIO_SIM = [/][/].*
COMENTARIO_MULT = [/][*][\s\S]*?[*][/]
BLANCOS = [\ \r\t\n\f]+

//palabras reservadas
KW_CONST = "const"
KW_VAR = "var"
KW_INT = "int"
KW_DOUBLE = "double"
KW_BOOL = "bool"
KW_CHAR = "char"
KW_STRING = "string"
KW_IF = "if"
KW_ELSE = "else"
KW_MATCH = "match"
KW_WHILE = "while"
KW_FOR = "for"
KW_DO = "do"
KW_BREAK = "break"
KW_CONTINUE = "continue"
KW_PRINTLN = "println"
KW_TRUE = "true"
KW_FALSE = "false"

%%
<YYINITIAL> {KW_CONST}  {return new Symbol(sym.KW_CONST, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_VAR}  {return new Symbol(sym.KW_VAR, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_INT}  {return new Symbol(sym.KW_INT, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_DOUBLE}  {return new Symbol(sym.KW_DOUBLE, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_BOOL}  {return new Symbol(sym.KW_BOOL, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_CHAR}  {return new Symbol(sym.KW_CHAR, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_STRING}  {return new Symbol(sym.KW_STRING, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_IF}  {return new Symbol(sym.KW_IF, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_ELSE}  {return new Symbol(sym.KW_ELSE, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_MATCH}  {return new Symbol(sym.KW_MATCH, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_WHILE}  {return new Symbol(sym.KW_WHILE, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_FOR}  {return new Symbol(sym.KW_FOR, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_DO}  {return new Symbol(sym.KW_DO, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_BREAK}  {return new Symbol(sym.KW_BREAK, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_CONTINUE}  {return new Symbol(sym.KW_CONTINUE, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_PRINTLN}  {return new Symbol(sym.KW_PRINTLN, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_TRUE}  {return new Symbol(sym.KW_TRUE, yyline, yycolumn, yytext());}
<YYINITIAL> {KW_FALSE}  {return new Symbol(sym.KW_FALSE, yyline, yycolumn, yytext());}

<YYINITIAL> {DECIMAL}   {return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext());}
<YYINITIAL> {ENTERO}    {return new Symbol(sym.ENTERO, yyline, yycolumn, yytext());}

<YYINITIAL> {CARACTER} {
                String caracter = yytext();
                caracter = caracter.substring(1, caracter.length()-1);
                caracter = caracter.replace("\\n","\n");
                caracter = caracter.replace("\\\\","\\");
                caracter = caracter.replace("\\\"","\"");
                caracter = caracter.replace("\\t","\t");
                caracter = caracter.replace("\\\'","\'");
                return new Symbol(sym.CARACTER, yyline, yycolumn, caracter);
            }

<YYINITIAL> {CADENA} {
                String cadena = yytext();
                cadena = cadena.substring(1, cadena.length()-1);
                cadena = cadena.replace("\\n","\n");
                cadena = cadena.replace("\\\\","\\");
                cadena = cadena.replace("\\\"","\"");
                cadena = cadena.replace("\\t","\t");
                cadena = cadena.replace("\\\'","\'");
                return new Symbol(sym.CADENA, yyline, yycolumn, cadena);
            }

<YYINITIAL> {ID} {return new Symbol(sym.ID, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR_IZQ}       {return new Symbol(sym.PAR_IZQ, yyline, yycolumn, yytext());}
<YYINITIAL> {PAR_DER}       {return new Symbol(sym.PAR_DER, yyline, yycolumn, yytext());}
<YYINITIAL> {LLAVE_IZQ}       {return new Symbol(sym.LLAVE_IZQ, yyline, yycolumn, yytext());}
<YYINITIAL> {LLAVE_DER}       {return new Symbol(sym.LLAVE_DER, yyline, yycolumn, yytext());}
<YYINITIAL> {CORCHETE_IZQ}       {return new Symbol(sym.CORCHETE_IZQ, yyline, yycolumn, yytext());}
<YYINITIAL> {CORCHETE_DER}       {return new Symbol(sym.CORCHETE_DER, yyline, yycolumn, yytext());}
<YYINITIAL> {PUNTO_COMA}       {return new Symbol(sym.PUNTO_COMA, yyline, yycolumn, yytext());}
<YYINITIAL> {DOS_PUNTOS}       {return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn, yytext());}
<YYINITIAL> {GUION_BAJO}       {return new Symbol(sym.GUION_BAJO, yyline, yycolumn, yytext());}
<YYINITIAL> {COMA}       {return new Symbol(sym.COMA, yyline, yycolumn, yytext());}
<YYINITIAL> {MAS}       {return new Symbol(sym.MAS, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOS}     {return new Symbol(sym.MENOS, yyline, yycolumn, yytext());}
<YYINITIAL> {MULT}      {return new Symbol(sym.MULT, yyline, yycolumn, yytext());}
<YYINITIAL> {DIV}       {return new Symbol(sym.DIV, yyline, yycolumn, yytext());}
<YYINITIAL> {MOD}       {return new Symbol(sym.MOD, yyline, yycolumn, yytext());}
<YYINITIAL> {IGUAL}       {return new Symbol(sym.IGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR_QUE}       {return new Symbol(sym.MENOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR_QUE}       {return new Symbol(sym.MAYOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {OR}       {return new Symbol(sym.OR, yyline, yycolumn, yytext());}
<YYINITIAL> {AND}       {return new Symbol(sym.AND, yyline, yycolumn, yytext());}
<YYINITIAL> {XOR}       {return new Symbol(sym.XOR, yyline, yycolumn, yytext());}
<YYINITIAL> {NOT}       {return new Symbol(sym.NOT, yyline, yycolumn, yytext());}
<YYINITIAL> {COMENTARIO_SIM}   {}
<YYINITIAL> {COMENTARIO_MULT}   {}
<YYINITIAL> {BLANCOS}   {}
