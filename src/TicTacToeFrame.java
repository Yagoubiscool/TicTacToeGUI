import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private final int ROW = 3;
    private final int COL = 3;
    private TicTacToeTile[][] board = new TicTacToeTile[ROW][COL];
    private String currentPlayer = "X";
    private int moveCnt = 0;

    private final int MOVES_FOR_WIN = 5;
    private final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createBoard();
        createControls();
    }

    private void createBoard() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROW, COL));
        TileListener listener = new TileListener();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = new TicTacToeTile(row, col);
                board[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                board[row][col].setText(" ");
                board[row][col].setFocusPainted(false);
                board[row][col].addActionListener(listener);
                boardPanel.add(board[row][col]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    private void createControls() {
        JPanel controlPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        quitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        controlPanel.add(quitButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private class TileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile clickedTile = (TicTacToeTile) e.getSource();

            if (!clickedTile.getText().equals(" ")) {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Illegal move! Square is already taken.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            clickedTile.setText(currentPlayer);
            moveCnt++;

            if (moveCnt >= MOVES_FOR_WIN && isWin(currentPlayer)) {
                handleGameOver("Player " + currentPlayer + " wins!");
                return;
            }

            if (moveCnt >= MOVES_FOR_TIE && isTie()) {
                handleGameOver("It's a tie!");
                return;
            }

            // Swap player
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

    private boolean isWin(String player) {
        return isColWin(player) || isRowWin(player) || isDiagnalWin(player);
    }

    private boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagnalWin(String player) {
        if (board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player)) {
            return true;
        }
        if (board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player)) {
            return true;
        }
        return false;
    }

    private boolean isTie() {
        boolean xFlag, oFlag;

        // Check rows
        for (int row = 0; row < ROW; row++) {
            xFlag = board[row][0].getText().equals("X") || board[row][1].getText().equals("X") || board[row][2].getText().equals("X");
            oFlag = board[row][0].getText().equals("O") || board[row][1].getText().equals("O") || board[row][2].getText().equals("O");
            if (!(xFlag && oFlag)) return false;
        }

        // Check columns
        for (int col = 0; col < COL; col++) {
            xFlag = board[0][col].getText().equals("X") || board[1][col].getText().equals("X") || board[2][col].getText().equals("X");
            oFlag = board[0][col].getText().equals("O") || board[1][col].getText().equals("O") || board[2][col].getText().equals("O");
            if (!(xFlag && oFlag)) return false;
        }

        // Check diagonals
        xFlag = board[0][0].getText().equals("X") || board[1][1].getText().equals("X") || board[2][2].getText().equals("X");
        oFlag = board[0][0].getText().equals("O") || board[1][1].getText().equals("O") || board[2][2].getText().equals("O");
        if (!(xFlag && oFlag)) return false;

        xFlag = board[0][2].getText().equals("X") || board[1][1].getText().equals("X") || board[2][0].getText().equals("X");
        oFlag = board[0][2].getText().equals("O") || board[1][1].getText().equals("O") || board[2][0].getText().equals("O");
        if (!(xFlag && oFlag)) return false;

        // If every win vector has both X and O, no one can win.
        return true;
    }

    private void handleGameOver(String message) {
        int response = JOptionPane.showConfirmDialog(this, message + "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private void resetBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col].setText(" ");
            }
        }
        currentPlayer = "X";
        moveCnt = 0;
    }
}
