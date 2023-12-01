package ast;

import token.EntradaRange;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class TipoPrimitivo extends NoRastreavel {
    private final Tipo type;

    public TipoPrimitivo(EntradaRange range, Tipo type) {
        super(range);
        this.type = type;
    }

    @Override
    public void accept(ASTVisita visitor) {

    }

    @Override
    public List<ASTNo> getChildren() {
        return new ArrayList<>();
    }

    public Tipo getType() {
        return type;
    }
}
