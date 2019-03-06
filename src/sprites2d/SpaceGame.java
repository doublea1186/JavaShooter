package sprites2d;

import java.awt.EventQueue;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SpaceGame extends JFrame {
	
	public final int BOARDSIZE = 600;
	public final int VERSPACE = 8;
	public final int HRZSPACE = 8;
	
	public SpaceGame() {
        setSize(BOARDSIZE+HRZSPACE, BOARDSIZE+VERSPACE);
		add(new Board(BOARDSIZE, BOARDSIZE));
        setResizable(false);
        setTitle("Space Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SpaceGame go = new SpaceGame();
                go.setVisible(true);
            }
        });
	}
}

