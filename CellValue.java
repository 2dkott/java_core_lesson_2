public enum CellValue {
    EMPTY("."),
    CROSS("x"),
    ZERO("0");

    private final String value;

    private CellValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
