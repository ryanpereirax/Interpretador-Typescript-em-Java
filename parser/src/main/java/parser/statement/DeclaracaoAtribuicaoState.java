package parser.statement;

import ast.expression.Identificador;
import parser.ExpressaoParser;
import token.Token;
import token.TipoToken;
import ast.statement.DeclaracaoAtribuicao;
import ast.statement.Estado;
import errorhandler.ManipuladorErros;


import java.util.Arrays;
import java.util.List;

public class DeclaracaoAtribuicaoState extends AbstracaoEstadoParser {

    private ExpressaoParser parser;

    DeclaracaoAtribuicaoState(ManipuladorErros handler, ExpressaoParser parser) {
        super(handler);
        this.parser = parser;
    }

    @Override
    public Estado parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() >= 3) {
            final Token identifier = statement.get(0);
            final Token assign = statement.get(1);
            if (validateTypes(Arrays.asList(identifier, assign),
                    Arrays.asList(TipoToken.IDENTIFIER, TipoToken.ASSIGN))) {
                return new DeclaracaoAtribuicao(
                        getRange(identifier, statement.get(statement.size() - 1)),
                        new Identificador(identifier.getRange(), identifier.getValue()),
                        parser.parse(statement.subList(2, statement.size())));
            }
        }
        handleInvalidStatement(getRange(statement.get(0), statement.get(statement.size() -1)));
        return null;
    }
}
