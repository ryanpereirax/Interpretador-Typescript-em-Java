import errorhandler.ManipuladorErros;
import parser.Parser;
import ast.ASTNo;

public class VerdadeiroInterpretador implements Interpretador{
    private Parser parser;

    public VerdadeiroInterpretador(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void execute(String src, EmissorMensagem emitter, ManipuladorErros handler) {
        ExecucaoVisita visitor = new ExecucaoVisita(emitter, handler);
        final ASTNo astNode = parser.parse(src, handler);
        if(handler.conforms()) astNode.accept(visitor);
    }
}
