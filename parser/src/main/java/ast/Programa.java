package ast;

import java.util.ArrayList;
import java.util.List;
import ast.statement.Estado;
import token.VerdadeiraEntradaRange;

public class Programa extends NoRastreavel {

    private final List<Estado> statements;

    public Programa(List<Estado> statements) {
        super(new VerdadeiraEntradaRange()); 
        this.statements = statements;
    }

    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return new ArrayList<ASTNo>(statements);
    }

    public List<Estado> getStatements() {
        return new ArrayList<>(statements);
    }
}
