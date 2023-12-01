

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import lexer.VerdadeiroLexo;
import parser.Parser;
import parser.VerdadeiroParser;
import errorhandler.AcumuladorErros;
import lexer.Lexer;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Lexer lexer = new VerdadeiroLexo();
        Parser parser = new VerdadeiroParser(lexer);

        AcumuladorMensagem printAccum = new AcumuladorMensagem();
        AcumuladorErros errorAcum = new AcumuladorErros();

        Interpretador interpreter = new VerdadeiroInterpretador(parser);

        String src = new Scanner(new File("E:\\interpretadorTypescript\\runner\\src\\main\\java\\program.txt"))
                .useDelimiter("\\A").next();

        interpreter.execute(src, printAccum, errorAcum);

        if(errorAcum.getErrors().isEmpty()){
            System.out.println("Execucao Feita com Sucesso\n");
            printAccum.getMessages().forEach(msg-> System.out.println("> " + msg));
        }
        else {
            System.out.println("Falha na Execução\n");
            errorAcum.getErrors().forEach(msg -> System.out.println(msg + "\n"));
        }
    }

}
