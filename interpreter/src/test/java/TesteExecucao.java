import lexer.Lexer;
import lexer.VerdadeiroLexo;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.VerdadeiroParser;
import errorhandler.AcumuladorErros;
import errorhandler.ManipuladorErros;


import static org.junit.jupiter.api.Assertions.assertEquals;

class TesteExecucao {

    private Lexer lexer = new VerdadeiroLexo();
    private Parser parser = new VerdadeiroParser(lexer);
    private Interpretador interpreter = new VerdadeiroInterpretador(parser);

    @Test
    void printLiteral() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        ManipuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("print(4);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("4.0", msgAccum.getMessages().get(0));
    }

    @Test
    void declareAssignAndPrintLiteral() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        ManipuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let pi: number; pi = 3.14; print(pi);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("3.14", msgAccum.getMessages().get(0));
    }

    @Test
    void declareAssignStatementWithUnmatchingTypes() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        AcumuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let pi: number = \"hola\";"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, errorAcum.getErrors().size());
        assertEquals("[INTERPRETER] Expression does not conform to declared type\n" +
                "Range -> from (line: 1, col: 1) to (line: 1, col 23)", errorAcum.getErrors().get(0));
    }

    @Test
    void declareAssignStatementWithPrint() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        ManipuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let name:string=\"agustin\";print(name);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("agustin", msgAccum.getMessages().get(0));
    }

    @Test
    void declareAssignArithmeticExpressionOfNumbers() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        ManipuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let result:number= 10*2-5;print(result);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("15.0", msgAccum.getMessages().get(0));
    }

    @Test
    void concatenationOfStringAndNumbers() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        ManipuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let result:string= \"el numero cinco es \" + 5;print(result);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("el numero cinco es 5.0", msgAccum.getMessages().get(0));
    }

    @Test
    void invalidMULTIPLICACAOOfStringWithNumber() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        AcumuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let result:number = \"string\" * 5;print(result);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, errorAcum.getErrors().size());
        assertEquals("[INTERPRETER] Invalid arithmetic operation for given types\n" +
                "Range -> from (line: 1, col: 21) to (line: 1, col 32)", errorAcum.getErrors().get(0));
    }

    @Test
    void multipleStatementsWithsArithmeticOperations() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        AcumuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("let hola: string = \"hola\";\n" +
                        "let cuenta: number;\n" +
                        "cuenta = 5 * 5 -8 / 4;\n" +
                        "print(cuenta + hola + \" mundo\");"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("23.0hola mundo", msgAccum.getMessages().get(0));
    }

    @Test
    void singleStatementWithNoSemiColon() {
        AcumuladorMensagem msgAccum = new AcumuladorMensagem();
        AcumuladorErros errorAcum = new AcumuladorErros();
        interpreter.execute("print(5)"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, errorAcum.getErrors().size());
        assertEquals("[PARSER] Missing semi-colon to end statement\n" +
                "Range -> from (line: 1, col: 8) to (line: 1, col 8)", errorAcum.getErrors().get(0));
    }


}
