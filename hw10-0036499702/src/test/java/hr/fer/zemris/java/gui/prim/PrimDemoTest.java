package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PrimDemoTest {
	
	private PrimDemo prim;
	
	@Before
	public void setup() {
		prim = new PrimDemo();
	}
	
	@Test
	public void testEmpty() {
		assertTrue(prim.getModel().getSize() == 1);
		assertTrue(prim.getModel().getElementAt(0) == 1);
	}
	
	@Test
	public void testNext() {
		for(int i = 0; i < 5; i++) {
			prim.getModel().next();
		}
		
		assertTrue(prim.getModel().getSize() == 6);
		assertTrue(prim.getModel().getElementAt(5) == 11);
	}
	
	@Test
	public void testNext2() {
		for(int i = 0; i < 45; i++) {
			prim.getModel().next();
		}
		
		assertTrue(prim.getModel().getSize() == 46);
		assertTrue(prim.getModel().getElementAt(45) == 197);
	}
	
	@Test
	public void testNext3() {
		for(int i = 0; i < 222; i++) {
			prim.getModel().next();
		}
		
		assertTrue(prim.getModel().getSize() == 223);
		assertTrue(prim.getModel().getElementAt(222) == 1399);
	}

}
