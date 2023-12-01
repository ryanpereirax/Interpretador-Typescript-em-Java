package automaton.builder;

import automaton.LexicoContexto;
import token.TipoToken;
import automaton.LexicoAutomacaoEstado;


public class ConstrutorString extends ResumoLexicoEstado {

    public ConstrutorString(LexicoContexto ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return true;
    }

    @Override
    public LexicoAutomacaoEstado next(Character c) {
        if(ctx.getAccum().matches("\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"|\\'([^\\'\\\\]*(\\\\.[^\\'\\\\]*)*)\\'")){ // el string esta cerrado
            return new ConstrutorCaracterUnico(ctx.resetAccum().addChar(c));
        }
        else if(!ctx.getAccum().startsWith("\"") && !ctx.getAccum().startsWith("\'")){
            return new ConstrutorDesconhecido(ctx.addChar(c));
        }
        else{
            this.ctx = ctx.addChar(c);
            return this;
        }
    }

    @Override
    public TipoToken obtainTokenType() {
        return TipoToken.STRING_LITERAL;
    }
}
