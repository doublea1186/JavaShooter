package sprites2d;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	
	private BufferedImage pic;
	private Point loc;
	private ArrayList<Shot> shots;
	public boolean isShooting;
	
	public Player(int x, int y) {
		try {
			//wouldn't it be nice if magenta was actually transparent..
			pic = ImageIO.read(new File("Resources\\uflier.bmp"));
		} catch (IOException e) {
			System.out.println("Couldn't load user picture.");
			e.printStackTrace();
		}
		loc = new Point(x,y);
		shots = new ArrayList<Shot>();
	}
	
	public Point getLoc() {
		return loc;
	}
	
	public void setLoc(int x, int y) {
		loc = new Point(x,y);
	}
	
	public ArrayList<Shot> getShots() {
		return shots;
	}
	
	public void addShot(Shot s) {
		shots.add(s);
	}
	
	public void removeShot(Shot s) {
		shots.remove(s);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//right now, pushing space toggles shooting
		if (key == KeyEvent.VK_SPACE) {
			isShooting = !isShooting;
		}
	}
	
	public BufferedImage getImage() {
		return pic;
	}
	
	//really, really wanted to call this getRect
	public Rectangle getBounds() {
		return new Rectangle((int)(loc.getX()), (int)(loc.getY()), pic.getWidth(), pic.getHeight());
	}

}
