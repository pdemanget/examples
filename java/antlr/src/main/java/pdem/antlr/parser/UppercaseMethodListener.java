package pdem.antlr.parser;

import org.antlr.v4.runtime.tree.TerminalNode;
import pdem.antlr.Java8BaseListener;
import pdem.antlr.Java8Parser;

import java.util.ArrayList;
import java.util.List;

public class UppercaseMethodListener extends Java8BaseListener {

    public List<String> getErrors() {
        return errors;
    }

    private List<String> errors = new ArrayList<>();

    // ... getter for errors
 
    @Override
    public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        TerminalNode node = ctx.Identifier();
        String methodName = node.getText();

        if (Character.isUpperCase(methodName.charAt(0))) {
            String error = String.format("Method %s is uppercased!", methodName);
            errors.add(error);
        }
    }

    @Override
    public void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        System.out.println(ctx.getText());
       // ctx.get
    }
}