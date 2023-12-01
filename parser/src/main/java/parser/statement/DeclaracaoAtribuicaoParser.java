package parser.statement;

import ast.TipoPrimitivo;
import ast.expression.Identificador;
import errorhandler.ManipuladorErros;
import parser.ExpressaoParser;
import token.Token;
import token.TipoToken;
import ast.statement.DeclaracaoAtribuicaoEstado;
import ast.statement.Estado;


import java.util.Arrays;
import java.util.List;

public class DeclaracaoAtribuicaoParser extends AbstracaoEstadoParser {

    private ExpressaoParser expressionParser;

    DeclaracaoAtribuicaoParser(ManipuladorErros handler, ExpressaoParser expressionParser) {
        super(handler);
        this.expressionParser = expressionParser;
    }

    @Override
    public Estado parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() >= 6) {
            final Token let = statement.get(0);
            final Token identifier = statement.get(1);
            final Token colon = statement.get(2);
            final Token type = statement.get(3);
            final Token assign = statement.get(4);
            if (validateTypes(Arrays.asList(let, identifier, colon,assign),
                    Arrays.asList(TipoToken.LET, TipoToken.IDENTIFIER, TipoToken.COLON, TipoToken.ASSIGN)) &&
                    (type.getType() == TipoToken.STRING_TYPE || type.getType() == TipoToken.NUMBER_TYPE)) {
                return new DeclaracaoAtribuicaoEstado(
                        getRange(let, statement.get(statement.size()-1)),
                        new TipoPrimitivo(type.getRange(), fromTokenTypeToLiteralType(type.getType())),
                        new Identificador(identifier.getRange(), identifier.getValue()),
                        expressionParser.parse(statement.subList(5, statement.size()))
                );
            }
        }
        handleInvalidStatement(getRange(statement.get(0), statement.get(statement.size() -1)));
        return null;
    }
}
