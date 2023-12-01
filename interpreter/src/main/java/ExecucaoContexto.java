import ast.expression.Escalar;
import token.EntradaRange;
import java.util.HashMap;
import java.util.Map;
import errorhandler.ManipuladorErros;


class ExecucaoContexto {
    private Map<String, Escalar> scopeVariables;
    private EmissorMensagem emitter;
    private ManipuladorErros handler;
    private boolean conforms;

    ExecucaoContexto(EmissorMensagem emitter, ManipuladorErros handler) {
        this.emitter = emitter;
        this.handler = handler;
        this.conforms = true;
        this.scopeVariables = new HashMap<>();
    }

    Escalar obtainIdentifierValue(String name) {
        return scopeVariables.get(name);
    }

    void registerNewVariable(String name, Escalar scalar) {
        scopeVariables.put(name, scalar);
    }

    boolean identifierIsDefined(String name) {
        return scopeVariables.containsKey(name);
    }

    void reportViolation(String msg, EntradaRange range){
        if(conforms){
            handler.reportViolation(msg, range);
            this.conforms = false;
        }
    }

    void reportViolation(String msg){
        if(conforms) {
            handler.reportViolation(msg);
            this.conforms = false;
        }
    }

    boolean conforms() {
        return conforms;
    }

    void print(String s) {
        emitter.print(s);
    }
}
