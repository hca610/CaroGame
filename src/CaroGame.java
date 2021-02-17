import java.util.Scanner;

public class CaroGame {

    public static final int DRAW_SCORE = 0;
    public static final int WIN_SCORE = 1;
    public static final int LOSE_SCORE = -1;

    public static final int SIZE_OF_BOARD = 5;
    public static final int NUMBER_OF_CONTINUOUS_CELL_TO_WIN = 4;
    public static final int HARD_LEVEL = 6;

    Table table;

    public void initGame(int sizeOfBoard, int numberOfContinuousCellToWin) {
        table = new Table();
        table.createTable(sizeOfBoard);
    }

    public void computeNextStep(int height) {
        if (table.isFullFilled())
            return;

        int max_score = -100000;
        int maxi_index = 0;
        int maxj_index = 0;

        table.trimTable();

        for (int i = table.getTopMargin(); i <= table.getBotMargin(); i++) {
            for (int j = table.getLeftMargin(); j <= table.getRightMargin(); j++) {
                if (table.getTableArray()[i][j] == 'n') {
                    Table tempTable = new Table(table);
                    tempTable.setCell(i, j, false);
                    int score = Computer.getInstance().scoreOfStateWithHeight(tempTable, height - 1, true);
                    if (score > max_score) {
                        maxi_index = i;
                        maxj_index = j;
                        max_score = score;
                    }
                }
            }
        }
        table.setCell(maxi_index, maxj_index, false);
        System.out.println("Computer: " + (maxi_index + 1) + " " + (maxj_index + 1));
    }

    public void getInput() {
        System.out.print("Enter your pace: ");
        int x, y;
        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();
        y = scanner.nextInt();
        while (x < 0 || x > table.getSize() || y < 0 || y > table.getSize() || table.getTableArray()[x - 1][y - 1] != 'n') {
            System.out.print("Error! Reenter your pace: ");
            x = scanner.nextInt();
            y = scanner.nextInt();
        }
        table.setCell(x - 1, y - 1, true);
    }

    public void resultAnnounce() {
        if (Computer.getInstance().scoreOfState(table) == WIN_SCORE)
            System.out.println("You lose!");
        else if (Computer.getInstance().scoreOfState(table) == LOSE_SCORE)
            System.out.println("You won!");
        else
            System.out.println("Draw!");
    }

    public void gameProcess() {
        Computer computer = Computer.getInstance();

        while (!table.isFullFilled() && Computer.getInstance().scoreOfState(table) == DRAW_SCORE) {
            getInput();
            if (Computer.getInstance().scoreOfState(table) == LOSE_SCORE)
                break;
            computeNextStep(HARD_LEVEL);
            table.printTable();
        }

        resultAnnounce();
    }

    public static void main(String[] args) {
        CaroGame caroGame = new CaroGame();
        caroGame.initGame(CaroGame.SIZE_OF_BOARD, CaroGame.NUMBER_OF_CONTINUOUS_CELL_TO_WIN);
        caroGame.gameProcess();
    }
}