package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector2DTest {

	@Test
	public void testTranslate() {
		Vector2D vector = new Vector2D(2.2, 4.4);
		Vector2D offset = new Vector2D(2.0, 2.0);
		vector.translate(offset);
		assertEquals("4,20i + 6,40j", vector.toString());
	}

	@Test
	public void testTranslated() {
		Vector2D vector = new Vector2D(2.2, 4.4);
		Vector2D offset = new Vector2D(2.0, 2.0);
		Vector2D result = vector.translated(offset);
		assertEquals("4,20i + 6,40j", result.toString());
	}

	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(3, 3);
		vector.rotate(90);
		assertEquals("-3,00i + 3,00j", vector.toString());
	}

	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(3, 3);
		Vector2D result = vector.rotated(180);
		assertEquals("-3,00i + -3,00j", result.toString());
	}

	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(2, 3);
		vector.scale(11);
		assertEquals("22,00i + 33,00j", vector.toString());
	}

	@Test
	public void testScaled() {
		Vector2D vector = new Vector2D(3.3, 3.8);
		Vector2D result = vector.scaled(4.5);
		assertEquals("14,85i + 17,10j", result.toString());
	}

	@Test
	public void testCopy() {
		Vector2D vector = new Vector2D(1.8, -4.6);
		Vector2D result = vector.copy();
		assertEquals("1,80i + -4,60j", result.toString());
	}

}
