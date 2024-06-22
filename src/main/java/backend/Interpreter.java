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
            LinkedList<Error> semanticErrors = new LinkedList<>();
            this.lexErrors = s.scannerErrors;
            this.syntaxErrors = p.parserErrors;
            //this.semanticErrors = semanticErrors;
            
            for (var a : ast.getInstructions()) {
                if (a == null) {
                    continue;
                }
                var res = a.interpret(ast, table);
                if (res instanceof Error) {
                    semanticErrors.add((Error) res);
                }
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
