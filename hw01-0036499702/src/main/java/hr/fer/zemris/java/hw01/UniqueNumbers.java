package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred UniqueNumbers sluzi za opisivanje uredenog binarnog stabla.
 * 
 * @author Alex
 *
 */

public class UniqueNumbers {
	
	
	/**
	 * Metoda sluzi za dodavanje cvorova u uredeno binarno stablo.
	 * 
	 * @param glava pocetak binarnog stabla
	 * @param value broj koji zelimo dodati u stablo
	 * @return TreeNode koji je pocetak binarnog stabla u kojem je dodan broj
	 */
	
	public static TreeNode addNode(TreeNode glava, int value) {
		
		if(glava == null) {
			TreeNode newNode = new TreeNode();
			newNode.value = value;
			newNode.left = null;
			newNode.right = null;
			return newNode;
		}
		
		if(glava.value > value) {
			glava.left = addNode(glava.left, value);
		} else if (glava.value < value){
			glava.right = addNode(glava.right, value);
		}
		
		return glava;
	}
	
	
	/**
	 * Metoda vraca velicinu binarnog stabla
	 * 
	 * @param glava pocetak binarnog stabla
	 * @return cijeli broj koji opisuje velicinu binarnog stabla
	 */
	
	public static int treeSize(TreeNode glava) {
		
		if(glava == null) {
			return 0;
		}
		
		int left = treeSize(glava.left);
		int right = treeSize(glava.right);
		
		return left + right + 1;
		
	}
	
	
	/**
	 * Metoda koja provjerava sadrzi li binarno stablu zadanu vrijednost.
	 * 
	 * @param glava pocetak biinarnog stabla
	 * @param value broj za koji zelimo provjeriti je li u binarnom stablu
	 * @return true ako je broj u stablu, inace false
	 */
	
	public static boolean containsValue(TreeNode glava, int value) {
		
		if (glava == null) {
			return false;
		} else if (glava.value == value) {
			return true;
		} else if (glava.value > value) {
			return containsValue(glava.left, value);
		} else {
			return containsValue(glava.right, value);
		}
	}
	
	
	/**
	 * Metoda ispisuje sve brojeve u stablu od najmanjeg prema najvecem
	 * 
	 * @param glava pocetak binarnog stabla
	 */
	
	public static void outputUp(TreeNode glava) {
		
		if(glava == null) {
			return;
		}
		
		outputUp(glava.left);
		System.out.print(glava.value + " ");
		outputUp(glava.right);
	}
	
	
	/**
	 * Metoda ispisuje sve brojeve u stablu od najveceg prema najmanjem.
	 * 
	 * @param glava pocetak binarnog stabla
	 */
	
	public static void outputDown(TreeNode glava) {
		
		if(glava == null) {
			return;
		}
		
		outputDown(glava.right);
		System.out.print(glava.value + " ");
		outputDown(glava.left);
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		
		while(true) {
			
			System.out.print("Unesite broj > ");
			String s = sc.next();
			int number;
			
			try {
				number = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				if (s.equals("kraj")) {
					
					System.out.print("Ispis od najmanjeg:");
					outputUp(glava);
					System.out.print("\nIspis od najvećeg:");
					outputDown(glava);
					break;
					
				} else {
					System.out.println("'" + s + "' nije cijeli broj.");
					continue;
				}
			}
			
			if(containsValue(glava, number)) {
				System.out.println("Broj već postoji. Preskačem.");
				continue;
			} else {
				glava = addNode(glava, number);
				System.out.println("Dodano.");
			}
		}
		
		sc.close();
	}
	
	
	/**
	 * Razred sluzi za opisivanje cvorova binarnog stabla
	 * 
	 * @author Alex
	 *
	 */
	
	static class TreeNode{
		TreeNode left;
		TreeNode right;
		int value;
	}
}
