package ast.expression;

import ast.ASTNo;
import ast.ExpressaoVisualizacao;
import ast.Tipo;
import token.EntradaRange;
import ast.ASTVisita;


import java.util.ArrayList;
import java.util.List;

public class Literal extends Expressao {

    private final Escalar scalar;


    public Literal(EntradaRange range, Tipo type, Object value) {
        super(range);
        this.scalar = new Escalar(type, value);
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
        return new ArrayList<>();
    }

    public Tipo getType() {
        return scalar.getType();
    }

    public Escalar getScalar() {
        return scalar;
    }

    public String getStringValue() {
        return scalar.getStringValue();
    }

    public Double getNumberValue() {
        return scalar.getNumberValue();
    }
}
