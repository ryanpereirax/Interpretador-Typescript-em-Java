package ast;

import java.util.ArrayList;
import java.util.List;

public class NoVazio implements ASTNo {
    @Override
    public void accept(ASTVisita visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNo> getChildren() {
        return new ArrayList<>();
    }
}
