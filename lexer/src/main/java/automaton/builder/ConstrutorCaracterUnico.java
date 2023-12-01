package automaton.builder;
import automaton.LexicoContexto;
import token.TipoToken;
import automaton.LexicoAutomacaoEstado;


import java.util.Arrays;

public class ConstrutorCaracterUnico extends ResumoLexicoEstado {

    public ConstrutorCaracterUnico(LexicoContexto ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return false;
    }

    @Override
    public LexicoAutomacaoEstado next(Character c) {
        //si tengo un char, me mando al helper on el ctx vacio y nuevo char
        //si esta vacio me quedo en el estado guardando el char
        if(specialChars.contains(c)){
            return new ConstrutorCaracterUnico(ctx.resetAccum().addChar(c));
        }
        else {
            return new EstadoAuxCaracterUnico(ctx.resetAccum().addChar(c));
        }
    }

    @Override
    public TipoToken obtainTokenType() {
        switch(ctx.getAccum()){
            case ":": return TipoToken.COLON;
            case ";": return TipoToken.SEMI_COLON;
            case " ": return TipoToken.SPACE;
            case "\t": return TipoToken.TAB;
            case "\n": return TipoToken.NEW_LINE;
            case "+": return TipoToken.ADICAO;
            case "-": return TipoToken.SUBTRACAO;
            case "*": return TipoToken.MULTIPLICACAO;
            case "/": return TipoToken.DIVISAO;
            case "=": return TipoToken.ASSIGN;
            case "(": return TipoToken.LEFT_PAREN;
            case ")": return TipoToken.RIGHT_PAREN;
            default: return TipoToken.UNKOWN;
        }
    }
}
