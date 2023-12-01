package parser.statement;

import ast.statement.Estado;
import token.Token;

import java.util.List;

public interface DeclaracaoParser {
    Estado parseToStatement(List<Token> statementTokens);
}
