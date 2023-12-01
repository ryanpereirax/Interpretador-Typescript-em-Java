package automaton.builder;

import token.Token;
import token.TipoToken;
import automaton.LexicoAutomacaoEstado;
import automaton.LexicoContexto;


import java.util.Arrays;

public class EstadoAuxCaracterUnico extends ResumoLexicoEstado {

    public EstadoAuxCaracterUnico(LexicoContexto ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return false;
    }

    @Override
    public LexicoAutomacaoEstado next(Character c) {
        return handleNormalCase(c);
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
            default:
                if(ctx.getAccum().matches("\\d+")) return TipoToken.NUMBER_LITERAL;
                else return TipoToken.UNKOWN;
        }
    }
}
