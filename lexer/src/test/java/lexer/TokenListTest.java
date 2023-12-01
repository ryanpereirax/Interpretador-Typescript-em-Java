package lexer;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TipoToken;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenListTest {

    private Lexer lexer = new VerdadeiroLexo();

    @Test
    void simplePrintStatement() {
        validateTokens("print(variable);",
                Arrays.asList(
                        TipoToken.PRINT, TipoToken.LEFT_PAREN, TipoToken.IDENTIFIER, TipoToken.RIGHT_PAREN, TipoToken.SEMI_COLON
                ));
    }

    @Test
    void simpleDeclarationStatement() {
        validateTokens("let p3pito: number;",
                Arrays.asList(
                        TipoToken.LET, TipoToken.SPACE, TipoToken.IDENTIFIER, TipoToken.COLON, TipoToken.SPACE,
                        TipoToken.NUMBER_TYPE, TipoToken.SEMI_COLON
                ));
    }

    @Test
    void printStatementWithADICAOOfAllTypes() {
        validateTokens("print(_pi+ $jorge+\"hola\" + 5)",
                Arrays.asList(
                        TipoToken.PRINT, TipoToken.LEFT_PAREN, TipoToken.IDENTIFIER, TipoToken.ADICAO, TipoToken.SPACE,
                        TipoToken.IDENTIFIER,TipoToken.ADICAO,TipoToken.STRING_LITERAL,TipoToken.SPACE,TipoToken.ADICAO,
                        TipoToken.SPACE, TipoToken.NUMBER_LITERAL, TipoToken.RIGHT_PAREN
                ));
    }

    @Test
    void expressionWithStringAndNumber() {
        validateTokens("\"hola\" + 54",
                Arrays.asList(
                        TipoToken.STRING_LITERAL, TipoToken.SPACE, TipoToken.ADICAO, TipoToken.SPACE, TipoToken.NUMBER_LITERAL
                ));
    }

    @Test
    void stringWithEscapeChars() {
        validateTokens("\" ho\\\" estoy adentro de quotes\\\"la \"",
                Arrays.asList(
                        TipoToken.STRING_LITERAL
                ));
    }

    @Test
    void wordWithQuotationInTheMiddel() {
        validateTokens("ho\"\"lal",
                Arrays.asList(
                        TipoToken.UNKOWN
                ));
    }

    @Test
    void unkownTokens() {
        validateTokens("let 3no: number = $#lala",
                Arrays.asList(
                        TipoToken.LET, TipoToken.SPACE, TipoToken.UNKOWN, TipoToken.COLON, TipoToken.SPACE, TipoToken.NUMBER_TYPE,
                        TipoToken.SPACE, TipoToken.ASSIGN, TipoToken.SPACE, TipoToken.UNKOWN
                ));
    }

    @Test
    void assignationStatementWithArithmeticOperations() {
        validateTokens("x = 5 * 3+ 2/5 -1;",
                Arrays.asList(
                TipoToken.IDENTIFIER, TipoToken.SPACE, TipoToken.ASSIGN, TipoToken.SPACE, TipoToken.NUMBER_LITERAL, TipoToken.SPACE,
                TipoToken.MULTIPLICACAO, TipoToken.SPACE, TipoToken.NUMBER_LITERAL, TipoToken.ADICAO, TipoToken.SPACE, TipoToken.NUMBER_LITERAL,
                TipoToken.DIVISAO, TipoToken.NUMBER_LITERAL, TipoToken.SPACE, TipoToken.SUBTRACAO, TipoToken.NUMBER_LITERAL, TipoToken.SEMI_COLON
                ));
    }

    @Test
    void assignationStatementOfDecimalNumber() {
        validateTokens("pi = 3.14;",
                Arrays.asList(
                TipoToken.IDENTIFIER, TipoToken.SPACE, TipoToken.ASSIGN, TipoToken.SPACE, TipoToken.NUMBER_LITERAL, TipoToken.SEMI_COLON
                ));
    }

    @Test
    void semiColonAfter() {
        validateTokens("let name=\"agustin\";print",
                Arrays.asList(
                TipoToken.LET, TipoToken.SPACE, TipoToken.IDENTIFIER, TipoToken.ASSIGN, TipoToken.STRING_LITERAL, TipoToken.SEMI_COLON, TipoToken.PRINT
                ));
    }

    @Test
    void printStatementWithStringStartingWithSpace() {
        validateTokens("print(\" mundo\");",
                Arrays.asList(
                TipoToken.PRINT, TipoToken.LEFT_PAREN, TipoToken.STRING_LITERAL, TipoToken.RIGHT_PAREN, TipoToken.SEMI_COLON
                ));
    }

    @Test
    void stringLiteralWithSingleQuotation() {
        validateTokens("let singleQuote: string = 'mundo';",
                Arrays.asList(
                TipoToken.LET, TipoToken.SPACE, TipoToken.IDENTIFIER, TipoToken.COLON, TipoToken.SPACE, TipoToken.STRING_TYPE, TipoToken.SPACE,
                        TipoToken.ASSIGN, TipoToken.SPACE, TipoToken.STRING_LITERAL, TipoToken.SEMI_COLON
                ));
    }

    private void validateTokens(String src, List<TipoToken> expectedTypes){
        final List<Token> tokens = lexer.generateTokens(src);
        final List<TipoToken> tokenTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        assertEquals(expectedTypes, tokenTypes);

    }
}
