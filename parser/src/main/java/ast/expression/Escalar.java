package ast.expression;

import token.TipoToken;
import ast.Tipo;

public class Escalar {
    private final Tipo type;
    private final Object value;
    private final boolean isDefined;

    public Escalar(Tipo type, Object value){
        this.type = type;
        if(type == Tipo.NUMBER)
            this.value = new Double(value.toString());
        else
            this.value = value;

        this.isDefined = true;
    }

    public Escalar(Tipo type){
        this.type = type;
        this.isDefined = false;
        this.value = null;
    }

    public String getStringValue() {
        return value.toString();
    }

    public Double getNumberValue() {
        return new Double(value.toString());
    }

    public Tipo getType() {
        return type;
    }

    public boolean isDefined() {
        return isDefined;
    }
}

