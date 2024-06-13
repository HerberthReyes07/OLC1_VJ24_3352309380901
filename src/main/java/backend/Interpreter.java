/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import abstracto.Instruction;
import analysis.parser;
import analysis.scanner;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import symbol.SymbolTable;
import symbol.Tree;
import exceptions.Error;

/**
 *
 * @author herberthreyes
 */
public class Interpreter {

    private String code;
    private String console;
    private LinkedList<Error> lexErrors;
    private LinkedList<Error> syntaxErrors;
    private LinkedList<Error> semanticErrors;

    public void interpret() {
        try {
            //println(\"\\\"CADENA CON \\n COMILLLAS\\t \\\'C:micarpeta\\\' \\\" \");
            /*String texto = "println(\'\\n\');\n"
                    + "println(\'\\\\');\n"
                    + "println(\'\\\"\');\n"
                    + "println(\'\\\'\');\n"
                    + "println(\'\\t\');";
            String texto = ""
                    + "match 1 {\n"
                    + "    2**2 => { \n"
                    + "        println(\"ES 4\");\n"
                    + "    }\n"
                    + "    4-1 => { \n"
                    + "        println(\"ES 3\");\n"
                    + "    }\n"
                    + "    2*1 => { \n"
                    + "        println(\"ES 2\");\n"
                    + "    }\n"
                    + "    _ => { \n"
                    + "        println(\"NO ES 4, 3 O 2\");\n"
                    + "    }\n"
                    + "}";*/
            scanner s = new scanner(new BufferedReader(new StringReader(this.code)));
            parser p = new parser(s);
            var resultado = p.parse();
            var ast = new Tree((LinkedList<Instruction>) resultado.value);
            var table = new SymbolTable();
            table.setName("GLOBAL");
            ast.setConsole("");
            LinkedList<Error> semanticErrors = new LinkedList<>();
            this.lexErrors = s.scannerErrors;
            this.syntaxErrors = p.parserErrors;
            
            for (var a : ast.getInstructions()) {
                if (a == null) {
                    continue;
                }
                var res = a.interpret(ast, table);
                if (res instanceof Error) {
                    semanticErrors.add((Error) res);
                }
            }
            this.semanticErrors = semanticErrors;
            this.console = ast.getConsole();
            
        } catch (Exception ex) {
            System.out.println("Algo salio mal");
            System.out.println(ex);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConsole() {
        return console;
    }

    public LinkedList<Error> getLexErrors() {
        return lexErrors;
    }

    public LinkedList<Error> getSyntaxErrors() {
        return syntaxErrors;
    }

    public LinkedList<Error> getSemanticErrors() {
        return semanticErrors;
    }
    
}
