package ast.expression;

import ast.ASTNo;
import ast.ExpressaoVisualizacao;
import token.EntradaRange;
import ast.ASTVisita;

import java.util.ArrayList;
import java.util.List;

public class Identificador extends Expressao {

    private final String name;

    public Identificador(EntradaRange range, String name) {
        super(range);
        this.name = name;
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

    public String getName() {
        return name;
    }
}
