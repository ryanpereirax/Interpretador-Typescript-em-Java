package parser.statement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import errorhandler.AcumuladorErros;
import errorhandler.ManipuladorErros;
import parser.ExpressaoParser;
import token.Token;
import token.TipoToken;



public class DeclaracaoParserFornecedor {

    private DeclaracaoAtribuicaoState assignParser;
    private DeclaracaoAtribuicaoParser declareAssignParser;
    private VisualizacaoParser printParser;
    private DeclaracaoAtribuicaroDoParser declareParser;
    private ExpressaoParser expressionParser;

    public DeclaracaoParserFornecedor() {
        ManipuladorErros defaultHandler = new AcumuladorErros();
        expressionParser = new ExpressaoParser(defaultHandler);
        assignParser = new DeclaracaoAtribuicaoState(defaultHandler, expressionParser);
        declareAssignParser = new DeclaracaoAtribuicaoParser(defaultHandler, expressionParser);
        printParser = new VisualizacaoParser(defaultHandler, expressionParser);
        declareParser = new DeclaracaoAtribuicaroDoParser(defaultHandler);
    }

    public void setHandler(ManipuladorErros handler) {
        final List<AbstracaoEstadoParser> parsers = Arrays.asList(assignParser, declareAssignParser, printParser, declareParser);
        parsers.forEach(parser -> parser.setHander(handler));
        expressionParser.setHandler(handler);
    }

    public DeclaracaoParser obtainParser(List<Token> statementTokens){
        final List<TipoToken> types = statementTokens.stream().map(Token::getType).collect(Collectors.toList());
        if (types.contains(TipoToken.PRINT)) {
            return printParser;
        }
        else if (types.contains(TipoToken.LET) && types.contains(TipoToken.ASSIGN)){
            return declareAssignParser;
        }
        else if (types.contains(TipoToken.LET)){
            return declareParser;
        }
        else {
            return assignParser;
        }
    }
}
