package ast.statement;

import ast.*;
import ast.expression.Identificador;
import token.EntradaRange;
import java.util.Arrays;
import java.util.List;
import ast.expression.Expressao;


public class DeclaracaoAtribuicao extends Estado {

    private final Identificador identifier;
    private final Expressao expression;

    public DeclaracaoAtribuicao(EntradaRange range, Identificador identifier, Expressao expression) {
        super(range);
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return Arrays.asList(identifier, expression);
    }

    public Identificador getIdentifier() {
        return identifier;
    }

    public Expressao getExpression() {
        return expression;
    }
}
