package tictactoe;
import java.util.Random;

/**
 *
 * @author test
 */
public class Engine
{
    private char playersIcon;
    private char computersIcon;
    private boolean isPlayersTurn;
    private char currentIcon;
    private int difficulty;
    private Random random;
    
    public Engine()
    {
        random = new Random();
        isPlayersTurn = true;
        if(isPlayersTurn)
        {
            currentIcon = getPlayersIcon();
        }
        else if(!isPlayersTurn)
        {
            currentIcon = getComputersIcon();
        }
    }
    
    public Board randomMove(Board board)
    {
        Board[] possibleMoves = getPossibleMoves(board);
        return possibleMoves[random.nextInt(possibleMoves.length)];
    }

    public Board bestMove(Board board)
    {
        if(1+random.nextInt(100) >= difficulty)
        {
            return randomMove(board);
        }
        else
        {
            Board optimalBoard = new Board();
            Board[] possibleMoves = getPossibleMoves(board);
            for(int i = 0; i < possibleMoves.length; i++)
            {
                int score = score(possibleMoves[i]);
                possibleMoves[i].setScore(score);
                //simple pruning takes place here
                //optimal board is returned immediately when found
                if(isPlayersTurn && score == -1)
                {
                    return possibleMoves[i];
                }
                else if(!isPlayersTurn && score == 1)
                {
                    return possibleMoves[i];
                }
            }

            if(!isPlayersTurn)
            {
                int score = -2;
                for(Board b : possibleMoves)
                {
                    if(b.getScore() > score)
                    {
                        score = b.getScore();
                        optimalBoard = b;
                    }
                }
            }
            else if(isPlayersTurn)
            {
                int score = 2;
                for(Board b : possibleMoves)
                {
                    if(b.getScore() < score)
                    {
                        score = b.getScore();
                        optimalBoard = b;
                    }
                }
            }
            return optimalBoard;
        }
    }

    //via minimax
    public int score(Board board)
    {
        //terminal node, user has won
        if(isPlayersTurn && hasUserWon(board))
        {
            return -1;
        }
        //terminal node, computer has won
        if(!isPlayersTurn && hasComputerWon(board))
        {
            return 1;
        }
        //terminal node, there is a tie
        if((!hasComputerWon(board) || !hasUserWon(board)) && getNumberOfEmptySquares(board) == 0)
        {
            return 0;
        }
        else
        {
            changeTurn();
            Board[] possibleMoves = getPossibleMoves(board);
            for(int i = 0; i < possibleMoves.length; i++)
            {
                int score = score(possibleMoves[i]);
                possibleMoves[i].setScore(score);
            }
            if(isPlayersTurn)
            {
                int score = 2;
                for(Board b : possibleMoves)
                {
                    if(b.getScore() < score)
                    {
                        score = b.getScore();
                    }
                }
                board.setScore(score);
                changeTurn();
                return(score);
            }
            else
            {
                int score = -2;
                for(Board b : possibleMoves)
                {
                    if(b.getScore() > score)
                    {
                        score = b.getScore();
                    }
                }
                board.setScore(score);
                changeTurn();
                return(score);
            }
        }
    }

