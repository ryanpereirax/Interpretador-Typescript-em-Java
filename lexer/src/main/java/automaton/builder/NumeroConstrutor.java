package automaton.builder;

import automaton.LexicoContexto;
import token.TipoToken;
import automaton.LexicoAutomacaoEstado;


public class NumeroConstrutor extends ResumoLexicoEstado {

    NumeroConstrutor(LexicoContexto ctx) {
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
        else if(Character.isDigit(c)){
            this.ctx = ctx.addChar(c);
            return this;
        }
        else if(c =='.' && !ctx.getAccum().contains(".")){
            this.ctx = ctx.addChar(c);
            return this;
        }
        else if(c.toString().matches("[A-Za-z0-9]+") && ctx.getAccum().isEmpty()){
            return new ConstrutorAlphaNumerico(ctx.addChar(c));
        }
        else{
            return new ConstrutorDesconhecido(ctx.addChar(c));
        }
    }

    @Override
    public TipoToken obtainTokenType() {
        if(ctx.getAccum().endsWith(".")){
            return TipoToken.UNKOWN;
        }
        return TipoToken.NUMBER_LITERAL;
    }
}
