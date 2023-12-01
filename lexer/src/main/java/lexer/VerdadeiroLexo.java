package lexer;

import automaton.LexicoAutomacaoEstado;
import automaton.LexicoContexto;
import token.Token;
import automaton.builder.ConstrutorAlphaNumerico;

import java.util.LinkedList;
import java.util.List;

public class VerdadeiroLexo implements Lexer {

    @Override
    public List<Token> generateTokens(String src) {

        LexicoAutomacaoEstado currentState = new ConstrutorAlphaNumerico(new LexicoContexto());
        List<Token> tokens = new LinkedList<>();

        for (char character : src.toCharArray()) {
            final LexicoAutomacaoEstado newState = currentState.next(character);
            if(!newState.isAcceptanceState()){
                tokens.add(currentState.obtainToken());
            }
            currentState = newState;
        }
        tokens.add(currentState.obtainToken());

        return tokens;
    }
}
