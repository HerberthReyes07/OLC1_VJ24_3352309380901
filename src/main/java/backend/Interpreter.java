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
import instructions.Declaration;
import instructions.DynamicListDeclaration;
import instructions.Method;
import instructions.StartWith;
import instructions.StructAssignment;
import instructions.StructDeclaration;
import instructions.StructInstantiation;
import instructions.VariableAssignment;
import instructions.VectorAssignment;
import instructions.VectorDeclaration;
import symbol.Struct;

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
    /*private LinkedList<SymbolTable> tables;
    private SymbolTable globalTable;*/
    private LinkedList<SymbolTable> symbolTable;

    public void interpret() {
        try {
            scanner s = new scanner(new BufferedReader(new StringReader(this.code)));
            parser p = new parser(s);
            var resultado = p.parse();

            var ast = new Tree((LinkedList<Instruction>) resultado.value);
            var table = new SymbolTable();
            table.setName("GLOBAL");
            ast.setConsole("");
            ast.setGlobalTable(table);

            LinkedList<Error> semanticErrors = new LinkedList<>();
            this.lexErrors = s.scannerErrors;
            this.syntaxErrors = p.parserErrors;
            //this.semanticErrors = semanticErrors;

            for (var a : ast.getInstructions()) {
                if (a == null) {
                    continue;
                }
                /*var res = a.interpret(ast, table);
                if (res instanceof Error) {
                    semanticErrors.add((Error) res);
                }*/
                if (a instanceof Method) {
                    ast.addFunction(a);
                }
                if (a instanceof StructDeclaration) {
                    a.interpret(ast, table);
                }
            }

            for (var a : ast.getInstructions()) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Declaration || a instanceof VariableAssignment
                        || a instanceof StructInstantiation || a instanceof StructAssignment
                        || a instanceof VectorDeclaration || a instanceof VectorAssignment
                        || a instanceof DynamicListDeclaration) {
                    var res = a.interpret(ast, table);
                    if (res instanceof Error) {
                        semanticErrors.add((Error) res);
                    }
                }
            }

            StartWith sw = null;
            for (var a : ast.getInstructions()) {
                if (a == null) {
                    continue;
                }
                if (a instanceof StartWith) {
                    sw = (StartWith) a;
                    break;
                }
            }
            
            var resultStartWith = sw.interpret(ast, table);
            if (resultStartWith instanceof Error) {
                //System.out.println("ERROR AL EJECUTAR START_WITH");
                semanticErrors.add((Error) resultStartWith);
            }

            this.symbolTable = new LinkedList<>();
            symbolTable.add(table);
            symbolTable.addAll(ast.getTables());

            this.semanticErrors = semanticErrors;
            this.console = ast.getConsole();

            System.out.println(ast.getStructs());

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

    public LinkedList<SymbolTable> getSymbolTable() {
        return symbolTable;
    }

}
