package parser.statement;

import ast.statement.VisualizacaoEstado;
import parser.ExpressaoParser;
import token.Token;
import token.TipoToken;

import ast.statement.Estado;
import errorhandler.ManipuladorErros;

import java.util.Arrays;
import java.util.List;

public class VisualizacaoParser extends AbstracaoEstadoParser {

    private ExpressaoParser expressionParser;

    VisualizacaoParser(ManipuladorErros handler, ExpressaoParser parser) {
        super(handler);
        this.expressionParser = parser;
    }

    @Override
    public Estado parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() >= 4) {
            final Token print = statement.get(0);
            final Token leftParen = statement.get(1);
            final Token rightParen = statement.get(statement.size() - 1);
            if (validateTypes(Arrays.asList(print, leftParen, rightParen),
                    Arrays.asList(TipoToken.PRINT, TipoToken.LEFT_PAREN, TipoToken.RIGHT_PAREN))) {
                return new VisualizacaoEstado(
                        getRange(print, rightParen),
                        expressionParser.parse(statement.subList(2, statement.size() - 1)));
            }
        }
        handleInvalidStatement(getRange(statement.get(0), statement.get(statement.size() -1)));
        return null;
    }
}
