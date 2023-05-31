import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMain {

    static Cell[][] gameField;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        init(4);
        print(gameField);
        start();
        print(gameField);
    }

    private static void init(int size){
        gameField = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                gameField[i][j]=new Cell(CellValue.EMPTY, i, j);
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
                System.out.print(gameField[i][j].getValue() + "|");
            }
            System.out.println();
        }
    }

    private static void getHumanTurn(){
        int x, y;
        while (true) {
            System.out.println("Введите координату по горизонтали:\n");
            x = scanner.nextInt();
            System.out.println("Введите координату по вертикали:\n");
            y = scanner.nextInt();
            if(isEmpty(x-1, y-1)) break;
        }
        gameField[x-1][y-1] = new Cell(CellValue.CROSS, x-1, y-1);
    }

    private static boolean isEmpty(int x, int y){
        Cell cell = gameField[x][y];
        return cell.getValue().equals(CellValue.EMPTY.getValue());
    }

    private static void start(){
            getHumanTurn();
    }
}
