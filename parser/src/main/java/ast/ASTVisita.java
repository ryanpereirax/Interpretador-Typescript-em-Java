package ast;

import ast.expression.ExpressaoAritmetica;
import ast.statement.DeclaracaoAtribuicao;
import ast.statement.DeclaracaoAtribuicaoEstado;
import ast.statement.DeclaracaoEstado;
import ast.statement.VisualizacaoEstado;
import ast.expression.Identificador;
import ast.expression.Literal;


public interface ASTVisita extends ExpressaoVisualizacao {
    void visit(Programa program);
    void visit(NoVazio empty);

    void visit(DeclaracaoAtribuicao assignStatement);
    void visit(DeclaracaoAtribuicaoEstado declareAssignStatement);
    void visit(DeclaracaoEstado declareStatement);
    void visit(VisualizacaoEstado printStatement);
}
