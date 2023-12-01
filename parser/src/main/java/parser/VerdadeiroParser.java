package parser;

import ast.ASTNo;
import ast.NoVazio;
import ast.Programa;
import ast.statement.Estado;
import errorhandler.ManipuladorErros;
import lexer.Lexer;
import parser.statement.DeclaracaoParser;
import parser.statement.DeclaracaoParserFornecedor;
import token.Token;
import token.TipoToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VerdadeiroParser implements Parser {

    private Lexer lexer;
    private DeclaracaoParserFornecedor provider;

    public VerdadeiroParser(Lexer lexer) {
        this.lexer = lexer;
        provider = new DeclaracaoParserFornecedor();
    }

    @Override
    public ASTNo parse(String src, ManipuladorErros handler) {
        List<Token> tokens = lexer.generateTokens(src);

        final List<Token> filtered = tokens.stream().filter(token -> token.getType() != TipoToken.SPACE && token.getType() != TipoToken.TAB
                && token.getType() != TipoToken.NEW_LINE).collect(Collectors.toList());

        provider.setHandler(handler);
        List<Token> statementTokens = new ArrayList<>();
        List<Estado> generatedStatements = new ArrayList<>();
        for (Token token : filtered) {
            if(token.getType() == TipoToken.SEMI_COLON){
                generatedStatements.add(provider.obtainParser(statementTokens).parseToStatement(statementTokens));
                statementTokens = new ArrayList<>();
            }
            else
                statementTokens.add(token);
        }
        if(!statementTokens.isEmpty()) handler.reportViolation("[PARSER] Missing semi-colon to end statement", statementTokens.get(statementTokens.size()-1).getRange());
        return new Programa(generatedStatements);
    }
}
