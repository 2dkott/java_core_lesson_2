import java.util.*;

public class GameMain {

    static Cell[][] gameField;
    static Scanner scanner = new Scanner(System.in);
    static CellValue mySing = CellValue.ZERO;
    static CellValue playerSing = CellValue.CROSS;

    public static void main(String[] args){
        init(3);
        print(gameField);
        start();
    }

    private static void init(int size){
        gameField = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                //gameField[i][j]=new Cell(String.format("%s%s", i+1, j+1), i, j);
                gameField[i][j]=new Cell(CellValue.EMPTY, j, i);
            }
        }
    }

    private static void print(Cell[][] gameField){
        System.out.print("+");
        for (int i = 0; i < gameField.length * 2 + 1; i ++){
            System.out.print((i%2==0) ? "_" : i/2+1);
        }
        System.out.println();
        for (int i = 0; i < gameField.length; i++){
            System.out.print(i + 1 + "|");
            for (int j = 0; j < gameField.length; j++){
                System.out.print(gameField[i][j].getStringValue() + "|");
            }
            System.out.println();
        }
    }

    private static void getHumanTurn(){
        int x, y;
        while (true) {
            try {
                System.out.print("Введите координату по горизонтали:\n");
                x = checkInput();
                System.out.print("Введите координату по вертикали:\n");
                y = checkInput();
                if(isEmpty(x-1, y-1)) {
                    putSign(x-1, y-1, playerSing);
                    break;
                }
            } catch (Exception e){
                System.out.println(e.getMessage()+"\n");
            }
        }
    }

    private static int checkInput() throws WrongValueException {
        try {

            int value = Integer.parseInt(scanner.next());
            if (value > gameField.length || value < 1) {
                throw new WrongValueException();
            }
            return value;
        } catch (NumberFormatException e) {
            throw new WrongValueException();
        }
    }

    private static void play() {
        Cell cellToSign = null;
        List<List<Cell>> winRows = new ArrayList<>();
        List<List<Cell>> protectRows = new ArrayList<>();
        List<Cell> availableCells = new ArrayList<>();

        for (int i = 0; i < gameField.length; i++){
            for (int j = 0; j < gameField.length; j++){
                if(gameField[i][j].getValue().equals(CellValue.EMPTY)){
                    winRows.addAll(getCleanRows(j, i, gameField.length-1, mySing, playerSing));
                    protectRows.addAll(getCleanRows(j, i, gameField.length-1, playerSing, mySing));
                    availableCells.add(gameField[i][j]);
                }
            }
        }
        if(!winRows.isEmpty()) {
            cellToSign = getEmptyCellFromRow(winRows);
        } else if(!protectRows.isEmpty()) {
            cellToSign = getEmptyCellFromRow(protectRows);
        } else if(!availableCells.isEmpty()) {
            cellToSign = availableCells.stream().findAny().get();
        } else return;

        putSign(cellToSign.getX(), cellToSign.getY(), mySing);
    }

    private static void putSign(int x, int y, CellValue sign){
        gameField[y][x] = new Cell(sign, x, y);
    }
    private static List<List<Cell>> getAllRowsForCell(int x, int y, int fieldSize) {
        List<List<Cell>> rows = new ArrayList<>();
        rows.add(getHorizontalRow(y));
        rows.add(getVerticalRow(x));
        rows.addAll(getCross(x, y, fieldSize));
        return rows;
    }

    private static boolean checkWin(CellValue winSing) {
        List<List<Cell>> rows = new ArrayList<>();
        for (int i = 0; i < gameField.length; i++){
            for (int j = 0; j < gameField.length; j++){
                    rows.addAll(getWinRows(j, i, winSing));
            }
        }
        return !rows.isEmpty();
    }

    private static boolean checkDraw() {
        List<Cell> availableCells = new ArrayList<>();
        for (int i = 0; i < gameField.length; i++){
            for (int j = 0; j < gameField.length; j++){
                if(gameField[i][j].getValue().equals(CellValue.EMPTY)){
                    availableCells.add(gameField[i][j]);
                }
            }
        }
        return availableCells.isEmpty();
    }

    private static Cell getEmptyCellFromRow(List<List<Cell>> rows) {
        return rows.stream().findAny().get().stream().filter(Cell::isEmpty).findFirst().get();
    }
    private static List<List<Cell>> getCleanRows(int x, int y, int count, CellValue mySign, CellValue enemySign) {
        List<List<Cell>> rows = getAllRowsForCell(x, y, gameField.length);
        return rows.stream()
                .filter(row -> row.stream().filter(cell -> cell.getValue().equals(enemySign)).findAny().isEmpty())
                .filter(row -> row.stream().filter(cell -> cell.getValue().equals(mySign)).count() == count).toList();
    }

    private static List<List<Cell>> getWinRows(int x, int y, CellValue winSing) {
        List<List<Cell>> rows = getAllRowsForCell(x, y, gameField.length);
        return rows.stream()
                .filter(row -> row.stream().filter(cell -> cell.getValue().equals(winSing)).count() == gameField.length).toList();
    }

    private static boolean isEmpty(int x, int y){
        Cell cell = gameField[y][x];
        return cell.getValue().equals(CellValue.EMPTY);
    }

    private static List<Cell> getHorizontalRow(int y){
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < gameField.length; i++){
            cellList.add(gameField[y][i]);
        }
        return cellList;
    }

    private static List<Cell> getVerticalRow(int x){
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < gameField.length; i++){
            cellList.add(gameField[i][x]);
        }
        return cellList;
    }

    private static List<List<Cell>> getCross(int x, int y, int gameFieldSize){
        List<Cell> cellList1 = new ArrayList<>();
        List<Cell> cellList2 = new ArrayList<>();
        if(x==y){
            for (int i = 0; i < gameFieldSize; i++){
                cellList1.add(gameField[i][i]);
            }
        }
        if (x+y==gameFieldSize-1){
            int i = 0;
            for (int j = gameFieldSize-1; j >=0; j--){
                cellList2.add(gameField[j][i]);
                i++;
            }
        }
        return Arrays.asList(cellList1, cellList2);
    }

    private static void start(){
        while (true){

            getHumanTurn();
            print(gameField);
            if(checkWin(playerSing)) {
                System.out.println("\nВы выиграли!");
                break;
            }

            play();
            print(gameField);
            if(checkWin(mySing)) {
                System.out.println("'\nЯ выиграл!");
                break;
            }

            if(checkDraw()){
                System.out.println("\nУх ты, ничья!");
                break;
            }
        }
    }
}
