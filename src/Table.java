import java.util.Arrays;

public class Table {
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
                if (tableArray[i][j] != 'n')
                    count++;
            }
        }
        return count;
    }

    public boolean isFullFilled() {
        return filledCellCount() == size * size;
    }

    public void printTable() {
        for (char[] chars : tableArray) {
            for (int j = 0; j < tableArray[0].length; j++) {
                if (chars[j] == 'n')
                    System.out.print("_ ");
                else
                    System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }

    public void setCell(int i, int j, boolean isTurnOfPlayer) {
        tableArray[i][j] = isTurnOfPlayer ? 'x' : 'o';
    }

    //Set margin for table
    public void trimTable() {
        leftMargin = size;
        rightMargin = 0;
        topMargin = size;
        botMargin = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (tableArray[i][j] != 'n') {
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
                if (tableArray[i][j] != 'n') {
                    if (j > rightMargin) {
                        rightMargin = j;
                    }
                    if (i > botMargin) {
                        botMargin = i;
                    }
                }
            }
        }
        topMargin = topMargin > 0 ? topMargin - 1 : topMargin;
        rightMargin = rightMargin < size - 1 ? rightMargin + 1 : rightMargin;
        botMargin = botMargin < size - 1 ? botMargin + 1 : botMargin;
        leftMargin = leftMargin > 0 ? leftMargin - 1 : leftMargin;
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

    public void setSize(int size) {
        this.size = size;
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

    public void setTableArray(char[][] tableArray) {
        this.tableArray = tableArray;
        this.trimTable();
    }
}
