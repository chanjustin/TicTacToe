/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

/**
 *
 * @author test
 */
public class Board implements Comparable {

    private String[][] board;
    private int score;
    private String stringRepresentation;

    public Board()
    {
        board = new String[3][3];
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                board[i][j] = "";
            }
        }
        stringRepresentation = toString();
    }

    public Board(String[][] board)
    {
        this.board = board;
        stringRepresentation = toString();
    }

    public Board(String[] board)
    {
        int index = 0;
        for(int i = 0; i < this.board.length; i++)
        {
            for(int j = 0; j < this.board[i].length; j++)
            {
                this.board[i][j] = board[index];
                index++;
            }
        }
        stringRepresentation = toString();
    }

    public String[][] getBoard()
    {
        return board;
    }

    public String[] getSingleBoard()
    {
        String[] toBeReturned;
        int length = 0;
        int index = 0;
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                length++;
            }
        }
        toBeReturned = new String[length];

        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                toBeReturned[index] = board[i][j];
                index++;
            }
        }
        return toBeReturned;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    public String toString()
    {
        String string = "Board: \n";

        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                if(board[i][j].equals(""))
                {
                    string += ".";
                }
                else
                {
                    string += board[i][j];
                }
            }
            string += "\n";
        }
        string += "\n";
        return string;
    }

    public int compareTo(Object b)
    {
        Board board = (Board)b;
        if(board.getScore() < score)
        {
            return -1;
        }
        else if(board.getScore() == score)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
