package automaton.builder;
import automaton.LexicoContexto;
import token.TipoToken;
import automaton.LexicoAutomacaoEstado;


import java.util.Arrays;

public class ConstrutorAlphaNumerico extends ResumoLexicoEstado {

    public ConstrutorAlphaNumerico(LexicoContexto ctx){
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return true;
    }

    @Override
    public LexicoAutomacaoEstado next(Character c) {
        return handleNormalCase(c);
    }

    @Override
    public TipoToken obtainTokenType() {
        switch (ctx.getAccum()) {
            case "let":
                return TipoToken.LET;
            case "string":
                return TipoToken.STRING_TYPE;
            case "number":
                return TipoToken.NUMBER_TYPE;
            case "print":
                return TipoToken.PRINT;
            default:
                return TipoToken.IDENTIFIER;

        }
    }
}
