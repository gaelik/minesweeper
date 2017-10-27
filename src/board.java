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
		System.out.println("Input height");
		h = reader.nextInt();
		h++;
		return h;
	}
			
	
	public void boardsize(int w, int h, int[][] secret, String[][] visible) {
		int x,y;
		for(x=1; x<w; x++) {
			for(y=1;y<h;y++) {
				secret[x][y]=0;
				visible[x][y] = "#";
				//System.out.printf("%d,%d = %d\n", x, y , secret[x][y] );
			}
		}
	}
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
		
	}
		
	public void displaysecret(int w, int h, int[][] secret) {
		int x,y;
		System.out.printf(" |1 2 3 4 5 6 7 8\n");
		System.out.printf("-----------------\n");
		for(y=1; y<h; y++) {
			System.out.print((y) + "|");
			for(x=1; x<w;x++) {
				System.out.print(secret[x][y] + " " );
			}
			System.out.println();
		}
	}

	public void displayvisible(int w, int h, String[][] visible) {
		int x,y;
		System.out.printf(" |1 2 3 4 5 6 7 8\n");
		System.out.printf("-----------------\n");
		for(y=1; y<h; y++) {
			System.out.print((y) + "|");
			for(x=1; x<w;x++) {
				System.out.printf("%s ", visible[x][y] );

			}
			System.out.println();
		}
	}

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
			System.out.println("flag " + flag);
			if ( flag.equals("f")) {
				if ( visible[x][y].equals("#")) {
					visible[x][y] = "F";
				} else if (visible[x][y].equals("F")) {
					visible[x][y] = "#";
				}
			} else if ( secret[x][y] == 9 ) {
				end = true;
				win = 0;
			} else {
				if ( secret[x][y] == 0 ) {
					visible[x][y] = " ";
				} else {
					visible[x][y] = Integer.toString(secret[x][y]);
				}
			}
			displayvisible(w,h,visible);
		}
		return win;
	}
	
	public void fillsecret ( int w, int h, int[][] secret ) {
		// prefill secret putting the amount of neighboring mines
		//
//		  |0 1 2 3 4 5 6 7
//		 -----------------
//		 0|0 0 0 0 0 0 1 9 
//		 1|1 1 1 1 1 1 1 1 
//		 2|3 9 2 1 9 1 0 0 
//		 3|9 9 2 1 1 1 1 1 
//		 4|3 2 1 0 1 2 9 1 
//		 5|9 1 0 0 1 9 3 2 
//		 6|1 1 0 0 2 2 3 9 
//		 7|0 0 0 0 1 9 2 1 	
		int x,y;
		for(x=1; x<w; x++) {
			for(y=1;y<h;y++) {
				if ( secret[x][y] == 0 ) {
					neighbors(w,h,x,y,secret);
				}
			}
		}
		
	}
	
	public int neighbors (int w, int h, int x, int y, int[][] secret) {
		//
		//			  (X-1,Y-1)  (X,Y-1)  (X+1,Y-1) 
		//            (X-1,Y)      X,Y    (X+1,Y)
		//            (X-1,Y+1)  (X,Y+1)  (X+1,Y+1)
		//
		//   if adjacent square = 0, target it and run neighbors method on it, clone secret squares
		//   content within the visible array
		int a = 0,b = 0; 
		int minesdetected = 0;
		if ( secret[x][y] == 0 ) {
			for (b = y-1; b<= y+1; b++) {
				for (a=x-1; a <= x+1; a++) {
					//System.out.printf("testing (%s,%s)\n", a,b );
					// a and b are contained in the secret grid
					if (	 a > 0 && a < w  && b > 0 && b < h ) {  
						// if the square checked contains a bomb, then mines count increments
						if ( secret[a][b] == 9 ) {
							minesdetected++;
						}
					// a or b is outside the secret grid range
					}
				}
			}
		}
		// assign the final count to secret square
		secret[x][y] = minesdetected;
		return minesdetected;
	}
	
		
	public void endresult (int w,int h, int[][] secret, int win) {
		if ( win == 0 ) {
			System.out.println("Boom, You lose");
		} else {
			System.out.println("You win");
		}
		displaysecret(w,h,secret);
	}

}



