/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbre_implementation;
import javafx.scene.paint.Color;

/**
 *
 * @author Ayoub MOUSTAID
 */

public class Noeud {

    private Color couleur;
    private Comparable info;
    private Noeud gauche;
    private Noeud droit;
    private Noeud parent;

    public Noeud(Comparable o) {
        couleur = Color.BLACK;
        info = o;
        gauche = droit = parent = null;
    }

    public Noeud(Comparable o, Color c, Noeud g, Noeud d, Noeud p) {
        couleur = c;
        info = o;
        gauche = g;
        droit = d;
        parent = p;
    }

    public boolean isSentinelle() {
        return this == ArbreRN.sentinelle;
    }

    public int getHauteur() {
        if (isSentinelle()) {
            return 0;
        } else {
            return 1 + Math.max(gauche.getHauteur(), droit.getHauteur());
        }
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public Comparable getInfo() {
        return info;
    }

    public void setInfo(Comparable info) {
        this.info = info;
    }

    public Noeud getGauche() {
        return gauche;
    }

    public void setGauche(Noeud gauche) {
        this.gauche = gauche;
    }

    public Noeud getDroit() {
        return droit;
    }

    public void setDroit(Noeud droit) {
        this.droit = droit;
    }

    public Noeud getParent() {
        return parent;
    }

    public void setParent(Noeud parent) {
        this.parent = parent;
    }
    
}

