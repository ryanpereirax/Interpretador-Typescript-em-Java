import ast.OperacaoAritmetica;
import ast.expression.Identificador;
import ast.expression.Literal;
import ast.expression.Escalar;
import errorhandler.ManipuladorErros;
import ast.ExpressaoVisualizacao;
import ast.Tipo;
import ast.expression.ExpressaoAritmetica;

import java.util.Map;
import java.util.Optional;

public class AvaliadorDeExpressaoVisita implements ExpressaoVisualizacao {

    private ExecucaoContexto ctx;
    private Optional<Escalar> result;

    AvaliadorDeExpressaoVisita(ExecucaoContexto ctx) {
        this.ctx = ctx;
        this.result = Optional.empty();
    }

    @Override
    public void visit(ExpressaoAritmetica arithmeticExpression) {
        if (!ctx.conforms())
            return;
        arithmeticExpression.getLeft().accept(this);
        Optional<Escalar> leftOpt = this.result;
        arithmeticExpression.getRight().accept(this);
        Optional<Escalar> rightOpt = this.result;
        final OperacaoAritmetica operation = arithmeticExpression.getOperation();
        if (leftOpt.isPresent() && rightOpt.isPresent()) {
            Escalar leftValue = leftOpt.get();
            Escalar rightValue = rightOpt.get();
            if (leftValue.getType() != rightValue.getType()) {
                if (operation != OperacaoAritmetica.ADICAO)
                    ctx.reportViolation("[INTERPRETER] Invalid arithmetic operation for given types",
                            arithmeticExpression.getInputRange());
                else
                    this.result = Optional
                            .of(new Escalar(Tipo.STRING, leftValue.getStringValue() + rightValue.getStringValue()));
            } else {
                if (leftValue.getType() == Tipo.NUMBER) { // son dos strings
                    switch (operation) {
                        case ADICAO:
                            this.result = Optional.of(
                                    new Escalar(Tipo.NUMBER, leftValue.getNumberValue() + rightValue.getNumberValue()));
                            break;
                        case SUBTRACAO:
                            this.result = Optional.of(
                                    new Escalar(Tipo.NUMBER, leftValue.getNumberValue() - rightValue.getNumberValue()));
                            break;
                        case DIVISAO:
                            this.result = Optional.of(
                                    new Escalar(Tipo.NUMBER, leftValue.getNumberValue() / rightValue.getNumberValue()));
                            break;
                        case MULTIPLICACAO:
                            this.result = Optional.of(
                                    new Escalar(Tipo.NUMBER, leftValue.getNumberValue() * rightValue.getNumberValue()));
                            break;
                    }
                } else { // Son dos strings
                    if (operation == OperacaoAritmetica.ADICAO) {
                        this.result = Optional
                                .of(new Escalar(Tipo.STRING, leftValue.getStringValue() + rightValue.getStringValue()));
                    } else {
                        ctx.reportViolation("[INTERPRETER] Cannot handle arithmetic operations in strings",
                                arithmeticExpression.getInputRange());
                    }
                }
            }
        }
    }

    @Override
    public void visit(Identificador identifier) {
        if (!ctx.conforms())
            return;
        if (ctx.identifierIsDefined(identifier.getName())) {
            this.result = Optional.of(ctx.obtainIdentifierValue(identifier.getName()));
        } else {
            ctx.reportViolation("[INTERPRETER] Variable " + identifier.getName() + " was not defined",
                    identifier.getInputRange());
        }
    }

    @Override
    public void visit(Literal literal) {
        if (!ctx.conforms())
            return;
        result = Optional.of(literal.getScalar());
    }

    public void reset() {
        this.result = Optional.empty();
    }

    public Optional<Escalar> getResult() {
        return result;
    }
}
