package ast.expression;

import ast.NoRastreavel;
import token.EntradaRange;
import ast.ExpressaoVisualizacao;

public abstract class Expressao extends NoRastreavel {

    public Expressao(EntradaRange range) {
        super(range);
    }
    public abstract void accept(ExpressaoVisualizacao visitor);

}