    public Board[] getPossibleMoves(Board board)
    {
        Board[] possibleMoves = new Board[getNumberOfEmptySquares(board)];
        int count = 0;

        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                if(board.getBoard()[i][j].equals(""))
                {
                    possibleMoves[count] = new Board();
                    copyArray(board,possibleMoves[count]);
                    possibleMoves[count].getBoard()[i][j] = getCurrentIcon() + "";
                    count++;
                }
            }
        }
        return possibleMoves;
    }

    public void copyArray(Board from, Board to)
    {
        for(int i = 0; i < from.getBoard().length; i++)
        {
            for(int j = 0; j < from.getBoard()[i].length; j++)
            {
                to.getBoard()[i][j] = new String(from.getBoard()[i][j]);
            }
        }
    }

    public boolean hasUserWon(Board board)
    {   
        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                //checks for horizontal wins
                if(board.getBoard()[i][j].equals(getPlayersIcon() + "") &&
                   board.getBoard()[i][j+1].equals(getPlayersIcon() + "") &&
                   board.getBoard()[i][j+2].equals(getPlayersIcon() + ""))
                {
                    return true;
                }
                break;
            }
        }
        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                //checks for vertical wins
                if(board.getBoard()[i][j].equals(getPlayersIcon() + "") &&
                   board.getBoard()[i+1][j].equals(getPlayersIcon() + "") &&
                   board.getBoard()[i+2][j].equals(getPlayersIcon() + ""))
                {
                    return true;
                }
            }
            break;
        }

        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                if(board.getBoard()[i][j].equals(getPlayersIcon() + "") &&
                board.getBoard()[i+1][j+1].equals(getPlayersIcon() + "") &&
                board.getBoard()[i+2][j+2].equals(getPlayersIcon() + ""))
                {
                    return true;
                }
                if(board.getBoard()[i][j+2].equals(getPlayersIcon() + "") &&
                board.getBoard()[i+1][j+1].equals(getPlayersIcon() + "") &&
                board.getBoard()[i+2][j].equals(getPlayersIcon() + ""))
                {
                    return true;
                }
                break;
            }
            break;
        }
        return false;
    }

    public boolean hasComputerWon(Board board)
    {
        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                //checks for horizontal wins
                if(board.getBoard()[i][j].equals(getComputersIcon() + "") &&
                   board.getBoard()[i][j+1].equals(getComputersIcon() + "") &&
                   board.getBoard()[i][j+2].equals(getComputersIcon() + ""))
                {
                    return true;
                }
                break;
            }
        }
        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                //checks for vertical wins
                if(board.getBoard()[i][j].equals(getComputersIcon() + "") &&
                   board.getBoard()[i+1][j].equals(getComputersIcon() + "") &&
                   board.getBoard()[i+2][j].equals(getComputersIcon() + ""))
                {
                    return true;
                }
            }
            break;
        }

        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                if(board.getBoard()[i][j].equals(getComputersIcon() + "") &&
                board.getBoard()[i+1][j+1].equals(getComputersIcon() + "") &&
                board.getBoard()[i+2][j+2].equals(getComputersIcon() + ""))
                {
                    return true;
                }
                if(board.getBoard()[i][j+2].equals(getComputersIcon() + "") &&
                board.getBoard()[i+1][j+1].equals(getComputersIcon() + "") &&
                board.getBoard()[i+2][j].equals(getComputersIcon() + ""))
                {
                    return true;
                }
                break;
            }
            break;
        }
        return false;
    }

    public void setIcons(int icon)
    {
        if(icon == 1)
        {
            playersIcon = 'O';
            computersIcon = 'X';
        }
        else
        {
            playersIcon = 'X';
            computersIcon = 'O';
        }
    }

    public char getPlayersIcon()
    {
        return playersIcon;
    }

    public char getComputersIcon()
    {
        return computersIcon;
    }

    public char getCurrentIcon()
    {
        return currentIcon;
    }

    public void setCurrentIcon(char c)
    {
        currentIcon = c;
    }

    public boolean isPlayersTurn()
    {
        return isPlayersTurn;
    }

    public void setIsPlayersTurn(boolean b)
    {
        isPlayersTurn = b;
        if(b)
        {
            setCurrentIcon(getPlayersIcon());
        }
        else
        {
            setCurrentIcon(getComputersIcon());
        }
    }

    public int getNumberOfEmptySquares(Board board)
    {
        int numberOfEmptySquares = 0;

        for(int i = 0; i < board.getBoard().length; i++)
        {
            for(int j = 0; j < board.getBoard()[i].length; j++)
            {
                if(board.getBoard()[i][j].equals(""))
                {
                    numberOfEmptySquares++;
                }
            }
        }
        return numberOfEmptySquares;
    }

    public void changeTurn()
    {
        if(isPlayersTurn)
        {
            isPlayersTurn = false;
        }
        else
        {
            isPlayersTurn = true;
        }
        if(currentIcon == 'X')
        {
            currentIcon = 'O';
        }
        else
        {
            currentIcon = 'X';
        }
    }

    public int max(int[] scores)
    {
        int max = -2;
        for(int i = 0; i < scores.length; i++)
        {
            if(scores[i] > max)
            {
                max = scores[i];
            }
        }
        return max;
    }

    public int min(int[] scores)
    {
        int min = 2;
        for(int i = 0; i < scores.length; i++)
        {
            if(scores[i] < min)
            {
                min = scores[i];
            }
        }
        return min;
    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }
}
