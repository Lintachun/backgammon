import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Game {
    private Board board;

    public Game() {
        board = new Board();
        JFrame frame = new JFrame("Five in a Row");
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

class Board extends JPanel {
    private static final int CELL_SIZE = 50;
    private static final int BOARD_SIZE = 15;
    private int[][] board;
    private int currentPlayer;

    public Board() {
        setPreferredSize(new Dimension(CELL_SIZE * BOARD_SIZE, CELL_SIZE * BOARD_SIZE));
        setBackground(Color.ORANGE);
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = 1;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if (isValidMove(x, y)) {
                    board[x][y] = currentPlayer;
                    repaint();
                    if (isGameOver()) {
                        showGameOverDialog();
                    } else {
                        currentPlayer = currentPlayer == 1 ? 2 : 1;
                    }
                }
            }
        });
    }

    private boolean isValidMove(int x, int y) {
        return board[x][y] == 0;
    }

    private boolean isGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                int player = board[i][j];
                int count = 0;
                // 檢查橫向
                for (int k = i; k < BOARD_SIZE; k++) {
                    if (board[k][j] == player) {
                        count++;
                        if (count == 5) {
                            return true;
                        }
                    } else {
                        count = 0;
                        break;
                    }
                }
                count = 0;
                // 檢查縱向
                for (int k = j; k < BOARD_SIZE; k++) {
                    if (board[i][k] == player) {
                        count++;
                        if (count == 5) {
                            return true;
                        }
                    } else {
                        count = 0;
                        break;
                    }
                }
                count = 0;
                // 檢查正斜線
                for (int k = 0; i + k < BOARD_SIZE && j + k < BOARD_SIZE; k++) {
                    if (board[i + k][j + k] == player) {
                        count++;
                        if (count == 5) {
                            return true;
                        }
                    } else {
                        count = 0;
                        break;
                    }
                }
                count = 0;
                // 檢查反斜線
                for (int k = 0; i + k < BOARD_SIZE && j - k >= 0; k++) {
                    if (board[i + k][j - k] == player) {
                        count++;
                        if (count == 5) {
                            return true;
                        }
                    } else {
                        count = 0;
                        break;
                    }
                }
            }
        }
        return false;
    }

    private void showGameOverDialog() {
        String message = "Player " + (currentPlayer == 1 ? "1" : "2") + " wins!";
        JOptionPane.showMessageDialog(this, message);
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = 1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 繪製棋盤線條
        for (int i = 0; i < BOARD_SIZE; i++) {
            g.drawLine(0, i * CELL_SIZE, CELL_SIZE * (BOARD_SIZE - 1), i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, CELL_SIZE * (BOARD_SIZE - 1));
        }
        // 繪製棋子
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(i * CELL_SIZE - CELL_SIZE / 2, j * CELL_SIZE - CELL_SIZE / 2, CELL_SIZE, CELL_SIZE);
                } else if (board[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(i * CELL_SIZE - CELL_SIZE / 2, j * CELL_SIZE - CELL_SIZE / 2, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}

public class backgammon {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game());
    }
}