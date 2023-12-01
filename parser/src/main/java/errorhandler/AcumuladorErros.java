package errorhandler;

import token.EntradaRange;

import java.util.LinkedList;
import java.util.List;

public class AcumuladorErros implements ManipuladorErros {

    private List<String> errors = new LinkedList<>();


    @Override
    public void reportViolation(String message, EntradaRange range) {
        errors.add(message + "\nRange -> from (line: "+range.getStartLine()+", col: "+range.getStartColumn()+
                ") to (line: "+range.getEndLine()+", col "+range.getEndColumn()+")");
    }

    @Override
    public void reportViolation(String message) {
        errors.add(message);
    }

    public List<String> getErrors() { return errors;}

    @Override
    public boolean conforms() {
        return errors.isEmpty();
    }
}
