package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

public class CalcLayoutTest {

	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentObject() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), new RCPosition(0, 2));
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentObject2() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), new RCPosition(2, -2));
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentObject3() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), new RCPosition(1, 3));
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentObject4() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), new RCPosition (6, 2));
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentObject5() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), new RCPosition(2, 8));
	}

	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentString() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), "-2,2");
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentString2() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), "0,2");
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentString3() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), "1,2");
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddLayoutComponentComponentString4() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), "3,8");
	}
	
	@Test (expected = CalcLayoutException.class)
	public void testAddMultipleLayoutComponentComponentString() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("a"), "2,3");
		p.add(new JLabel("a"), "2,3");
	}
	
	@Test
	public void testPrefferedLayoutSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertTrue(dim.equals(new Dimension(152, 158)));
	}
	
	@Test
	public void testPrefferedLayoutSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertTrue(dim.equals(new Dimension(152, 158)));
	}

}
