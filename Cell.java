public class Cell {
    private  CellValue value;
    private int x;
    private int y;

    public Cell(CellValue value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty(){
        return value.equals(CellValue.EMPTY);
    }

    public String getValue() {
        return value.getValue();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
