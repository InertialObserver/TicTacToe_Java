public class GameBoard
{
  private int gameBoard [];

	private int vectors [] [] =
		{
			{0,1,2}, // Row 1
			{3,4,5}, // Row 2
			{6,7,8}, // Row 3
			{0,3,6}, // Col 1
			{1,4,7}, // Col 2
			{2,5,8}, // Col 3
			{0,4,8}, // Diag 1
			{2,4,6}  // Diag 2
		};

	public GameBoard()
	{
		this.reset();
	}

  public void reset()
	{
		gameBoard = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2}; // set all squares to empty (value of empty is 2)
	}

	private int getSquare(int index)
  {
	  return gameBoard[index];
  }

  public int getSquare(String square)
	{
		int index = mapSquareToIndex(square);
		if (index == -1)
		{
			throw new IllegalArgumentException("Invalid Square");
		}
		switch(getSquare(index))
		{
		case 3:
			return 1;
		case 5:
			return 2;
			default:
				return 0;
		}
	}

  private int mapSquareToIndex(String square)
	{
		switch(square)
		{
		case "A1":
			return 0;
		case "A2":
			return 1;
		case "A3":
			return 2;
		case "B1":
			return 3;
		case "B2":
			return 4;
		case "B3":
			return 5;
		case "C1":
			return 6;
		case "C2":
			return 7;
		case "C3":
			return 8;
		default:
			return -1;
		}
	}

  private String mapIndexToSquare(int index)
	{
		switch(index)
		{
		case 0:
			return "A1";
		case 1:
			return "A2";
		case 2:
			return "A3";
		case 3:
			return "B1";
		case 4:
			return "B2";
		case 5:
			return "B3";
		case 6:
			return "C1";
		case 7:
			return "C2";
		case 8:
			return "C3";
		default:
			return "";
		}
	}


  public void goPlay(String square, int player)
	{
		int index = mapSquareToIndex(square);
		if (index == -1)
			throw new IllegalArgumentException("Invalid square");
		this.goPlay(index, player);
	}

  private void goPlay(int index, int player)
	{

		if(player == 1)
		{
			gameBoard[index] = 3;
		} else {
			gameBoard[index] = 5;
		}
	}

  public int gameOver()
	{
		/**
		 * check if a player has won
		 */
		for(int v = 0; v < 8; v++)
		{
			int p = getVectorProduct(v);
			if(p == 27)
			{
				return 1;  // player 1 wins
			}
			if(p == 125)
			{
				return 2;  // computer wins
			}
		}
		/**
		 * check if it's a draw
		 */
		int blanks = 0;
		for(int i = 0; i < 9; i++) // iterate through all the squares
		{
			if(gameBoard[i] == 2) // if the square is blank, increase count
			{
				blanks++;
			}
		}
		if(blanks == 0) // if none of the squares are blank
		{
			return 3;  // game is a draw
		}
		return 0;  // if there are any blank squres, game not a draw and not over
	}

  public String canPlayerWin(int player)
	{
		if(player < 1 || player > 2)
		{
			throw new IllegalArgumentException("Player must be 1 or 2");
		}

		for(int v = 0; v < 8; v++)
		{
			int p = getVectorProduct(v);
			if((player == 1 & p == 18) || (player == 2 & p == 50))
			{
				if(gameBoard[vectors[v][0]] == 2)
				{
					return mapIndexToSquare(vectors[v][0]);
				}
				if(gameBoard[vectors[v][1]] == 2)
				{
					return mapIndexToSquare(vectors[v][1]);
				}
				if(gameBoard[vectors[v][2]] == 2)
				{
					return mapIndexToSquare(vectors[v][2]);
				}
			}
		}
		return "";
	}

  private int getVectorProduct(int vector)
	{
		return gameBoard[vectors[vector][0]] *
		       gameBoard[vectors[vector][1]] *
		       gameBoard[vectors[vector][2]];
	}

    public String getNextMove()
  	{
  		String bestMove;

  		/**
  		 * Go for the win
  		 */
  		bestMove = this.canPlayerWin(2);
  		if(bestMove != "")
  		{
  			return bestMove;
  		}

  		/**
  		 * Block the puny human
  		 */
  		 bestMove = this.canPlayerWin(1);
   		if(bestMove != "")
   		{
   			return bestMove;
   		}

   		/**
   		 * Try center square first
   		 */
   		if(gameBoard[4] == 2) // if position B2 (center square) is empty, put the marker there
   		{
   			return "B2";
   		}
   		/**
   		 * Failing that, find a corner
   		 */
   		if(gameBoard[0] == 2) // if position A1 is empty, put the marker there
   		{
   			return "A1";
   		}
   		if(gameBoard[2] == 2) // if position A3 is empty, put the marker there
   		{
   			return "A3";
   		}
   		if(gameBoard[6] == 2) // if position C1 is empty, put the marker there
   		{
   			return "C1";
   		}
   		if(gameBoard[8] == 2)
   		{
   			return "C3";
   		}
   		/**
   		 * Failing that, take an edge
   		 */
   		if(gameBoard[1] == 2)  // if position A2 is empty, put the marker there
   		{
   			return "A2";
   		}
   		if(gameBoard[3] == 2)
   		{
   			return "B1";
   		}
   		if(gameBoard[5] == 2)
   		{
   			return "B3";
   		}
   		if(gameBoard[7] == 2)
   		{
   			return "C2";
   		}
   		/**
   		 * The board is full.
   		 */
   		return ""; // no spaces are empty, so no mark can be placed

   	}
	}
