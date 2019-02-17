package hr.fer.zemris.java.hw07.shell.commands;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandsUtilTest {

	@Test
	public void testGetArgs() {
		String[] args = CommandsUtil.getArgs("hr/fer/zemris/java/src/main/java");
		assertTrue(args.length == 1);
		assertTrue(args[0].equals("hr/fer/zemris/java/src/main/java"));
	}
	
	@Test
	public void testGetArgs2() {
		String[] args = CommandsUtil.getArgs("\"hr/fer/zemris/java/src/main/java\"");
		assertTrue(args.length == 1);
		assertTrue(args[0].equals("hr/fer/zemris/java/src/main/java"));
	}
	
	@Test
	public void testGetTwoArgs() {
		String[] args = CommandsUtil.getArgs("hr/fer zemris/java");
		assertTrue(args.length == 2);
		assertTrue(args[0].equals("hr/fer"));
		assertTrue(args[1].equals("zemris/java"));
	}
	
	@Test
	public void testGetTwoArgs2() {
		String[] args = CommandsUtil.getArgs("\"hr/fer\" zemris/java");
		assertTrue(args.length == 2);
		assertTrue(args[0].equals("hr/fer"));
		assertTrue(args[1].equals("zemris/java"));
	}
	
	@Test
	public void testGetTwoArgs3() {
		String[] args = CommandsUtil.getArgs("\"hr/fer\" \"zemris/java\"");
		assertTrue(args.length == 2);
		assertTrue(args[0].equals("hr/fer"));
		assertTrue(args[1].equals("zemris/java"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetTwoArgsEx() {
		String[] args = CommandsUtil.getArgs("\"hr/fer\" \"zemris/java");
		assertTrue(args.length == 2);
		assertTrue(args[0].equals("hr/fer"));
		assertTrue(args[1].equals("zemris/java"));
	}
	
	@Test
	public void testEscape() {
		String[] args = CommandsUtil.getArgs("\"hr\\\"/fer\" \"zem\\\\ris/java\"");
		assertTrue(args.length == 2);
		assertTrue(args[0].equals("hr\"/fer"));
		assertTrue(args[1].equals("zem\\ris/java"));
	}

}
