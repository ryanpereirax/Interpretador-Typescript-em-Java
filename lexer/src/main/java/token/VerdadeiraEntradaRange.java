package token;

public class VerdadeiraEntradaRange implements EntradaRange {

    private final Integer startLine;
    private final Integer startColumn;
    private final Integer endLine;
    private final Integer endColumn;

    public VerdadeiraEntradaRange(Integer startLine, Integer startColumn, Integer endLine, Integer endColumn) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public VerdadeiraEntradaRange() {
        this.startLine = 1;
        this.startColumn = 1;
        this.endLine = 1;
        this.endColumn = 0;
    }

    public VerdadeiraEntradaRange startFromNextPosition() {
        return new VerdadeiraEntradaRange(endLine,endColumn + 1,endLine,endColumn);
    }

    public VerdadeiraEntradaRange moveColumn(int amt) {
        return new VerdadeiraEntradaRange(startLine,startColumn,endLine,endColumn + amt);
    }

    public VerdadeiraEntradaRange moveLine() {
        return new VerdadeiraEntradaRange(startLine,startColumn,endLine + 1,0);
    }

    @Override
    public Integer getStartLine() {
        return startLine;
    }

    @Override
    public Integer getStartColumn() {
        return startColumn;
    }

    @Override
    public Integer getEndLine() {
        return endLine;
    }

    @Override
    public Integer getEndColumn() {
        return endColumn;
    }
}
