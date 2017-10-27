
public class minesweeper {

	public static void main(String[] args) {
		board grid = new board();
		grid.Intro();
		int w = grid.width();
		int h = grid.height();
		int[][] secret = new int[w][h];
		String[][] visible = new String[w][h];
		int win = 0; 
		grid.boardsize(w, h, secret, visible);
		grid.minesgen(w,h,secret);
		grid.fillsecret(win, h, secret);
		grid.printline(w, h, secret);
		grid.printvisible(w, h, visible);
		win = grid.play(w, h, secret, visible);
		grid.endresult(w, h, secret, win);
	}

}
