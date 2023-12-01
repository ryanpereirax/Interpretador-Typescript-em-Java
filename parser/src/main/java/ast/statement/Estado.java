package ast.statement;

import token.EntradaRange;
import ast.NoRastreavel;

public abstract class Estado extends NoRastreavel {
    public Estado(EntradaRange range) {
        super(range);
    }
}
