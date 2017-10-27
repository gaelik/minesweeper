import java.util.Scanner;
import java.util.Random;
public class board {
	public int w,h;
	public int[][] secret;
	public String[][] visible;
	Scanner reader = new Scanner(System.in);
//Add instructions for the game
	public void Intro() {
		System.out.println(
	  "This program will create Minesweeper games" 
	+ "\n The rules are simple" 
	+ "\n The numbers 1-8 represent nearby bombs and the number 9 represents a bomb" 
	+ "\n the goal is to find fill out the map and not hitting any bombs");
		
		
	}
	public int width() {
		System.out.print("Input width");
		w = reader.nextInt();
		w++;
		return w;
			}
	public int height() {
		System.out.print("Input height");
		h = reader.nextInt();
		h++;
		return h;
	}
			
	// Initialize both arrays secret and visible
	public void boardsize(int w, int h, int[][] secret, String[][] visible) {
		int x,y;
		for(x=1; x<w; x++) {
			for(y=1;y<h;y++) {
				secret[x][y]=0;
				visible[x][y] = "#";
			}
		}
	}
	
	// Randomly places 10 mines
	public void generatemines(int w, int h, int[][] secret) {
		int minecount=1;
		Random rand = new Random();
		while(minecount<11) {
			int a = rand.nextInt(w);
			int b = rand.nextInt(h);
			if ( secret[a][b] == 0 ) {
				secret[a][b] = 9;
				minecount++;
			} 
		}
		//System.out.println("mines " + minecount);
		//displaysecret(w,h,secret);
	}
		
	// Displays the content of the array secret
	public void displaysecret(int w, int h, int[][] secret) {
		int x,y;
		System.out.printf("\n\n");
		System.out.printf(" |1 2 3 4 5 6 7 8\n");
		System.out.printf("-----------------\n");
		for(y=1; y<h; y++) {
			System.out.print((y) + "|");
			for(x=1; x<w;x++) {
					System.out.print(secret[x][y] + " " );
			}
			System.out.println();
		}
		System.out.printf("\n\n");
	}

	// Displays the content of the array visible
	public void displayvisible(int w, int h, String[][] visible) {
		int x,y;
		System.out.printf("\n\n");
		System.out.printf(" |1 2 3 4 5 6 7 8\n");
		System.out.printf("-----------------\n");
		for(y=1; y<h; y++) {
			System.out.print((y) + "|");
			for(x=1; x<w;x++) {
				System.out.printf("%s ", visible[x][y] );
			}
			System.out.println();
		}
		System.out.printf("\n\n");
	}

	// The game itself
	public int play(int w, int h, int[][] secret, String[][] visible) {
		int x,y;
		boolean end = false;
		int win = 0;
		String flag;
		while ( end == false) {
			do {
				System.out.printf("Enter X (1-" + ( w - 1 ) + "):");
				x = reader.nextInt() ;
			} while ( x < 1 || x > 8);
			do {
				System.out.printf("Enter Y (1-" + ( h - 1 ) + "):");
				y = reader.nextInt() ;
			} while ( y < 1 || y > 8);
			System.out.printf("Enter f to add or remove a flag, any other key to continue:");
			flag = reader.next();
			if ( flag.equals("f")) {
				if ( visible[x][y].equals("#")) {
					visible[x][y] = "F";
				} else if (visible[x][y].equals("F")) {
					visible[x][y] = "#";
				}
			} else if ( visible[x][y].equals("F")) {
				System.out.println("This is a flagged square, please unflag it first before targeting it");
			} else if ( secret[x][y] == 9 ) {
				end = true;
				win = 0;
			} else {
				if ( secret[x][y] == 0 ) {
					visible[x][y] = " ";
					neighborsvisible(w,h,x,y,secret,visible);
					uncovervisible(w,h, secret,visible);
				} else {
					visible[x][y] = Integer.toString(secret[x][y]);
				}
			}
			displayvisible(w,h,visible);
			if ( uncoveredsquares(w,h,secret, visible) == 10) {
				win = 1;
				end = true;
			}
		}
		return win;
	}
	
	// Populates the secret array with all the possible values
	public void fillsecret ( int w, int h, int[][] secret ) {

		//		  |1 2 3 4 5 6 7 8
		//		 -----------------
		//		 1|0 0 0 0 0 0 1 9 
		//		 2|1 1 1 1 1 1 1 1 
		//		 3|3 9 2 1 9 1 0 0 
		//		 4|9 9 2 1 1 1 1 1 
		//		 5|3 2 1 0 1 2 9 1 
		//		 6|9 1 0 0 1 9 3 2 
		//		 7|1 1 0 0 2 2 3 9 
		//		 8|0 0 0 0 1 9 2 1 	
		
		int x,y;
		for(x=1; x<w; x++) {
			for(y=1;y<h;y++) {
				if ( secret[x][y] == 0 ) {
					neighbors(w,h,x,y,secret);
				}
			}
		}
		
	}
	
