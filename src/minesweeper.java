
public class minesweeper {

	public static void main(String[] args) {
		board grid = new board();
		grid.Intro();
		int w = grid.width();
		int h = grid.height();
		int[][] array1 = new int[w][h];
		String[][] visible = new String[w][h];
		int win = 0; 
		grid.boardsize(w, h, array1, visible);
		grid.minesgen(w,h,array1);
		grid.printline(w, h, array1);
		grid.printvisible(w, h, visible);
		win = grid.play(w, h, array1, visible);
		grid.status(w, h, array1, win);
	}

}
