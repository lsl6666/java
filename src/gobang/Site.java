package gobang;

import java.awt.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class Site.
 *
 * @date 2020Äê7ÔÂ3ÈÕ
 * @author nieming
 * @version  v1.0
 */
public class Site {
	
	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The color. */
	private Color color;

	/**
	 * Instantiates a new site.
	 */
	public Site() {
	}

	/**
	 * Instantiates a new site.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public Site(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	

}
