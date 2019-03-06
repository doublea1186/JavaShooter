package sprites2d;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	//private int vOffset;
	//private int hOffset;
	private Player user;
	private ArrayList<Enemy> enemies;
	private Timer timer;
	private Point size;
	
	public Board(int xDim, int yDim) {
		super();
		setBackground(Color.BLACK);
		addMouseListener(new MAdapter());
		addMouseMotionListener(new MAdapter());
		addKeyListener(new KAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		//common initialization stuff
		user = new Player(xDim/2, yDim*5/6);
		enemies = new ArrayList<Enemy>();
		size = new Point(xDim, yDim);
		timer = new Timer(0, this);
		timer.start();
		//hide the mouse pointer
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TRANSLUCENT), new Point(0, 0), "blank cursor");
		setCursor(blankCursor);
		
	}
	
	//if anything happened, redraw the picture
	public void actionPerformed(ActionEvent e) {
		updateShots();
		updateEnemies();
		updateCollisions();
		repaint();		
	}
	
	public void updateShots() {
		if (user.isShooting) { //so many shots...
			user.addShot(new Shot(user.getLoc().x, user.getLoc().y, 6.0*Math.random()-3, 6.0*Math.random()-3));
		}
		int i = 0;
		while (i < user.getShots().size()) {
			if (!(user.getShots().get(i).isVisible(0, size.x, 0, size.y))) {
				user.removeShot(user.getShots().get(i));
			} else {
				user.getShots().get(i).updateLoc();
				i++;
			}
		}
	}
	
	public void updateEnemies() {
		if (Math.random() > 0.6) { //random random random!
			enemies.add(new Enemy(size.x*Math.random(), 0.0, 5*Math.random()-2.5, 2.5*Math.random(), 1));
		}
		int i = 0;
		while (i < enemies.size()) {
			if (!(enemies.get(i).isVisible(0, size.x, 0, size.y))) {
				enemies.remove(i);
			} else {
				enemies.get(i).updateLoc();
				i++;
			}
		}
		System.out.println(enemies.size());
	}
	
	private boolean shotCollided(Shot s, Enemy e) {
		return s.getBounds().intersects(e.getBounds());
	}
	
	public void updateCollisions() {
		int i = 0;
		int j = 0;
		//using while loops is better than for loops since the lists are being modified
		while (i < user.getShots().size()) {
			j = 0;
			while ((i < user.getShots().size()) && (j < enemies.size())) {
				if (shotCollided(user.getShots().get(i), enemies.get(j))) {
					enemies.remove(j);
					user.getShots().remove(i);
				}
				j++;
			}
			i++;
		}
	}
	
    public void paintComponent(Graphics g)  	                 // draw graphics in the panel
    {
        super.paintComponent(g);                              	 // call superclass to make panel display correctly
        renderEnemies(g);
        renderShots(g);
        renderUser(g);
        Toolkit.getDefaultToolkit().sync();
    }
        
	private void renderShots(Graphics g) {
		for (Shot s : user.getShots()) {
			g.drawImage(s.getImage(),  (int)(s.getLoc().getX()), (int)(s.getLoc().getY()), this);
		}
	}

	private void renderUser(Graphics g) {
		g.drawImage(user.getImage(),  user.getLoc().x - user.getImage().getWidth()/2,  user.getLoc().y - user.getImage().getHeight()/2, this);
		
	}

	private void renderEnemies(Graphics g) {
		for (Enemy nme : enemies) {
			g.drawImage(nme.getImage(),  (int)(nme.getLoc().getX()),  (int)(nme.getLoc().getY()),  this);
		}
		
	}

	//where the mouse handler goes
	private class MAdapter extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			user.setLoc(e.getX(), e.getY());	
		}
	}
	
	//where the key handler goes
	private class KAdapter extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			//quit out if escape is pressed, let the player class handle other input...so far
			if (key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			} else {
				user.keyPressed(e);
			}
		}
	}
	
}
