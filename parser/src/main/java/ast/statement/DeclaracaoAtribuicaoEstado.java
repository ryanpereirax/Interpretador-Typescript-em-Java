package ast.statement;

import ast.*;
import ast.expression.Identificador;
import token.EntradaRange;
import java.util.Arrays;
import java.util.List;
import ast.expression.Expressao;


public class DeclaracaoAtribuicaoEstado extends Estado {

    private final TipoPrimitivo type;
    private final Identificador identifier;
    private final Expressao expression;

    public DeclaracaoAtribuicaoEstado(EntradaRange range, TipoPrimitivo type, Identificador identifier, Expressao expression) {
        super(range);
        this.type = type;
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return Arrays.asList(type, identifier, expression);
    }

    public TipoPrimitivo getType() {
        return type;
    }

    public Identificador getIdentifier() {
        return identifier;
    }

    public Expressao getExpression() {
        return expression;
    }
}
