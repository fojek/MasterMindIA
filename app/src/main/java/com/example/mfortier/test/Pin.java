package com.example.mfortier.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Pin {

	// Enum avec possibilit� de random
	public static enum Couleurs {
		ROUGE, BLANC, NOIR, JAUNE, BLEU, VERT;

		private static final Couleurs[] VALUES = values();
		private static final int SIZE = VALUES.length;
		private static final Random RANDOM = new Random();

		public static Couleurs getCouleurRand() {
			return VALUES[RANDOM.nextInt(SIZE)];
		}
	}

	Couleurs couleur;

	public Pin(Couleurs c) {
		couleur = c;
	}

	public Pin() {
		couleur = Couleurs.getCouleurRand();
	}

	@Override
    public String toString() {
        switch (couleur) {
            case ROUGE:
                return "Rouge";
            case BLANC:
                return "Blanc";
            case NOIR:
                return "Noir";
            case JAUNE:
                return "Jaune";
            case BLEU:
                return "Bleu";
            case VERT:
                return "Vert";
            default:
                throw new IllegalArgumentException("Cette couleur n'est pas g�r�e!");
        }
    }

    public Paint toPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        switch (couleur) {
            case ROUGE:
                paint.setColor(Color.RED);
                break;
            case BLANC:
                paint.setColor(Color.WHITE);
                break;
            case NOIR:
                paint.setColor(Color.DKGRAY);
                break;
            case JAUNE:
                paint.setColor(Color.YELLOW);
                break;
            case BLEU:
                paint.setColor(Color.BLUE);
                break;
            case VERT:
                paint.setColor(Color.GREEN);
                break;
            default:
                throw new IllegalArgumentException("Cette couleur n'est pas g�r�e!");
        }
        return paint;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pin other = (Pin) obj;
		if (couleur != other.couleur) {
			return false;
		}
		return true;
	}
}
