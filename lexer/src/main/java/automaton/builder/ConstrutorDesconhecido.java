package automaton.builder;

import automaton.LexicoAutomacaoEstado;
import automaton.LexicoContexto;
import token.TipoToken;

public class ConstrutorDesconhecido extends ResumoLexicoEstado {

    public ConstrutorDesconhecido(LexicoContexto ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return true;
    }

    @Override
    public LexicoAutomacaoEstado next(Character c) {
        if(specialChars.contains(c)){
            return new ConstrutorCaracterUnico(ctx.resetAccum().addChar(c));
        }
        else{
            this.ctx = ctx.addChar(c);
            return this;
        }
    }

    @Override
    public TipoToken obtainTokenType() {
        return TipoToken.UNKOWN;
    }

}
