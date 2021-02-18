public class Position {
    private int xCoordinate;
    private int yCoordinate;
    private int scoreOfPosition;

    Position(int yCoordinate, int xCoordinate, int scoreOfPosition) {
        this.yCoordinate = yCoordinate;
        this.xCoordinate = xCoordinate;
        this.scoreOfPosition = scoreOfPosition;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getScoreOfPosition() {
        return scoreOfPosition;
    }

    public void print() {
        System.out.println(yCoordinate + " " + xCoordinate + " " + scoreOfPosition);
    }
}
