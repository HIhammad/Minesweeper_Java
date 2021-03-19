package mine;
/// Authors: Ahmed Eshra, Hussain Hammad
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class Board extends JPanel {
    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 40;
    private int N_MINES;
    public static final int N_ROWS = 15;
    public static final int N_COLS = 25;
    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 10;
    private final int BOARD_HEIGHT = (N_ROWS + 1) * CELL_SIZE + 10;
    public Cell Cells[][] = new Cell[N_ROWS][N_COLS];
    Random rand = new Random();
    private boolean inGame;
    private int minesLeft;
    private Image[] img;
    private final JLabel status;
    private boolean firstClick;

    public Board(JLabel s) {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                Cells[i][j] = new Cell();
            }
        }
        this.status = s;
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        img = new Image[NUM_IMAGES];
        for (int i = 0; i < NUM_IMAGES; i++) {
            String path = "resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_DEFAULT);
        }
        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void mineChecker(int randX, int randY) {
        if (randY > 0 && randX > 0) {
            Cells[randY - 1][randX - 1].mines_increment();
        }
        if (randY > 0 && randX < N_COLS - 1) {
            Cells[randY - 1][randX + 1].mines_increment();
        }
        if (randY < N_ROWS - 1 && randX > 0) {
            Cells[randY + 1][randX - 1].mines_increment();
        }
        if (randY < N_ROWS - 1 && randX < N_COLS - 1) {
            Cells[randY + 1][randX + 1].mines_increment();
        }
        if (randY > 0) {
            Cells[randY - 1][randX].mines_increment();
        }
        if (randX > 0) {
            Cells[randY][randX - 1].mines_increment();
        }
        if (randX < N_COLS - 1) {
            Cells[randY][randX + 1].mines_increment();
        }
        if (randY < N_ROWS - 1) {
            Cells[randY + 1][randX].mines_increment();
        }
    }

    private void newGame() {
        boolean valid = false;
        while (!valid) {
            String s = JOptionPane.showInputDialog("Input Number of mines, max is " + Integer.toString(N_ROWS * N_COLS - 9) + ", Recommended is " + Integer.toString(((N_ROWS * N_COLS - 9)) / 5));
            if (s == null) {
                N_MINES = (N_ROWS * N_COLS - 9) / 5;
                valid = true;
            } else {
                try {
                    N_MINES = Integer.parseInt(s);
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
            }
        }
        if (N_MINES > N_ROWS * N_COLS - 9) {
            N_MINES = N_ROWS * N_COLS - 9;
        }
        inGame = true;
        firstClick = true;
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                Cells[i][j] = new Cell();
            }
        }
        minesLeft = N_MINES;
    }

    private void find_empty_cells(int x, int y) {
        if (Cells[y][x].getMines_around() == 0) {
            if (y > 0 && x > 0 && !Cells[y - 1][x - 1].isClicked()) {
                Cells[y - 1][x - 1].click();
                find_empty_cells(x - 1, y - 1);
            }
            if (y > 0 && x < N_COLS - 1 && !Cells[y - 1][x + 1].isClicked()) {
                Cells[y - 1][x + 1].click();
                find_empty_cells(x + 1, y - 1);

            }
            if (y < N_ROWS - 1 && x > 0 && !Cells[y + 1][x - 1].isClicked()) {
                Cells[y + 1][x - 1].click();
                find_empty_cells(x - 1, y + 1);

            }
            if (y < N_ROWS - 1 && x < N_COLS - 1 && !Cells[y + 1][x + 1].isClicked()) {
                Cells[y + 1][x + 1].click();
                find_empty_cells(x + 1, y + 1);

            }
            if (y > 0 && !Cells[y - 1][x].isClicked()) {
                Cells[y - 1][x].click();
                find_empty_cells(x, y - 1);
            }
            if (x > 0 && !Cells[y][x - 1].isClicked()) {
                Cells[y][x - 1].click();
                find_empty_cells(x - 1, y);

            }
            if (x < N_COLS - 1 && !Cells[y][x + 1].isClicked()) {
                Cells[y][x + 1].click();
                find_empty_cells(x + 1, y);

            }
            if (y < N_ROWS - 1 && !Cells[y + 1][x].isClicked()) {
                Cells[y + 1][x].click();
                find_empty_cells(x, y + 1);

            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (firstClick) {
            status.setText("Click to place mines.");
        } else if (inGame) {
            status.setText("Mines Left: " + Integer.toString(minesLeft));
        }
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                if (inGame) {
                    if (Cells[i][j].isClicked()) {
                        g.drawImage(img[Cells[i][j].getMines_around()], (j * CELL_SIZE), (i * CELL_SIZE), this);
                    } else if (Cells[i][j].isFlaged()) {
                        g.drawImage(img[11], (j * CELL_SIZE), (i * CELL_SIZE), this);
                    } else {
                        g.drawImage(img[10], (j * CELL_SIZE), (i * CELL_SIZE), this);
                    }
                } else {
                    if (Cells[i][j].isMine()) {
                        g.drawImage(img[9], (j * CELL_SIZE), (i * CELL_SIZE), this);
                    } else {
                        g.drawImage(img[Cells[i][j].getMines_around()], (j * CELL_SIZE), (i * CELL_SIZE), this);
                    }
                }
            }
        }
    }

    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX() / CELL_SIZE;
            int y = e.getY() / CELL_SIZE;
            if ((x < N_COLS) && (y < N_ROWS)) {
                if (!inGame) {
                    newGame();
                    repaint();
                } else if (firstClick) {
                    firstClick = false;
                    boolean done = false;
                    int mines_left_to_be_placed = N_MINES;
                    while (!done) {
                        int randX = rand.nextInt(N_COLS);
                        int randY = rand.nextInt(N_ROWS);
                        if (!((randX > x - 2) && (randY > y - 2) && (randX < x + 2) && (randY < y + 2)) && !Cells[randY][randX].isMine()) {
                            Cells[randY][randX].setMine();
                            mineChecker(randX, randY);
                            mines_left_to_be_placed--;
                        }
                        if (mines_left_to_be_placed == 0) {
                            done = true;
                        }
                    }
                    Cells[y][x].click();
                    find_empty_cells(x, y);
                } else {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Cells[y][x].flagIt();
                    } else {
                        if (Cells[y][x].isMine() && !Cells[y][x].isFlaged()) {
                            status.setText("   ISIS WON!, click again to start over");
                            inGame = false;
                        } else {
                            Cells[y][x].click();
                            find_empty_cells(x, y);
                        }
                    }
                    boolean gameWonFlags = true;
                    for (int i = 0; i < N_ROWS; i++) {
                        for (int j = 0; j < N_COLS; j++) {
                            if (Cells[i][j].isMine() && !Cells[i][j].isFlaged()) {
                                gameWonFlags = false;
                            }
                        }
                    }
                    boolean gameWonClick = true;
                    for (int i = 0; i < N_ROWS; i++) {
                        for (int j = 0; j < N_COLS; j++) {
                            if (!Cells[i][j].isMine() && !Cells[i][j].isClicked()) {
                                gameWonClick = false;
                            }
                        }
                    }
                    if (gameWonClick || gameWonFlags) {
                        status.setText("You Won!");
                        inGame = false;
                    }
                }
            }
            repaint();
        }
    }
}
