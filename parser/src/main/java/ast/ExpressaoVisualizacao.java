package ast;

import ast.expression.Identificador;
import ast.expression.Literal;
import ast.expression.ExpressaoAritmetica;

public interface ExpressaoVisualizacao {
    void visit(ExpressaoAritmetica arithmeticExpression);
    void visit(Identificador identifier);
    void visit(Literal literal);
}
