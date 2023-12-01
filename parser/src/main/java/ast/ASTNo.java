package ast;

import java.util.List;

public interface ASTNo {
    void accept(ASTVisita visitor);
    List<ASTNo> getChildren();
}
