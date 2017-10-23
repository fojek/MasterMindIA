package com.example.mfortier.test;

import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Random;

public class Jeu {

	ArrayList<Rangee> coupsPrecedents;
	ArrayList<Rangee> hypothesesRestantes;

    // UI
    private View.OnClickListener genererListener;
    private View.OnClickListener recommencerListener;
    private PinView[][] pins;
    private ResultButton[][] buttons;
    MainActivity mainActivity;

    public Jeu() {
        initJeu();
	}

	private void initJeu(){
        coupsPrecedents = new ArrayList<Rangee>();
        hypothesesRestantes = Rangee.toutesRangees();
    }

	public Rangee genererCoup() {
		Rangee hypothese = hypothesesRestantes.remove((new Random()).nextInt(hypothesesRestantes.size()));
		coupsPrecedents.add(hypothese);
		return hypothese;
	}

	public String printCoupsMemoire() {
		String res = "Coups en memoire : (" + coupsPrecedents.size() + ")\n";
		for (Rangee coup : coupsPrecedents) {
			res += coup.toString();
		}
		return res;
	}

	public String printCoupsRestants() {
		return "Coups possibles restants : (" + hypothesesRestantes.size() + ")\n";
	}

	public void updateCoupsPossibles() {
		ArrayList<Rangee> temp = new ArrayList<Rangee>();

		for (Rangee hypothese : hypothesesRestantes) {
			for (Rangee coup : coupsPrecedents) {

				coup.verifier(hypothese);

				if (!hypothese.resultat.equals(coup.resultat)) {
					temp.add(hypothese);
					break;
				}
			}
		}
		
		for(Rangee coup: temp){
			hypothesesRestantes.remove(coup);
		}
		
		System.out.println("Hypotheses restantes : " + hypothesesRestantes.size());
	}

	public boolean solutionTrouvee() {
		if(hypothesesRestantes.size() == 0)
			return true;
		return false;
	}

	public String printSolution() {
		if(!solutionTrouvee())
			return "Solution pas encore trouvee! " + hypothesesRestantes.size() + " " + coupsPrecedents.size();
		else if(hypothesesRestantes.size() == 0)
			return "\nSolution :\n" + coupsPrecedents.get(coupsPrecedents.size()-1).toString() + "Trouvee en " + coupsPrecedents.size() + " coup(s) !";
		return "\nSolution :\n" + hypothesesRestantes.get(hypothesesRestantes.size()-1).toString() + "Trouvee en " + coupsPrecedents.size() + " coup(s) !";
	}

	public void resultatDernierCoup(Resultat inputResultat) {
		coupsPrecedents.get(coupsPrecedents.size()-1).setResultat(inputResultat);
	}

	public void initUI(MainActivity pMainActivity) {
        mainActivity = pMainActivity;
        // Génération des pins (4 rangées)
        pins = new PinView[8][4];

        for(int i=0; i<pins.length; ++i) {
            for(int j=0; j<pins[i].length; ++j){
                pins[i][j] = new PinView(mainActivity, new Pin());
                pins[i][j].setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                pins[i][j].setVisibility(View.INVISIBLE);
            }
        }

        // Pour les noirs / blancs
        buttons = new ResultButton[8][2];
        for(int i=0; i<buttons.length; ++i) {
            buttons[i][0] = ResultButton.NoirResultButton(mainActivity);
            buttons[i][0].setLayoutParams(new TableRow.LayoutParams(100, 100));
            buttons[i][0].setEnabled(false);
            buttons[i][0].setVisibility(View.INVISIBLE);


            buttons[i][1] = ResultButton.BlancResultButton(mainActivity);
            buttons[i][1].setLayoutParams(new TableRow.LayoutParams(100, 100));
            buttons[i][1].setEnabled(false);
            buttons[i][1].setVisibility(View.INVISIBLE);
        }

        // Génération de la table 4x8 qui va contenir les pins
        TableLayout myLayout = new TableLayout(mainActivity);

        ArrayList<TableRow> rows = new ArrayList<TableRow>();

        for(int i=0; i<8; ++i) {
            TableRow row = new TableRow(mainActivity);
            TableRow.LayoutParams layouparams1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
            layouparams1.span = 6;
            row.setLayoutParams(layouparams1);
            row.setGravity(Gravity.CENTER_HORIZONTAL);

            for(int j=0; j<4; j++){
                row.addView(pins[i][j]);
            }

            // Noir et Blanc
            row.addView(buttons[i][0]);
            row.addView(buttons[i][1]);

            rows.add(row);
        }

        for(TableRow row : rows){
            myLayout.addView(row);
        }

        Button genererBouton = new Button(mainActivity);
        genererBouton.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 200));
        genererBouton.setText("Générer");

        Button resetBouton = new Button(mainActivity);
        resetBouton.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 100));
        resetBouton.setText("Recommencer");

        myLayout.addView(genererBouton);
        myLayout.addView(resetBouton);

        initListener();
        genererBouton.setOnClickListener(genererListener);
        resetBouton.setOnClickListener(recommencerListener);

        mainActivity.setContentView(myLayout);

        // Génère le coup initial
        setRangeeUI();
	}

	public Animation rotationAnim(){
        Animation anim = new RotateAnimation(0,360, 50,50);
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        return anim;
    }

    public Animation victoireAnim(){
        Animation anim = new RotateAnimation(360,0, 50,50);
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }

    public Animation erreurAnim(){
        Animation anim = new ScaleAnimation(1, 0.9f, 1, 0.9f);
        //anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(100);
        anim.setRepeatCount(3);
        return anim;
    }


    public void setRangeeUI(){
        genererCoup();
        for(int i=0; i<pins[coupsPrecedents.size()-1].length; ++i){
            pins[coupsPrecedents.size()-1][i].setPin(coupsPrecedents.get(coupsPrecedents.size()-1).getPin(i));
            pins[coupsPrecedents.size()-1][i].setVisibility(View.VISIBLE);
            pins[coupsPrecedents.size()-1][i].startAnimation(rotationAnim());
        }

        if(coupsPrecedents.size() > 1){
            for(ResultButton button : buttons[coupsPrecedents.size()-2]){
                button.setEnabled(false);
            }
        }

        for(ResultButton button : buttons[coupsPrecedents.size()-1]){
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
    }

    public void checkVictoire(){
        if(solutionTrouvee() == true){
            for(int i=0; i<pins[coupsPrecedents.size()-1].length; ++i) {
                pins[coupsPrecedents.size() - 1][i].startAnimation(victoireAnim());
            }
            buttons[coupsPrecedents.size() - 1][0].setVisibility(View.INVISIBLE);
            buttons[coupsPrecedents.size() - 1][1].setVisibility(View.INVISIBLE);
        }
        else
        {
            setRangeeUI();
        }
    }

    public int getNoirs(){ return buttons[coupsPrecedents.size()-1][0].getCompte(); }
    public int getBlancs(){ return buttons[coupsPrecedents.size()-1][1].getCompte(); }

    public void setResultat(){
        resultatDernierCoup(new Resultat(getNoirs(), getBlancs()));
        updateCoupsPossibles();
    }

    private void initListener(){
        genererListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getNoirs()+getBlancs() > Rangee.NUMSOCKRANGEE){
                    buttons[coupsPrecedents.size()-1][0].startAnimation(erreurAnim());
                    buttons[coupsPrecedents.size()-1][1].startAnimation(erreurAnim());
                }
                else{
                    setResultat();
                    checkVictoire();
                }
            }
        };
        recommencerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.recreate();
            }
        };
    }
}