import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class CaroGame {

    public static final int DRAW_SCORE = 0;
    public static final int WIN_SCORE = 1;
    public static final int LOSE_SCORE = -2;

    public static final int SIZE_OF_BOARD = 9;
    public static final int NUMBER_OF_CONTINUOUS_CELL_TO_WIN = 6;
    public static final int HARD_LEVEL = 5;  // 5 is min hard level that player can see AI working

    Table table;
    int heightOfMinimaxAlgorithm;
    int numberOfContinuousCellToWin;

    public void initGame(int sizeOfBoard, int numberOfContinuousCellToWin, int heightOfMinimaxAlgorithm) {
        table = new Table();
        table.createTable(sizeOfBoard);
        this.heightOfMinimaxAlgorithm = heightOfMinimaxAlgorithm;
        this.numberOfContinuousCellToWin = numberOfContinuousCellToWin;
    }

    public void computeNextStep(int height) {
        if (table.isFullFilled())
            return;

        int max_score = -100000;
        Vector<Position> maxScorePositionVector = new Vector<>();

        table.trimTable();

        for (int i = table.getTopMargin(); i <= table.getBotMargin(); i++) {
            for (int j = table.getLeftMargin(); j <= table.getRightMargin(); j++) {
                if (table.getTableArray()[i][j] == '_') {
                    Table tempTable = new Table(table);
                    tempTable.setCell(i, j, false);
                    int score = Computer.getInstance().scoreOfStateWithHeight(tempTable, height - 1,
                            true, numberOfContinuousCellToWin);
                    if (score > max_score) {
                        maxScorePositionVector = new Vector<>();
                        max_score = score;
                        maxScorePositionVector.add(new Position(i, j, score));
                    } else if (score == max_score) {
                        maxScorePositionVector.add(new Position(i, j, score));
                    }
                }
            }
        }

        Random random = new Random();
        int randomIndex = random.nextInt(maxScorePositionVector.size());
        if (randomIndex == maxScorePositionVector.size())
            randomIndex--;
        System.out.println(randomIndex + " " +maxScorePositionVector.size());
        for (int i = 0; i < maxScorePositionVector.size(); i++) {
            maxScorePositionVector.get(i).print();
        }
        int i_maxIndex = maxScorePositionVector.get(randomIndex).getyCoordinate();
        int j_maxIndex = maxScorePositionVector.get(randomIndex).getxCoordinate();
        table.setCell(i_maxIndex, j_maxIndex, false);
        System.out.println("Computer: " + (i_maxIndex + 1) + " " + (j_maxIndex + 1));
    }

    public void getInput() {
        System.out.print("Enter your pace: ");
        int x, y;
        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();
        y = scanner.nextInt();
        while (x < 0 || x > table.getSize() || y < 0 || y > table.getSize() || table.getTableArray()[x - 1][y - 1] == 'x' || table.getTableArray()[x - 1][y - 1] == 'o') {
            System.out.print("Error! Reenter your pace: ");
            x = scanner.nextInt();
            y = scanner.nextInt();
        }
        table.setCell(x - 1, y - 1, true);
    }

    public void resultAnnounce() {
        if (Computer.getInstance().scoreOfState(table, numberOfContinuousCellToWin) == WIN_SCORE)
            System.out.println("You lose!");
        else if (Computer.getInstance().scoreOfState(table, numberOfContinuousCellToWin) == LOSE_SCORE)
            System.out.println("You won!");
        else
            System.out.println("Draw!");
    }

    public void gameProcess() {
        while (!table.isFullFilled() && Computer.getInstance().scoreOfState(table, numberOfContinuousCellToWin) == DRAW_SCORE) {
            getInput();
            if (Computer.getInstance().scoreOfState(table, numberOfContinuousCellToWin) == LOSE_SCORE)
                break;
            computeNextStep(heightOfMinimaxAlgorithm);
            table.printTable();
        }

        resultAnnounce();
    }

    public static void main(String[] args) {
        CaroGame caroGame = new CaroGame();
        caroGame.initGame(CaroGame.SIZE_OF_BOARD, CaroGame.NUMBER_OF_CONTINUOUS_CELL_TO_WIN, CaroGame.HARD_LEVEL);
        caroGame.gameProcess();
    }
}