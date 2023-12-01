package token;

public class VerdadeiroToken implements Token {

    private String value;
    private EntradaRange range;
    private TipoToken type;

    public VerdadeiroToken(String value, EntradaRange range, TipoToken type) {
        this.value = value;
        this.range = range;
        this.type = type;
    }

    @Override
    public TipoToken getType() {
        return type;
    }

    @Override
    public EntradaRange getRange() {
        return range;
    }

    @Override
    public String getValue() {
        return value;
    }
}