	// Count how many mines are in the adjacent squares around the target x,y
	public int neighbors (int w, int h, int x, int y, int[][] secret) {
		//
		//			  (X-1,Y-1)  (X,Y-1)  (X+1,Y-1) 
		//            (X-1,Y)      X,Y    (X+1,Y)
		//            (X-1,Y+1)  (X,Y+1)  (X+1,Y+1)
		//
		//   scan the 8 possible adjacents squares, if they contain 9, increment minesdetected.
		int a = 0,b = 0; 
		int minesdetected = 0;
		if ( secret[x][y] == 0 ) {
			for (b = y-1; b<= y+1; b++) {
				for (a=x-1; a <= x+1; a++) {
					
					// a and b are contained in the secret grid
					if (	 a > 0 && a < w  && b > 0 && b < h ) {  
						// if the square checked contains a bomb, then mines count increments
						if ( secret[a][b] == 9 ) {
							minesdetected++;
						}
					}
				}
			}
		}
		// assign the final count to secret square
		secret[x][y] = minesdetected;
		return minesdetected;
	}
	
	// Compare the adjacent squares to the target in x,y within the array secret and populate the entries into visible if > 0
	public void neighborsvisible (int w, int h, int x, int y, int[][] secret, String[][] visible) {
		//
		//			  (X-1,Y-1)  (X,Y-1)  (X+1,Y-1) 
		//            (X-1,Y)      X,Y    (X+1,Y)
		//            (X-1,Y+1)  (X,Y+1)  (X+1,Y+1)
		//
		//   scan the 8 possible adjacents squares, if they contain a value > 0 in the secret array, copy it into visible
		//  if the entries in secret are 0, replace the content of the square with a "." to allow scanning it later on
		int a = 0,b = 0; 

			for (b = y - 1; b<= y + 1; b++) {
				for (a=x - 1; a <= x + 1; a++) {
					
					// a and b are contained in the visible grid
					if (	 a > 0 && a < w  && b > 0 && b < h ) {  
						
						// if the adjacent square contains a "." it will be individually scanned later on
						// do nothing
						if ( visible[a][b].equals(".")) {
						 
						// if the square contains a value between 1 and 8, copy the value from secret into visible
						} else if ( secret[a][b] > 0  && secret[a][b] < 8 && visible[a][b].equals("#") ) {
							visible[a][b] = Integer.toString(secret[a][b]); // copying the secret entry 
							
						// if this square contains 0 into secret and is still unchecked, flagging it for a scan later on
						} else if ( secret[a][b] == 0 && visible[a][b].equals("#"))  {
							visible[a][b] = ".";   // marking that square for scanning later on.
						}
					}
				}
			}
	}
	
	// This method counts how many squares are containing "." into visible.
	// Those will be the squares scanned by uncovervisible
	public int countsquarestargeted( int w, int h, String[][] visible) {
		int x,y;
		int remaining=0;
		for(x=1; x<w; x++) {
			for(y=1;y<h;y++) {
				if ( visible[x][y].equals(".")) {
					remaining++;
				}
			}
		}
		return remaining;
	}
	
	// scan the whole array and run neighborsvisible against all squares containing a "."
	// the loop will stops once there are no more squares containing "." into the visible array
	public void uncovervisible ( int w, int h, int[][] secret, String[][] visible) {
		int x,y;
		do {
			for(x=1; x<w; x++) {
				for(y=1;y<h;y++) {
					// Replacing "." by space in the target to avoid it being scanned again later.
					if ( visible[x][y].equals(".")) {
						visible[x][y] = " ";
						neighborsvisible(w,h, x,y, secret, visible);
					}
				}
			}
		} while ( countsquarestargeted(w,h,visible) > 0);
	}
	
	// Counts how many squares contains "#" and "F", the remaining unchecked squares ( once that total is equal to 10, the game is won. )
	public int uncoveredsquares ( int w, int h, int[][] secret, String[][] visible) {
		int howmanyleft = 0;
		int x,y;

			for(x=1; x<w; x++) {
				for(y=1;y<h;y++) {
					if ( visible[x][y].equals("#") && secret[x][y] == 9) {
						howmanyleft++;
					} else if (visible[x][y].equals("F") && secret[x][y] == 9 ) {
						howmanyleft++;
					// if we still have squares with "#" not matching a mine or wrongly flaged in secret, we cannot win yet
					} else if (visible[x][y].equals("#") || visible[x][y].equals("F")) {
						howmanyleft = w * h;
					}
				}
			}
			return howmanyleft;
	}
	
	// Displays the game result
	public void endresult (int w,int h, int[][] secret, int win) {
		if ( win == 0 ) {
			System.out.printf("\n\nBoom, sorry, you failed\n\n");
		} else {
			System.out.printf("\n\nYou win !!\n\n");
		}
		displaysecret(w,h,secret);
	}

}



