public class Main {
    int[][] board = new int[9][9];

    int randomGenerator(int num)
    {
        return (int) Math.floor((num * Math.random()) + 1);
    }

    void fillDiagonal()
    {
        for(int i = 0; i < 9; i = i + 3)
        {
            fillBox(i, i);
        }
    }

    void fillBox(int row, int col)
    {
        int num;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                do
                {
                    num = randomGenerator(9);
                }
                while(!unUsedInBox(row, col, num));

                board[row + i][col + j] = num;
            }
        }
    }

    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(board[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    boolean fillRemaining(int row, int col)
    {
        if(col >= 9 && row < 8)
        {
            row = row + 1;
            col = 0;
        }

        if(row >= 9 && col >= 9)
            return true;

        if(row < 3)
        {
            if(col < 3)
                col = 3;
        }
        else if(row < 6)
        {
            if(col == (int)(row / 3) * 3)
                col = col + 3;
        }
        else
        {
            if(col == 6)
            {
                row = row + 1;
                col = 0;
                if(row >= 9)
                    return true;
            }
        }

        for(int num = 1; num <= 9; num++)
        {
            if(CheckIfSafe(row, col, num))
            {
                board[row][col] = num;
                if(fillRemaining(row, col + 1))
                {
                    return true;
                }
                board[row][col] = 0;
            }
        }

        return false;
    };

    boolean CheckIfSafe(int row, int col, int num)
    {
        return (unUsedInRow(row, num) &&
                unUsedInCol(col, num) &&
                unUsedInBox(row - row % 3, col - col % 3, num));
    }

    boolean unUsedInRow(int row, int num)
    {
        for(int i = 0; i < 9; i++)
            if(board[row][i] == num)
                return false;

        return true;
    }

    boolean unUsedInCol(int col, int num)
    {
        for(int i = 0; i < 9; i++)
            if(board[i][col] == num)
                return false;

        return true;
    }

    void generateBoard()
    {
        fillDiagonal();
        fillRemaining(0, 3);
    }

    void printSudoku()
    {
        for(int row = 0; row < 9; row++)
        {
            if(row == 3 || row == 6)
                System.out.println("\n------ | ------ | ------");
            else
                System.out.println(" ");
            for(int col = 0; col < 9; col++)
            {
                if(col == 3 || col == 6)
                {
                    System.out.print(" | ");
                }

                if(row == 8 && col == 8)
                    System.out.println(board[row][col]);
                else
                    System.out.print(board[row][col] + " ");
            }
        }
    }

    void generateBoardForPlayer()
    {
        int i = 0;
        while (i < 30)
        {
            int row = randomGenerator(9) - 1;
            int col = randomGenerator(9) - 1;
            if (board[row][col] != 0)
            {
                int originalValue = board[row][col];
                board[row][col] = 0;

                if (isSolvable(0, 0))
                {
                    i++;
                }
                else
                {
                    board[row][col] = originalValue;
                }
            }
        }
    }


    boolean isSolvable(int row, int col)
    {
        if(row == 9)
            return true;

        if(col == 9)
            return isSolvable(row + 1, 0);

        if(board[row][col] != 0)
            return isSolvable(row, col + 1);

        for(int num = 1; num <= 9; num++)
        {
            if(CheckIfSafe(row, col, num))
            {
                if(isSolvable(row, col + 1))
                    return true;}
        }
        return false;
    }

    boolean solveBoard()
    {
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                if(board[r][c] == 0)
                {
                    for(int num = 1; num <= 9; num++)
                    {
                        if(CheckIfSafe(r, c, num))
                        {
                            board[r][c] = num;
                            if(solveBoard())
                            {
                                return true;
                            }
                            else
                            {
                                board[r][c] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Main sudoku = new Main();
        System.out.println("Sudoku Board generated:");
        sudoku.generateBoard();
        sudoku.printSudoku();
        System.out.println();

        System.out.println("Sudoku Board generated for player:");
        sudoku.generateBoardForPlayer();
        sudoku.printSudoku();
        System.out.println();

        System.out.println("Sudoku Board solved:");
        sudoku.solveBoard();
        sudoku.printSudoku();
        System.out.println();
    }
}