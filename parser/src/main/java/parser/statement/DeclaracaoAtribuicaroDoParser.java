package parser.statement;

import ast.TipoPrimitivo;
import ast.expression.Identificador;
import ast.statement.Estado;
import errorhandler.ManipuladorErros;
import token.Token;
import token.TipoToken;
import ast.statement.DeclaracaoEstado;


import java.util.Arrays;
import java.util.List;

public class DeclaracaoAtribuicaroDoParser extends AbstracaoEstadoParser {

    DeclaracaoAtribuicaroDoParser(ManipuladorErros handler) {
        super(handler);
    }

    @Override
    public Estado parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() == 4) {
            final Token let = statement.get(0);
            final Token identifier = statement.get(1);
            final Token colon = statement.get(2);
            final Token type = statement.get(3);
            if (validateTypes(Arrays.asList(let, identifier, colon),
                    Arrays.asList(TipoToken.LET, TipoToken.IDENTIFIER, TipoToken.COLON)) &&
                    (type.getType() == TipoToken.STRING_TYPE || type.getType() == TipoToken.NUMBER_TYPE)) {
                return new DeclaracaoEstado(
                        getRange(identifier, type),
                        new TipoPrimitivo(type.getRange(), fromTokenTypeToLiteralType(type.getType())),
                        new Identificador(identifier.getRange(), identifier.getValue()));
            }
        }
        handleInvalidStatement(getRange(statement.get(0), statement.get(statement.size() -1)));
        return null;
    }
}
