package sprites2d;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy {
	
	private BufferedImage pic;
	private Point2D loc;
	private Point2D vel;
	private int size;
	
	public Enemy(double xLoc, double yLoc, double xVel, double yVel, int health) {
		try {
			pic = ImageIO.read(new File("Resources\\enemy.bmp"));
		} catch (IOException e) {
			System.out.println("Couldn't load user picture.");
			e.printStackTrace();
		}
		loc = new Point2D.Double(xLoc, yLoc);
		vel = new Point2D.Double(xVel, yVel);
		size = health;
	}

	public Enemy(Enemy e) {
		pic = e.pic;
		loc = e.loc;
		vel = e.vel;
		size = e.size;
	}

	public Point2D getLoc() {
		return loc;
	}
	
	public void setLoc(Point2D newLoc) {
		loc = newLoc;
	}
	
	public void updateLoc() {
		loc.setLocation(loc.getX() + vel.getX(), loc.getY() + vel.getY());		
	}
	
	public boolean isVisible(int xLow, int xHi, int yLow, int yHi) {
		return ((loc.getX() + pic.getWidth() >= xLow) &&
				(loc.getX() <= xHi) &&
				(loc.getY() + pic.getWidth() >= yLow) &&
				(loc.getY() <= yHi));
	}
	
	public Image getImage() {
		return pic;
	}
	
	//really, really wanted to call this getRect
	public Rectangle getBounds() {
		return new Rectangle((int)(loc.getX()), (int)(loc.getY()), pic.getWidth(), pic.getHeight());
	}

}
