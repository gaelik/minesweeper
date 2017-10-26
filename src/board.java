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
			
	
	public void boardsize(int w, int h) {
		int x,y;
		int[][] array1 = new int[w][h];
		String[][] visible = new String[w][h];
		System.out.println("boardsize");
		for(x=0; x<w; x++) {
			for(y=0;y<h;y++) {
				array1[x][y]=0;
				visible[x][y] = "\\s";
			}
		}
	}
	public void minesgen(int w, int h, int[][] array1) {
		System.out.println("within minesgem");
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
		System.out.println("printline method");
		System.out.printf(" |1 2 3 4 5 6 7 8\n");
		System.out.printf("-----------------\n");
		for(y=0; y<h; y++) {
			System.out.print((y + 1) + "|");
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
				System.out.print(visible[x][y] + " " );

			}
			System.out.println();
		}
	}

	public void play(int w, int h, int[][] array1, String[][] visible) {
		int x,y;
		boolean end = false;
		while ( end == false) {
			System.out.printf("enter X:");
			x = reader.nextInt() - 1;
			System.out.printf("enter Y:");
			y = reader.nextInt() - 1;

			if ( array1[x][y] == 9 ) {
				System.out.println("You lose");
				printvisible(w,h,visible);
				end = true;
			}
		}
	}


}



