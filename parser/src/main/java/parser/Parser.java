package parser;

import ast.ASTNo;
import errorhandler.ManipuladorErros;

public interface Parser {
    ASTNo parse(String src, ManipuladorErros handler);
}
