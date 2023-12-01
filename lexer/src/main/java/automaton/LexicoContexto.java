package automaton;

import token.VerdadeiraEntradaRange;
import lexer.Lexer;
import token.EntradaRange;


public class LexicoContexto {

    private final String accum;
    private final VerdadeiraEntradaRange range;

    public LexicoContexto() {
        this.accum = "";
        this.range = new VerdadeiraEntradaRange();
    }

    private LexicoContexto(String accum, VerdadeiraEntradaRange range) {
        this.accum = accum;
        this.range = range;
    }

    public LexicoContexto resetAccum(){
        return new LexicoContexto("", range.startFromNextPosition());
    }

    public LexicoContexto addChar(Character character) {
        VerdadeiraEntradaRange newRange;
        if(character == '\n'){
            newRange = range.moveLine();
        }
        else if (character == '\t') {
            newRange = range.moveColumn(4);
        }
        else {
            newRange = range.moveColumn(1);
        }
        return new LexicoContexto(accum + character, newRange);
    }

    public String getAccum() {
        return accum;
    }

    public EntradaRange getRange() {
        return range;
    }
}
