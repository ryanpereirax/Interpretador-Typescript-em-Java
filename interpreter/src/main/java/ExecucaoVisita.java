import ast.*;
import ast.expression.Literal;
import ast.expression.Escalar;
import ast.statement.*;
import errorhandler.ManipuladorErros;
import ast.expression.ExpressaoAritmetica;
import ast.expression.Identificador;


import java.util.Optional;

public class ExecucaoVisita implements ASTVisita {

    private ExecucaoContexto ctx;
    private AvaliadorDeExpressaoVisita expressionEvaluator;

    ExecucaoVisita(EmissorMensagem emitter, ManipuladorErros handler) {
        this.ctx = new ExecucaoContexto(emitter, handler);
        expressionEvaluator = new AvaliadorDeExpressaoVisita(this.ctx);

    }

    @Override
    public void visit(Programa program) {
        for (Estado statement : program.getStatements()) {
            statement.accept(this);
            if(!ctx.conforms()) return;
        }
    }

    @Override
    public void visit(NoVazio empty) {
        ctx.reportViolation("[INTERPRETER] Program was parsed incorrectly");
    }

    @Override
    public void visit(DeclaracaoAtribuicao assignStatement) {
        if(!ctx.conforms()) return;
        final Identificador identifier = assignStatement.getIdentifier();
        if(!ctx.identifierIsDefined(identifier.getName())){
            ctx.reportViolation("[INTERPRETER] Variable "+ identifier.getName() + " was not declared", assignStatement.getInputRange());
        }
        else {
            final Escalar scalar = ctx.obtainIdentifierValue(identifier.getName());
            final Tipo identifierType = scalar.getType();

            expressionEvaluator.reset();
            assignStatement.getExpression().accept(expressionEvaluator);
            expressionEvaluator.getResult().ifPresent(result -> {
                if(result.getType() != identifierType)
                    ctx.reportViolation("[INTERPRETER] Variable " + identifier.getName() + " was assigned to invalid type", assignStatement.getInputRange());
                else
                    ctx.registerNewVariable(identifier.getName(), result);
            });
        }
    }

    @Override
    public void visit(DeclaracaoAtribuicaoEstado declareAssignStatement) {
        if(!ctx.conforms()) return;
        final Identificador identifier = declareAssignStatement.getIdentifier();
        if(ctx.identifierIsDefined(identifier.getName()))
            ctx.reportViolation("[INTERPRETER] Variable was already been declared", declareAssignStatement.getInputRange());
        else{
            expressionEvaluator.reset();
            declareAssignStatement.getExpression().accept(expressionEvaluator);
            expressionEvaluator.getResult().ifPresent(value -> {
                if(declareAssignStatement.getType().getType() != value.getType())
                    ctx.reportViolation("[INTERPRETER] Expression does not conform to declared type", declareAssignStatement.getInputRange());
                else
                    ctx.registerNewVariable(identifier.getName(), value);
            });
        }
    }

    @Override
    public void visit(DeclaracaoEstado declareStatement) {
        if(!ctx.conforms()) return;
        final Identificador identifier = declareStatement.getIdentifier();
        if(ctx.identifierIsDefined(identifier.getName())){
            ctx.reportViolation("[INTERPRETER] Variable was already been declared", declareStatement.getInputRange());
        }
        else{
            ctx.registerNewVariable(identifier.getName(), new Escalar(declareStatement.getType().getType()));
        }
    }

    @Override
    public void visit(VisualizacaoEstado printStatement) {
        if(!ctx.conforms()) return;
        expressionEvaluator.reset();
        printStatement.getExpressionToPrint().accept(expressionEvaluator);
        expressionEvaluator.getResult().ifPresent(value -> {
            if(value.isDefined()){
                if(value.getType() == Tipo.STRING)
                    ctx.print(value.getStringValue());
                else
                    ctx.print(""+value.getNumberValue());
            }else
                ctx.reportViolation("[INTERPRETER] Undefined value",printStatement.getExpressionToPrint().getInputRange());
        });

    }

    @Override
    public void visit(ExpressaoAritmetica arithmeticExpression) {
        arithmeticExpression.accept(expressionEvaluator);
    }

    @Override
    public void visit(Identificador identifier) {
        identifier.accept(expressionEvaluator);
    }

    @Override
    public void visit(Literal literal) {
        literal.accept(expressionEvaluator);
    }
}
