package parser;

import ast.OperacaoAritmetica;
import ast.Tipo;
import ast.expression.ExpressaoAritmetica;
import ast.expression.Expressao;
import ast.expression.Identificador;
import ast.expression.Literal;
import errorhandler.ManipuladorErros;
import token.EntradaRange;
import token.VerdadeiraEntradaRange;
import token.Token;
import token.TipoToken;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExpressaoParser {

    private ManipuladorErros errorHandler;

    public ExpressaoParser(ManipuladorErros errorHandler) {
        this.errorHandler = errorHandler;
    }

    public Expressao parse(List<Token> tokens) {
        return tryParsingArithmeticOperationOf(TipoToken.ADICAO, tokens).orElseGet(
                () -> tryParsingArithmeticOperationOf(TipoToken.SUBTRACAO, tokens).orElseGet(
                        () -> tryParsingArithmeticOperationOf(TipoToken.MULTIPLICACAO, tokens).orElseGet(
                                ()-> tryParsingArithmeticOperationOf(TipoToken.DIVISAO, tokens).orElseGet(
                                        ()-> parseIndentifierOrLiteral(tokens)
                                )
                        )
                )
        );
    }

    private Optional<Expressao> tryParsingArithmeticOperationOf(TipoToken type,List<Token> tokens) {
        final int size = tokens.size();
        if(size < 3) return Optional.empty();
        final Token start = tokens.get(0);
        final Token end = tokens.get(size - 1);
        final List<TipoToken> types = tokens.stream().map(Token::getType).collect(Collectors.toList());
        final int operationIndex = types.indexOf(type);
        if (operationIndex != -1) {
            final List<Token> left = tokens.subList(0, operationIndex);
            final List<Token> right = tokens.subList(operationIndex+1, size);
            return Optional.of(new ExpressaoAritmetica(getRange(start, end),
                    parse(left),
                    parse(right),
                    fromTokenTypeToArithmeticOperation(type)));
        } else return Optional.empty();
    }

    private Expressao parseIndentifierOrLiteral(List<Token> tokens){
        if(tokens.isEmpty()){
            errorHandler.reportViolation("[PARSER] Expected expression not defined");
            return null;
        }
        else if(tokens.size()>1){
            errorHandler.reportViolation("[PARSER] Invalid expression", getRange(tokens.get(0), tokens.get(tokens.size()-1)));
        }
        final Token token = tokens.get(0);
        if(token.getType() == TipoToken.IDENTIFIER) return new Identificador(token.getRange(), token.getValue());
        else if (token.getType() == TipoToken.NUMBER_LITERAL) return new Literal(token.getRange(), Tipo.NUMBER, token.getValue());
        else if (token.getType() == TipoToken.STRING_LITERAL) return new Literal(token.getRange(), Tipo.STRING, token.getValue().substring(1, token.getValue().length() - 1));
        else {
            errorHandler.reportViolation("[PARSER] Expected expression but found " + token.getValue(),token.getRange());
            return null;
        }
    }

    private OperacaoAritmetica fromTokenTypeToArithmeticOperation(TipoToken type) {
        if(type == TipoToken.ADICAO) return OperacaoAritmetica.ADICAO;
        else if(type == TipoToken.SUBTRACAO) return OperacaoAritmetica.SUBTRACAO;
        else if(type == TipoToken.MULTIPLICACAO) return OperacaoAritmetica.MULTIPLICACAO;
        else return OperacaoAritmetica.DIVISAO;
    }

    private EntradaRange getRange(Token start, Token end) {
        final EntradaRange startRange = start.getRange();
        final EntradaRange endRange = end.getRange();
        return new VerdadeiraEntradaRange(startRange.getStartLine(), startRange.getStartColumn(), endRange.getEndLine(), endRange.getEndColumn());
    }

    public void setHandler(ManipuladorErros handler) {
        this.errorHandler = handler;
    }
}
