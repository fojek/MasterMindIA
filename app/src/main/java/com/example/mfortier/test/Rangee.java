package com.example.mfortier.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class Rangee {

	public Pin getPin(int i) {
		return sockets.get(i);
	}

	public enum enumResultat {
		NOIR, BLANC
	};

	ArrayList<Pin> sockets;
	Resultat resultat;
	final static int NUMSOCKRANGEE = 4;

	Rangee(ArrayList<Pin> pins) {
		if (pins.size() != NUMSOCKRANGEE) {
			throw new IllegalArgumentException("Une rangee doit contenir exactement 4 pins");
		}
		
		resultat = null;

		sockets = new ArrayList<Pin>();

		for (Pin p : pins) {
			sockets.add(p);
		}
	}

	public static Rangee randomRangee() {

		ArrayList<Pin> s = new ArrayList<Pin>();

		for (int i = 0; i < NUMSOCKRANGEE; ++i) {
			s.add(new Pin());
		}

		return new Rangee(s);
	}

	public Resultat verifier(Rangee hypothese) {

		hypothese.resultat = new Resultat();

		ArrayList<Integer> posVerifiees1 = new ArrayList<Integer>();
		ArrayList<Integer> posVerifiees2 = new ArrayList<Integer>();

		// V�rifie la pr�sence de Noirs
		for (int i = 0; i < hypothese.sockets.size(); i++) {
			if (hypothese.sockets.get(i).equals(sockets.get(i))) {
				hypothese.resultat.addNoir();
				posVerifiees1.add(i);
				posVerifiees2.add(i);
				continue;
			}
		}

		// V�rifie la pr�sence de Blancs
		for (int i = 0; i < sockets.size(); i++) {
			if (posVerifiees1.contains(i)) {
				continue;
			}
			for (int j = 0; j < hypothese.sockets.size(); j++) {
				if (posVerifiees2.contains(j)) {
					continue;
				}
				if (sockets.get(i).equals(hypothese.sockets.get(j))) {
					posVerifiees1.add(i);
					posVerifiees2.add(j);
					hypothese.resultat.addBlanc();
					break;
				}
			}
		}
		return hypothese.resultat;
	}

	@Override
	public String toString() {
		String res = "\t\t";
		for (Pin p : sockets) {
			res += p.toString() + ", ";
		}
		if(resultat != null)
		    res += resultat.toString();
		return res + "\n";
	}

	public static ArrayList<Rangee> toutesRangees() {
		ArrayList<Rangee> all = new ArrayList<Rangee>();

		for (int i = 0; i < Pin.Couleurs.values().length; i++) {
			for (int j = 0; j < Pin.Couleurs.values().length; j++) {
				for (int k = 0; k < Pin.Couleurs.values().length; k++) {
					for (int l = 0; l < Pin.Couleurs.values().length; l++) {
						ArrayList<Pin> pins = new ArrayList<Pin>();

						pins.add(new Pin(Pin.Couleurs.values()[i]));
						pins.add(new Pin(Pin.Couleurs.values()[j]));
						pins.add(new Pin(Pin.Couleurs.values()[k]));
						pins.add(new Pin(Pin.Couleurs.values()[l]));

						all.add(new Rangee(pins));
					}
				}
			}
		}

		return all;
	}

	public void setResultat(Resultat inputResultat) {
		resultat = inputResultat;
	}
}
