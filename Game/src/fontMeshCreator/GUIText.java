package fontMeshCreator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fontRendering.TextMaster;

/**
 * Represents a piece of text in the game.
 * 
 * @author Karl
 *
 */
public class GUIText {

	private String textString;
	private float fontSize;
	
	private boolean useEffects = true;

	private int textMeshVao;
	private int vertexCount;
	private Vector3f colour = new Vector3f(0f, 0f, 0f);
	
	private float previousWidth;
	private float width;
	private float borderWidth;
	private float edge;
	private float borderEdge;
	private Vector3f outlineColour = new Vector3f(0f, 0f, 0f);
	
	private float offsetX = 0;
	private float offsetY = 0;

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;

	private FontType font;

	private boolean centerText = false;

	/**
	 * Creates a new text, loads the text's quads into a VAO, and adds the text
	 * to the screen.
	 * 
	 * @param text
	 *            - the text.
	 * @param fontSize
	 *            - the font size of the text, where a font size of 1 is the
	 *            default size.
	 * @param font
	 *            - the font that this text should use.
	 * @param position
	 *            - the position on the screen where the top left corner of the
	 *            text should be rendered. The top left corner of the screen is
	 *            (0, 0) and the bottom right is (1, 1).
	 * @param maxLineLength
	 *            - basically the width of the virtual page in terms of screen
	 *            width (1 is full screen width, 0.5 is half the width of the
	 *            screen, etc.) Text cannot go off the edge of the page, so if
	 *            the text is longer than this length it will go onto the next
	 *            line. When text is centered it is centered into the middle of
	 *            the line, based on this line length value.
	 * @param centered
	 *            - whether the text should be centered or not.
	 */
	public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
			boolean centered, float width, float borderWidth, float edge, float borderEdge) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		this.width = width;
		this.previousWidth = borderWidth;
		this.borderWidth = borderWidth;
		this.edge = edge;
		this.borderEdge = borderEdge;
		TextMaster.loadText(this);
	}
	
	public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
			boolean centered, float width, float borderWidth, float edge, float borderEdge, Vector3f outlineColour) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		this.width = width;
		this.previousWidth = borderWidth;
		this.borderWidth = borderWidth;
		this.edge = edge;
		this.borderEdge = borderEdge;
		this.outlineColour = outlineColour;
		TextMaster.loadText(this);
	}

	/**
	 * Remove the text from the screen.
	 */
	public void remove() {
		TextMaster.removeText(this);
	}

	/**
	 * @return The font used by this text.
	 */
	public FontType getFont() {
		return font;
	}

	/**
	 * Set the colour of the text.
	 * 
	 * @param r
	 *            - red value, between 0 and 1.
	 * @param g
	 *            - green value, between 0 and 1.
	 * @param b
	 *            - blue value, between 0 and 1.
	 */
	public void setColour(float r, float g, float b) {
		colour.set(r, g, b);
	}

	/**
	 * 
	 * @param r 
	 * 			  - red value, between 0 and 1. 
	 * @param g
	 * 			  - green value, between 0 and 1.
	 * @param b
	 * 			  - blue value, between 0 and 1.
	 */
	public void setOutlineColour(float r, float g, float b) {
		outlineColour.set(r, g, b);
	}
	
	/**
	 * @return the colour of the text.
	 */
	public Vector3f getColour() {
		return colour;
	}

	/**
	 * @return The number of lines of text. This is determined when the text is
	 *         loaded, based on the length of the text and the max line length
	 *         that is set.
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}

	/**
	 * @return The position of the top-left corner of the text in screen-space.
	 *         (0, 0) is the top left corner of the screen, (1, 1) is the bottom
	 *         right.
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @return the ID of the text's VAO, which contains all the vertex data for
	 *         the quads on which the text will be rendered.
	 */
	public int getMesh() {
		return textMeshVao;
	}

	/**
	 * Set the VAO and vertex count for this text.
	 * 
	 * @param vao
	 *            - the VAO containing all the vertex data for the quads on
	 *            which the text will be rendered.
	 * @param verticesCount
	 *            - the total number of vertices in all of the quads.
	 */
	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	/**
	 * @return The total number of vertices of all the text's quads.
	 */
	public int getVertexCount() {
		return this.vertexCount;
	}

	/**
	 * @return the font size of the text (a font size of 1 is normal).
	 */
	protected float getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the number of lines that this text covers (method used only in
	 * loading).
	 * 
	 * @param number
	 */
	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	/**
	 * @return {@code true} if the text should be centered.
	 */
	protected boolean isCentered() {
		return centerText;
	}

	/**
	 * @return The maximum length of a line of this text.
	 */
	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	/**
	 * @return The string of text.
	 */
	protected String getTextString() {
		return textString;
	}

	public void setOffset(Vector2f offset) {
		if (useEffects) {
			offsetX = offset.x;
			offsetY = offset.y;
		} else {
			offsetX = 0;
			offsetY = 0;
		}
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getBorderWidth() {
		return borderWidth;
	}
	
	public float getEdge() {
		return edge;
	}
	
	public float getBorderEdge() {
		return borderEdge;
	}
	
	public Vector3f getOutlineColour() {
		return outlineColour;
	}
	
	public Vector2f getOffset() {
		return new Vector2f(offsetX, offsetY);
	}
	
	public void useEffects(boolean effects) {
		useEffects = effects;
		if (!useEffects) {
			this.borderWidth = 0;
		} else {
			this.borderWidth = this.previousWidth;
		}
	}
	
}
