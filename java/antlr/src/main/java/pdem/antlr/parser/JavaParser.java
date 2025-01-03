package pdem.antlr.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import pdem.antlr.Java8Lexer;
import pdem.antlr.Java8Parser;

public class JavaParser {

    public static void main(String[] args) {
        new JavaParser().run();

    }

    private void run() {
        String javaClassContent = "public class SampleClass { void DoSomething(){} }";
        Java8Lexer lexer = new Java8Lexer(CharStreams.fromString(javaClassContent));


        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);
        ParseTree tree = parser.compilationUnit();


        ParseTreeWalker walker = new ParseTreeWalker();
        UppercaseMethodListener listener= new UppercaseMethodListener();


        walker.walk(listener, tree);

//            assertThat(listener.getErrors().size(), is(1));
//            assertThat(listener.getErrors().get(0),
//                    is("Method DoSomething is uppercased!"));
        System.out.println(listener.getErrors());
    }
}
