package inscaparrella.model;

import inscaparrella.utils.CellType;

public class CellBuilder {
    private int row;
    private int col;
    private CellType cType;
    private boolean open;

    public CellBuilder setRow(int row) {
        this.row = row;
        return this;
    }

    public CellBuilder setCol(int col) {
        this.col = col;
        return this;
    }

    public CellBuilder setcType(CellType cType) {
        this.cType = cType;
        return this;
    }

    public CellBuilder setOpen(boolean open) {
        this.open = open;
        return this;
    }


}