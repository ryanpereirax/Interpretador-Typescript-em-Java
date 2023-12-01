package parser.statement;

import ast.NoVazio;
import ast.Tipo;
import token.EntradaRange;
import token.VerdadeiraEntradaRange;
import token.Token;
import token.TipoToken;
import ast.statement.Estado;
import errorhandler.ManipuladorErros;


import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstracaoEstadoParser implements DeclaracaoParser {

    private ManipuladorErros errorHandler;

    AbstracaoEstadoParser(ManipuladorErros handler) {
        this.errorHandler = handler;
    }

    void setHander(ManipuladorErros newHandler){
        this.errorHandler = newHandler;
    }

    boolean validateTypes(List<Token> tokens, List<TipoToken> expectedTypes) {
        final List<TipoToken> actualTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        for (int i = 0; i < actualTypes.size(); i++) {
            if (actualTypes.get(i) != expectedTypes.get(i)) {
                return false;
            }
        }
        return true;
    }

    void handleInvalidStatement(EntradaRange range) {
        errorHandler.reportViolation("[PARSER] Invalid statement", range);
    }

    void checkForUnkownTokens(List<Token> tokens) {
        final List<Token> invalidTokens = tokens.stream()
                .filter(token -> token.getType() == TipoToken.UNKOWN)
                .collect(Collectors.toList());
        if(!invalidTokens.isEmpty()){
            invalidTokens.forEach(token -> errorHandler.reportViolation("[LEXER] Invalid token.", token.getRange()));
        }
    }

    Tipo fromTokenTypeToLiteralType(TipoToken type) {
        if(type == TipoToken.STRING_TYPE) return Tipo.STRING;
        else return Tipo.NUMBER;
    }

    EntradaRange getRange(Token start, Token end) {
        final EntradaRange startRange = start.getRange();
        final EntradaRange endRange = end.getRange();
        return new VerdadeiraEntradaRange(startRange.getStartLine(), startRange.getStartColumn(), endRange.getEndLine(), endRange.getEndColumn());
    }
}
