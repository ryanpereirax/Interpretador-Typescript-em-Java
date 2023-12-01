package ast.expression;

import ast.ASTNo;
import ast.ExpressaoVisualizacao;
import token.EntradaRange;
import java.util.Arrays;
import java.util.List;
import ast.ASTVisita;
import ast.OperacaoAritmetica;


public class ExpressaoAritmetica extends Expressao {

    private final Expressao left;
    private final Expressao right;
    private final OperacaoAritmetica operation;

    public ExpressaoAritmetica(EntradaRange range, Expressao left, Expressao right, OperacaoAritmetica operation) {
        super(range);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(ExpressaoVisualizacao visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return Arrays.asList(left,right);
    }

    public Expressao getLeft() {
        return left;
    }

    public Expressao getRight() {
        return right;
    }

    public OperacaoAritmetica getOperation() {
        return operation;
    }
}
