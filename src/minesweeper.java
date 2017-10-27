
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
		grid.generatemines(w,h,secret);
		grid.fillsecret(w, h, secret);
		grid.displaysecret(w, h, secret);
		grid.displayvisible(w, h, visible);
		win = grid.play(w, h, secret, visible);
		grid.endresult(w, h, secret, win);
	}

}
