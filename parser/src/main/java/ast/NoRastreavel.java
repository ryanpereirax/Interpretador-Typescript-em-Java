package ast;

import token.EntradaRange;

public abstract class NoRastreavel implements ASTNo {
    private final EntradaRange range;

    public NoRastreavel(EntradaRange range) {
        this.range = range;
    }

    public EntradaRange getInputRange(){ return range;}
}
