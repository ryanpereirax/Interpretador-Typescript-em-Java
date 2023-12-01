package automaton.builder;

import java.util.Arrays;
import java.util.List;
import token.EntradaRange;
import token.VerdadeiroToken;
import token.Token;
import token.TipoToken;
import automaton.LexicoAutomacaoEstado;
import automaton.LexicoContexto;


abstract class ResumoLexicoEstado implements LexicoAutomacaoEstado {

    LexicoContexto ctx;
    List<Character> specialChars = Arrays.asList(';', ':', '\n', '\t', ' ', '=', '+', '-', '*', '/', '(', ')');

    public abstract TipoToken obtainTokenType();

    @Override
    public Token obtainToken() {
        String value = ctx.getAccum();
        EntradaRange range = ctx.getRange();
        return new VerdadeiroToken(value, range, obtainTokenType());
    }

    ResumoLexicoEstado handleNormalCase(Character c){
        String currentAccum = ctx.getAccum() + c;
        if(currentAccum.startsWith("\"") || currentAccum.startsWith("\'"))
            return new ConstrutorString(ctx.addChar(c));
        else if(specialChars.contains(c))
            return new ConstrutorCaracterUnico(ctx.resetAccum().addChar(c));
        else if(currentAccum.matches("[0-9]+(\\.[0-9]*)?")) //is a number
            return new NumeroConstrutor(ctx.addChar(c));
        else if (currentAccum.matches("[A-Za-z_$][A-Za-z0-9_$]*"))
            return new ConstrutorAlphaNumerico(ctx.addChar(c));
        else
            return new ConstrutorDesconhecido(ctx.addChar(c));
    }

    ResumoLexicoEstado(LexicoContexto ctx) {
        this.ctx = ctx;
    }
}
