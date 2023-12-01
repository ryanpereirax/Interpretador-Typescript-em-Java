package automaton;

import token.Token;

public interface LexicoAutomacaoEstado {
    boolean isAcceptanceState();
    LexicoAutomacaoEstado next(Character c);
    Token obtainToken();
}
