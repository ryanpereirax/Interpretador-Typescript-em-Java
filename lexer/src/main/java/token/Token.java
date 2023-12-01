package token;

public interface Token {
    TipoToken getType();
    EntradaRange getRange();
    String getValue();
}
