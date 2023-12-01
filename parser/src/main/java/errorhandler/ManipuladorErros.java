package errorhandler;

import token.EntradaRange;

public interface ManipuladorErros {
    void reportViolation(String message, EntradaRange range);
    void reportViolation(String message);
    boolean conforms();
}
