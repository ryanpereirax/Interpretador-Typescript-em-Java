package ast.statement;

import ast.*;
import token.EntradaRange;
import java.util.Collections;
import java.util.List;
import ast.expression.Identificador;


public class DeclaracaoEstado extends Estado {

    private final TipoPrimitivo type;
    private final Identificador identifier;

    public DeclaracaoEstado(EntradaRange range, TipoPrimitivo type, Identificador identifier) {
        super(range);
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return Collections.singletonList(identifier);
    }

    public TipoPrimitivo getType() {
        return type;
    }

    public Identificador getIdentifier() {
        return identifier;
    }
}
