public class Computer {

    static Computer computer = new Computer();

    private Computer() {

    }

    public static Computer getInstance() {
        return computer;
    }

    // Dirty code :(
    private boolean winCheckFromCoordinate(Table table, int y, int x, int numberOfContinuousCellToWin) {
        boolean verticalWinCheck = true;
        boolean horizontalWinCheck = true;
        boolean rightCrossWinCheck = true;
        boolean leftCrossWinCheck = true;

        int verticalContinuousCellCount = 0;
        int horizontalContinuousCellCount = 0;
        int rightCrossContinuousCellCount = 0;
        int leftCrossContinuousCellCount = 0;

        char[][] arr = table.getTableArray();

        for (int k = 0; k < numberOfContinuousCellToWin; k++) {
            if (x + k < table.getSize() && arr[y][x + k] == arr[y][x])
                horizontalContinuousCellCount++;
            if (y + k < table.getSize() && arr[y + k][x] == arr[y][x])
                verticalContinuousCellCount++;
            if (y + k < table.getSize() && x + k < table.getSize() && arr[y + k][x + k] == arr[y][x])
                rightCrossContinuousCellCount++;
            if (y + k < table.getSize() && x - k >= 0 && arr[y + k][x - k] == arr[y][x])
                leftCrossContinuousCellCount++;

            if (x + k < table.getSize() && arr[y][x + k] != arr[y][x])
                horizontalWinCheck = false;
            if (y + k < table.getSize() && arr[y + k][x] != arr[y][x])
                verticalWinCheck = false;
            if (y + k < table.getSize() && x + k < table.getSize() && arr[y + k][x + k] != arr[y][x])
                rightCrossWinCheck = false;
            if (x - k >= 0 && y + k < table.getSize() && arr[y + k][x - k] != arr[y][x])
                leftCrossWinCheck = false;

            if (!horizontalWinCheck && !verticalWinCheck && !rightCrossWinCheck && !leftCrossWinCheck)
                return false;
        }

        horizontalWinCheck = horizontalContinuousCellCount == numberOfContinuousCellToWin;
        verticalWinCheck = verticalContinuousCellCount == numberOfContinuousCellToWin;
        leftCrossWinCheck = leftCrossContinuousCellCount == numberOfContinuousCellToWin;
        rightCrossWinCheck = rightCrossContinuousCellCount == numberOfContinuousCellToWin;

        return horizontalWinCheck || verticalWinCheck || rightCrossWinCheck || leftCrossWinCheck;
    }

    public int scoreOfState(Table table, int numberOfContinuousCellToWin) {
        int size = table.getSize();
        char[][] arr = table.getTableArray();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr[i][j] == 'x' && winCheckFromCoordinate(table, i, j,numberOfContinuousCellToWin))
                    return CaroGame.LOSE_SCORE;
                else if (arr[i][j] == 'o' && winCheckFromCoordinate(table, i, j,numberOfContinuousCellToWin))
                    return CaroGame.WIN_SCORE;
            }
        }
        return CaroGame.DRAW_SCORE;
    }

    private int multiplyFactorScoreForLeaf(Table table, int height) {
        int begin = table.getSize() * table.getSize() - table.filledCellCount();
        int end = table.getSize() * table.getSize() - table.filledCellCount() - height;
        int result = 1;

        for (int i = begin; i > end; i--) {
            result *= i;
        }
        return result;
    }

    public int scoreOfStateWithHeight(Table table, int height, boolean isTurnOfPlayer, int numberOfContinuousCellToWin) {
        if (height == 1)
            return scoreOfState(table,numberOfContinuousCellToWin);

        if (table.isFullFilled())
            return scoreOfState(table,numberOfContinuousCellToWin);

        if (scoreOfState(table,numberOfContinuousCellToWin) != CaroGame.DRAW_SCORE) {
            int score = scoreOfState(table,numberOfContinuousCellToWin);
            return score * multiplyFactorScoreForLeaf(table, height);
        }

        int score = 0;
        table.trimTable();

        for (int i = table.getTopMargin(); i <= table.getBotMargin(); i++) {
            for (int j = table.getLeftMargin(); j <= table.getRightMargin(); j++) {
                if (table.getTableArray()[i][j] == 'n') {
                    Table tempTable = new Table(table);
                    tempTable.setCell(i, j, isTurnOfPlayer);
                    score += scoreOfStateWithHeight(tempTable, height - 1, !isTurnOfPlayer,numberOfContinuousCellToWin);
                }
            }
        }

        return score;
    }
}
