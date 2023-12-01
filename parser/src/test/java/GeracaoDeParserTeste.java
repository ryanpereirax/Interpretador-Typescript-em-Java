
import ast.OperacaoAritmetica;
import ast.TipoPrimitivo;
import ast.Programa;
import ast.Tipo;
import ast.expression.ExpressaoAritmetica;
import ast.expression.Identificador;
import ast.expression.Literal;
import ast.statement.DeclaracaoAtribuicao;
import ast.statement.DeclaracaoAtribuicaoEstado;
import ast.statement.DeclaracaoEstado;
import ast.statement.VisualizacaoEstado;
import errorhandler.AcumuladorErros;
import lexer.Lexer;
import lexer.VerdadeiroLexo;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.VerdadeiroParser;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GeracaoDeParserTeste {
    private Lexer lexer = new VerdadeiroLexo();
    private Parser parser = new VerdadeiroParser(lexer);

    @Test
    void printStatementWithNumberLiteral() {
        final Programa result = (Programa) parser.parse("print(5);", new AcumuladorErros());

        final VisualizacaoEstado print = (VisualizacaoEstado) result.getStatements().get(0);

        final Literal exp = (Literal) print.getExpressionToPrint();

        assertEquals(1,result.getStatements().size());
        assertEquals(Tipo.NUMBER, exp.getType());
        assertEquals(5.0, exp.getNumberValue());
        assertEquals(7, exp.getInputRange().getStartColumn());
        assertEquals(7, exp.getInputRange().getEndColumn());
    }

    @Test
    void printStatementWithStringLiteral() {
        final Programa result = (Programa) parser.parse("print(\"hola\");", new AcumuladorErros());

        final VisualizacaoEstado print = (VisualizacaoEstado) result.getStatements().get(0);

        final Literal exp = (Literal) print.getExpressionToPrint();

        assertEquals(1,result.getStatements().size());
        assertEquals(Tipo.STRING, exp.getType());
        assertEquals("hola", exp.getStringValue());
    }

    @Test
    void printStatementWithMultipleArithmeticOperations() {
        final Programa result = (Programa) parser.parse("print(5 * 5 -8/ 2 + \"hola\");", new AcumuladorErros());

        final VisualizacaoEstado print = (VisualizacaoEstado) result.getStatements().get(0);
        final ExpressaoAritmetica ADICAO = (ExpressaoAritmetica) print.getExpressionToPrint();
        final Literal hola = (Literal) ADICAO.getRight();
        final ExpressaoAritmetica SUBTRACAO = (ExpressaoAritmetica) ADICAO.getLeft();
        final ExpressaoAritmetica MULTIPLICACAO = (ExpressaoAritmetica) SUBTRACAO.getLeft();
        final ExpressaoAritmetica DIVISAO = (ExpressaoAritmetica) SUBTRACAO.getRight();

        assertEquals(1,result.getStatements().size());
        assertEquals(OperacaoAritmetica.ADICAO, ADICAO.getOperation());
        assertEquals(OperacaoAritmetica.SUBTRACAO, SUBTRACAO.getOperation());
        assertEquals(OperacaoAritmetica.MULTIPLICACAO, MULTIPLICACAO.getOperation());
        assertEquals(OperacaoAritmetica.DIVISAO, DIVISAO.getOperation());
        assertEquals(Tipo.STRING, hola.getType());
        assertEquals("hola", hola.getStringValue());
    }

    @Test
    void invalidExpressionInsidePrintStatement() {
        final AcumuladorErros handler = new AcumuladorErros();
        final Programa result = (Programa) parser.parse("print(5*);", handler);

        assertEquals("[PARSER] Invalid expression\n" +
                "Range -> from (line: 1, col: 7) to (line: 1, col 8)", handler.getErrors().get(0));
    }

    @Test
    void reservedWordInsidePrintStatement() {
        final AcumuladorErros handler = new AcumuladorErros();
        final Programa result = (Programa) parser.parse("print(let);", handler);

        assertEquals("[PARSER] Expected expression but found let\n" +
                "Range -> from (line: 1, col: 7) to (line: 1, col 9)", handler.getErrors().get(0));
    }

    @Test
    void assignationStatementOfNumberLiteral() {
        final Programa result = (Programa) parser.parse("x = 1;", new AcumuladorErros());

        final DeclaracaoAtribuicao assignation = (DeclaracaoAtribuicao) result.getStatements().get(0);
        final Identificador identifier = assignation.getIdentifier();
        final Literal number = (Literal) assignation.getExpression();


        assertEquals(1,result.getStatements().size());
        assertEquals("x", identifier.getName());
        assertEquals(Tipo.NUMBER, number.getType());
        assertEquals(1, number.getNumberValue());
    }

    @Test
    void declarationStatement() {
        final Programa result = (Programa) parser.parse("let pi: number;", new AcumuladorErros());
        final DeclaracaoEstado declaration = (DeclaracaoEstado) result.getStatements().get(0);
        final TipoPrimitivo primitiveType = declaration.getType();
        final Identificador identifier = declaration.getIdentifier();

        assertEquals(1,result.getStatements().size());
        assertEquals(Tipo.NUMBER, primitiveType.getType());
        assertEquals("pi", identifier.getName());
    }

    @Test
    void declarationAndAssignationStatementsInTwoLines() {
        final Programa result = (Programa) parser.parse("let pi: number;\npi = 3.14;", new AcumuladorErros());
        final DeclaracaoEstado declaration = (DeclaracaoEstado) result.getStatements().get(0);
        final DeclaracaoAtribuicao assignation = (DeclaracaoAtribuicao) result.getStatements().get(1);
        final TipoPrimitivo primitiveType = declaration.getType();
        final Identificador identifier = declaration.getIdentifier();
        final Literal number = (Literal) assignation.getExpression();

        assertEquals(2,result.getStatements().size());
        assertEquals(Tipo.NUMBER, primitiveType.getType());
        assertEquals("pi", identifier.getName());
        assertEquals(Tipo.NUMBER, number.getType());
        assertEquals(3.14, number.getNumberValue());
    }

    @Test
    void declarationAssignationStatementOfString() {
        final Programa result = (Programa) parser.parse("let name : number = \"agustin\";", new AcumuladorErros());
        final DeclaracaoAtribuicaoEstado declareAssign = (DeclaracaoAtribuicaoEstado) result.getStatements().get(0);
        final TipoPrimitivo primitiveType = declareAssign.getType();
        final Identificador identifier = declareAssign.getIdentifier();
        final Literal number = (Literal) declareAssign.getExpression();

        assertEquals(1,result.getStatements().size());
        assertEquals(Tipo.NUMBER, primitiveType.getType());
        assertEquals("name", identifier.getName());
        assertEquals(Tipo.STRING, number.getType());
        assertEquals("agustin", number.getStringValue());
    }

    @Test
    void catchLexerErrorsInMultipleLines() {
        final AcumuladorErros errorAccum = new AcumuladorErros();
        parser.parse("let na#me : string = \"agustin\";\n jorg# = 5;", errorAccum);
        assertEquals(4,errorAccum.getErrors().size());
    }

    @Test
    void invalidExponentialArithmeticOperation() {
        final AcumuladorErros errorAccum = new AcumuladorErros();
        parser.parse("let nota: number = 7 + 3 ** 0.8;", errorAccum);
        assertEquals(2,errorAccum.getErrors().size());
    }


}