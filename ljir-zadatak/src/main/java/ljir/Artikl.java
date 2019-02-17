package ljir;

import java.awt.Color;

public class Artikl {
	
	private String ime;
	private int broj;
	private Color boja;
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public int getBroj() {
		return broj;
	}
	public void setBroj(int broj) {
		if(broj <= 0) {
			throw new IllegalArgumentException("Number is less than 0!");
		}
		this.broj = broj;
	}
	public Color getBoja() {
		return boja;
	}
	public void setBoja(Color boja) {
		this.boja = boja;
	}
	public Artikl(String ime, int broj) {
		this.ime = ime;
		if(broj <= 0) {
			throw new IllegalArgumentException("Number is less than 0!");
		}
		this.broj = broj;
		
		int r = (int)(Math.random() * 255); 
		int g = (int)(Math.random() * 255); 
		int b = (int)(Math.random() * 255);
		this.boja = new Color(r, g, b);
	}
	
	@Override
	public String toString() {
		return ime + "    " + broj + "    " + boja.getRGB();
	}

	
}
