package ast.statement;

import ast.ASTNo;
import ast.ASTVisita;
import ast.statement.Estado;
import token.EntradaRange;
import java.util.Collections;
import java.util.List;
import ast.expression.Expressao;


public class VisualizacaoEstado extends Estado {

    private final Expressao expressionToPrint;

    public VisualizacaoEstado(EntradaRange range, Expressao expressionToPrint) {
        super(range);
        this.expressionToPrint = expressionToPrint;
    }

    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return Collections.singletonList(expressionToPrint);
    }

    public Expressao getExpressionToPrint() {
        return expressionToPrint;
    }
}
