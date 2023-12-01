import errorhandler.ManipuladorErros;

public interface Interpretador {
    void execute(String src, EmissorMensagem emitter, ManipuladorErros handler);
}
