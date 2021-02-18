import java.util.Arrays;

public class Table {
    final int MARGIN_WIDTH = 1;

    private int leftMargin = 0;
    private int rightMargin = 0;
    private int topMargin = 0;
    private int botMargin = 0;

    private char[][] tableArray;
    private int size;

    public void createTable(int size) {
        tableArray = new char[size][size];
        for (char[] chars : tableArray) {
            Arrays.fill(chars, 'n');
        }
        this.size = size;
    }

    public void refreshTable() {
        for (char[] chars : tableArray) {
            Arrays.fill(chars, 'n');
        }
    }

    public int filledCellCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < tableArray[0].length; j++) {
                if (tableArray[i][j] == 'x' || tableArray[i][j] == 'o')
                    count++;
            }
        }
        return count;
    }

    public boolean isFullFilled() {
        return filledCellCount() == size * size;
    }

    public void printTable() {
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        int count = 1;
        for (int i = 0; i < tableArray.length; i++) {
            System.out.print(count + " ");
            for (int j = 0; j < tableArray[0].length; j++) {
                if (tableArray[i][j] == 'n')
                    System.out.print("_ ");
                else
                    System.out.print(tableArray[i][j] + " ");
            }
            System.out.println(count++);
        }
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
    }

    public void setCell(int i, int j, boolean isTurnOfPlayer) {
        tableArray[i][j] = isTurnOfPlayer ? 'x' : 'o';
        trimTable();
    }

    //Set margin for table
    public void trimTable() {
        leftMargin = size;
        rightMargin = 0;
        topMargin = size;
        botMargin = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (tableArray[i][j] == 'x' || tableArray[i][j] == 'o') {
                    if (j < leftMargin) {
                        leftMargin = j;
                    }
                    if (i < topMargin) {
                        topMargin = i;
                    }
                }
        }

        for (int i = size - 1; i >= 0; i--) {
            for (int j = size - 1; j >= 0; j--) {
                if (tableArray[i][j] == 'x' || tableArray[i][j] == 'o') {
                    if (j > rightMargin) {
                        rightMargin = j;
                    }
                    if (i > botMargin) {
                        botMargin = i;
                    }
                }
            }
        }
        topMargin = topMargin - MARGIN_WIDTH >= 0 ? topMargin - MARGIN_WIDTH : topMargin;
        rightMargin = rightMargin < size - MARGIN_WIDTH ? rightMargin + MARGIN_WIDTH : rightMargin;
        botMargin = botMargin < size - MARGIN_WIDTH ? botMargin + MARGIN_WIDTH : botMargin;
        leftMargin = leftMargin - MARGIN_WIDTH >= 0 ? leftMargin - MARGIN_WIDTH : leftMargin;
        setAvailableMoveCellAfterTrim();
    }

    public void setAvailableMoveCellAfterTrim() {
        for (int i = topMargin + 1; i < botMargin; i++) {
            for (int j = leftMargin + 1; j < rightMargin; j++) {
                if (tableArray[i][j] == 'x' || tableArray[i][j] == 'o') {
                    for (int k = i-1; k <= i+1 ; k++) {
                        for (int l = j-1; l <= j+1 ; l++) {
                            if (tableArray[k][l] == 'n')
                                tableArray[k][l] = '_';
                        }
                    }
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public int getBotMargin() {
        return botMargin;
    }

    public char[][] getTableArray() {
        return tableArray;
    }

    public Table() {
    }

    public Table(Table table) {
        char[][] tempArray = new char[table.getSize()][table.getSize()];
        for (int i = 0; i < table.getSize(); i++) {
            for (int j = 0; j < table.getSize(); j++) {
                tempArray[i][j] = table.getTableArray()[i][j];
            }
        }
        this.tableArray = tempArray;

        this.size = table.getSize();
        this.leftMargin = table.getLeftMargin();
        this.rightMargin = table.getRightMargin();
        this.topMargin = table.getTopMargin();
        this.botMargin = table.getBotMargin();
        this.trimTable();
    }
}
