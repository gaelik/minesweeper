import java.util.Scanner;
import java.util.Random;
public class board {
	public int w,h;
	public int[][] array1;
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
		return w;
			}
	public int height() {
		System.out.println("Input height");
		h = reader.nextInt();
		return h;
	}
			
	
	public void boardsize(int w, int h, int[][] array1, String[][] visible) {
		int x,y;
		for(x=0; x<w; x++) {
			for(y=0;y<h;y++) {
				array1[x][y]=0;
				visible[x][y] = "#";
			}
		}
	}
	public void minesgen(int w, int h, int[][] array1) {
		//System.out.println("within minesgem");
		int minecount=1;
		Random rand = new Random();
		while(minecount<11) {
			int a = rand.nextInt(w);
			int b = rand.nextInt(h);
			if ( array1[a][b] == 0 ) {
				array1[a][b] = 9;
				minecount++;
			} 
		}
		
	}
		
	public void printline(int w, int h, int[][] array1) {
		int x,y;
		//System.out.println("printline method");
		System.out.printf(" |0 1 2 3 4 5 6 7\n");
		System.out.printf("-----------------\n");
		for(y=0; y<h; y++) {
			System.out.print((y) + "|");
			for(x=0; x<w;x++) {
				System.out.print(array1[x][y] + " " );
			}
			System.out.println();
		}
	}

	public void printvisible(int w, int h, String[][] visible) {
		int x,y;
		System.out.println("printline method");
		System.out.printf(" |1 2 3 4 5 6 7 8\n");
		System.out.printf("-----------------\n");
		for(y=0; y<h; y++) {
			System.out.print((y + 1) + "|");
			for(x=0; x<w;x++) {
				System.out.printf("%s ", visible[x][y] );

			}
			System.out.println();
		}
	}

	public int play(int w, int h, int[][] array1, String[][] visible) {
		int x,y;
		boolean end = false;
		int win = 0;
		String flag;
		int plantaflag = 0;
		while ( end == false) {
			do {
				System.out.printf("enter X (1-" + w + "):");
				x = reader.nextInt() - 1;
			} while ( x < 0 || x > 7);
			do {
				System.out.printf("enter Y (1-" + h + "):");
				y = reader.nextInt() - 1;
			} while ( y < 0 || y > 7);
			System.out.printf("F or not\n");
			flag = reader.next();
			System.out.println("flag " + flag);
			if ( flag.equals("F")) {
				System.out.println("in flag");
				plantaflag = 1;
				visible[x - 1][y - 1] = "F";
			} else if ( array1[x][y] == 9 ) {
				end = true;
				win = 0;
			} else { 
				neighbors(w,h,x,y, array1);
			}
			printvisible(w,h,visible);
		}
		return win;
	}
	
	public void fillarray1 ( int w, int h, int[][] array1 ) {
		// prefill array1 putting the amount of neighboring mines
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
		for(x=0; x<w; x++) {
			for(y=0;y<h;y++) {
				System.out.println("within neighbors " + x + " " + y );
				if ( array1[x][y] == 0 ) {
					neighbors(w,h,x,y,array1);
				} else {
					System.out.printf("Skipping (%d,%d) because of the mine in it\n",x,y);
				}
			}
		}
		
	}
	
	public void neighbors (int w, int h, int x, int y, int[][] array1) {
		//
		//			  (X-1,Y-1)  (X,Y-1)  (X+1,Y-1) 
		//            (X-1,Y)      X,Y    (X+1,Y)
		//            (X-1,Y+1)  (X,Y+1)  (X+1,Y+1)
		//
		//   if adjacent square = 0, target it and run neighbors method on it, clone array1 squares
		//   content within the visible array
		int a = 0,b = 0; 
		int minesdetected = 0;
		System.out.printf("center of the grid (%s,%s)\n", x,y );
		if ( array1[x][y] == 0 ) {
			for (b = y-1; b<= y+1; b++) {
				for (a=x-1; a <= x+1; a++) {
					System.out.printf("testing (%s,%s)\n", a,b );
					// a and b are contained in the array1 grid
					if (	 a >= 0 && a < w  && b >= 0 && b < h ) {  
						System.out.printf("(%s,%s)\n", a,b );
						// if the square checked contains a bomb, then mines count increments
						if ( array1[a][b] == 9 ) {
							System.out.printf("(%d,%d) => %d mine detected\n", a,b, array1[a][b]);
							minesdetected++;
						}
					// a or b is outside the array1 grid range
					} else {
						System.out.printf("skipped (%s,%s) because a or b is not within the array\n", a,b );	
					}
				}
			}
		}
		// assign the final count to array1 square
		array1[x][y] = minesdetected;
		System.out.println("Assigning " + minesdetected + " to (" + x + "," + y + ")" );
		printline(w,h,array1);
	}
	
	public void endresult (int w,int h, int[][] array1, int win) {
		if ( win == 0 ) {
			System.out.println("Boom, You lose");
			printline(w,h,array1);
		} else {
			System.out.println("You win");
		}
		printline(w,h,array1);
	}

}



