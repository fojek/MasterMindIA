package com.example.mfortier.test;

public class Resultat {

	int noirs;
	int blancs;
	
	public Resultat(){
		noirs = 0;
		blancs = 0;
	}
	
	public Resultat(int pNoirs, int pBlancs) {
		noirs = pNoirs;
		blancs = pBlancs;
	}

	static public Resultat inputResultat(String input) {
		String inputs[] = input.split(" ");
		
		return new Resultat(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
	}

	public void addNoir(){
		++noirs;
	}
	
	public void addBlanc(){
		++blancs;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resultat other = (Resultat) obj;
		if (blancs != other.blancs)
			return false;
		if (noirs != other.noirs)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "Noirs : " + noirs + ", Blancs : " + blancs;
	}
}
