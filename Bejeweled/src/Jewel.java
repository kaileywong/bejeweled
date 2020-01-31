import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


public abstract class Jewel {
	/**
	 * This spritesheet has all the images in the game.  Each subclass
	 * of Jewel, will use the spritesheet to open a subimage to be shared
	 * by all instances of that subclass.  See Emerald for example.
	 */
	private static Image spriteSheet;
	/** standardize the size of every Jewel */
	public static final int SQUARE_SIZE = 64;
	/** Color of this Jewel.  If another Jewel has the same Color
	 * then they are of the same type */
	private Color color;
	private Image image;
	private Image click;
	private boolean clicked = false;
	private Color over = new Color(255,255,255);
	/** The row and col of this Jewel.  It is used to draw itself, so
	 * any time a Jewel moves, row and col must be updated*/
	private int row, col;

	public Jewel(Color c, Image i, int r, int co) {
		this.color = c;
		this.image = i;
		this.row=r;
		this.col = co;		
	}
	/** Retrieves the subimage from the spritesheet based on the 
	 * specified x,y,w, and h of bounding rectangle of the subimage
	 * Each Jewel has its own bounding rectangle
	 * @param x x-coord of upper-lefthand corner of bounding rectangle
	 * @param y y-coord of upper-lefthand corner of bounding rectangle
	 * @param w width of bounding rectangle
	 * @param h height of bounding rectangle
	 * @return Image that represents this Jewel
	 */
	protected static Image openImageFromSpriteSheet(int x, int y, int w, int h) {
		openSpriteSheet();
		return ((BufferedImage)spriteSheet).getSubimage(x,y,w,h).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
	}
	/**Opens the spritesheet if it hasn't already been opened.  This spritesheet
	 * will be shared by all Jewels
	 */
	private static void openSpriteSheet() {
		try {
			spriteSheet = ImageIO.read(new File("bejeweled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics g) {
		g.drawImage(image, this.col*this.SQUARE_SIZE + BejeweledGrid.OFFSET_X,
				this.row*this.SQUARE_SIZE + BejeweledGrid.OFFSET_Y, null);
		if(this.clicked == true) {
			g.setColor(new Color(255,255,255));
			g.drawRect(this.col*this.SQUARE_SIZE + BejeweledGrid.OFFSET_X,
					this.row*this.SQUARE_SIZE + BejeweledGrid.OFFSET_Y, this.SQUARE_SIZE, this.SQUARE_SIZE);
			g.setColor(new Color(255,255,0,50));
			g.fillRect(this.col*this.SQUARE_SIZE + BejeweledGrid.OFFSET_X,
					this.row*this.SQUARE_SIZE + BejeweledGrid.OFFSET_Y, this.SQUARE_SIZE, this.SQUARE_SIZE);
		}
	}

	/** moves this Jewel dr rows and dc cols. */
	public void move(int dr, int dc) {
		this.col+= dc;
		this.row += dr;
	}
	public void moveUp() {
		this.move(-1, 0);
	}
	public void moveDown() {
		this.move(1, 0);
	}
	public int getRow() {
		return row;
	}
	/** moves this Jewel to the specified row and col.  It jumps 
	 * immediately to that new location and will appear there next 
	 * time the grid is redrawn.
	 * @param r This Jewel's new row
	 * @param c This Jewel's new column
	 */
	public void moveTo(int r, int c) {
		this.col = c;
		this.row = r;
	}
	public int getCol() {
		return col;
	}
	public Color getColor() {
		return color;
	}
	
	public boolean getClicked() {
		return this.clicked;
	}
	
	public void setImage(Image im) {
		image = im;
	}
	
	public void show() {
		this.clicked = true;
	}
	
	public void hide() {
		this.clicked = false;
	}

	public boolean equals(Jewel j) {
		return this.color.equals(j.getColor());
	}

	@Override 
	public String toString() {
		return row+","+col;
	}
}