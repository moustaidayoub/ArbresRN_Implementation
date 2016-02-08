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

public class ArbreRN {

    public static Noeud sentinelle;

    static {
        ArbreRN.sentinelle = new Noeud(null, Color.BLACK, null, null, null);
    }
    private Noeud racine, noeudAjoute;

    public ArbreRN() {
        racine = ArbreRN.sentinelle;
    }

    public void ajout(Comparable o) {
        racine = ajout(o, racine, null);
        reOrganiser(noeudAjoute);
    }

    private Noeud ajout(Comparable o, Noeud r, Noeud p) {
        // p est le parent de r 
        if (r.isSentinelle()) {
            r = noeudAjoute = new Noeud(o, Color.RED, r, r, p);
        } else if (o.compareTo(r.getInfo()) < 0) {
            r.setGauche(ajout(o, r.getGauche(), r));
        } else {
            r.setDroit(ajout(o, r.getDroit(), r));
        }
        return r;
    }

    private void rotationGauche(Noeud n) {
        Noeud y = n.getDroit();
        n.setDroit(y.getGauche());
        if (!y.getGauche().isSentinelle()) {
            y.getGauche().setParent(n);
        }
        y.setParent(n.getParent());
        if (n.getParent() == null) {
            racine = y;
        } else if (n.getParent().getGauche() == n) {
            n.getParent().setGauche(y);
        } else {
            n.getParent().setDroit(y);
        }
        y.setGauche(n);
        n.setParent(y);
    }

    private void rotationDroite(Noeud n) {
        Noeud y = n.getGauche();
        n.setGauche(y.getDroit());
        if (!y.getDroit().isSentinelle()) {
            y.getDroit().setParent(n);
        }
        y.setParent(n.getParent());
        if (n.getParent() == null) {
            racine = y;
        } else if (n.getParent().getDroit() == n) {
            n.getParent().setDroit(y);
        } else {
            n.getParent().setGauche(y);
        }
        y.setDroit(n);
        n.setParent(y);
    }

    private void reOrganiser(Noeud n) {
        while (n != racine && n.getParent().getCouleur() == Color.RED) {
            if (n.getParent() == n.getParent().getParent().getGauche()) {
                Noeud y = n.getParent().getParent().getDroit();
                if (y.getCouleur() == Color.RED) {
                    n.getParent().setCouleur(Color.BLACK);
                    y.setCouleur(Color.BLACK);
                    n.getParent().getParent().setCouleur(Color.RED);
                    n = n.getParent().getParent();
                } else {
                    if (n == n.getParent().getDroit()) {
                        n = n.getParent();
                        rotationGauche(n);
                    }
                    n.getParent().setCouleur(Color.BLACK);
                    n.getParent().getParent().setCouleur(Color.RED);
                    rotationDroite(n.getParent().getParent());
                }
            } else {
                Noeud y = n.getParent().getParent().getGauche();
                if (y.getCouleur() == Color.RED) {
                    n.getParent().setCouleur(Color.BLACK);
                    y.setCouleur(Color.BLACK);
                    n.getParent().getParent().setCouleur(Color.RED);
                    n = n.getParent().getParent();
                } else {
                    if (noeudAjoute == n.getParent().getGauche()) {
                        n = n.getParent();
                        rotationDroite(n);
                    }
                    n.getParent().setCouleur(Color.BLACK);
                    n.getParent().getParent().setCouleur(Color.RED);
                    rotationGauche(n.getParent().getParent());
                }
            }
        }
        racine.setCouleur(Color.BLACK);
        // la racine est toujours noire
    }

    public void supprimer(Comparable o) {
        supprimer(racine, o);
    }

    private Noeud supprimer(Noeud r, Comparable o) {
        if (r.isSentinelle()) {
            return r; // Pas trouvé
        }
        if (o.compareTo(r.getInfo()) == 0) {
            r = supprimer(r);
        } else if (o.compareTo(r.getInfo()) < 0) {
            supprimer(r.getGauche(), o);
        } else {
            supprimer(r.getDroit(), o);
        }
        return r;
    }

    private Noeud supprimer(Noeud z) {
        Noeud y, x;
        if (z.getGauche().isSentinelle() || z.getDroit().isSentinelle()) {
            y = z;
        } else {
            y = arbreSuccesseur(z);
        }
        if (!y.getGauche().isSentinelle()) {
            x = y.getGauche();
        } else {
            x = y.getDroit();
        }
        x.setParent(y.getParent());
        if (y.getParent() == null) {
            racine = x;
        } else if (y == y.getParent().getGauche()) {
            y.getParent().setGauche(x);
        } else {
            y.getParent().setDroit(x);
        }
        if (y != z) {
            z.setInfo(y.getInfo());
        }
        if (y.getCouleur() == Color.BLACK) {
            reOrganiserSuppression(x);
        }
        return y;
    }

    private Noeud arbreSuccesseur(Noeud x) {
        if (!x.getDroit().isSentinelle()) {
            return arbreMinimum(x.getDroit());
        }
        Noeud y = x.getParent();
        while (!y.isSentinelle() && x == y.getDroit()) {
            x = y;
            y = x.getParent();
        }
        return y;
    }

    private Noeud arbreMinimum(Noeud x) {
        while (!x.getGauche().isSentinelle()) {
            x = x.getGauche();
        }
        return x;
    }

    private void reOrganiserSuppression(Noeud n) {
        // re organisation de l'arbre, en remontant vers la racine
        while (n != racine && n.getCouleur() == Color.BLACK) {
            if (n == n.getParent().getGauche()) {
                Noeud y = n.getParent().getDroit();
                if (y.getCouleur() == Color.RED) {
                    y.setCouleur(Color.BLACK);
                    n.getParent().setCouleur(Color.RED);
                    rotationGauche(n.getParent());
                    y = n.getParent().getDroit();
                }
                if (y.getGauche().getCouleur() == Color.BLACK && y.getDroit().getCouleur() == Color.BLACK) {
                    y.setCouleur(Color.RED);
                    n = n.getParent();
                } else {
                    if (y.getDroit().getCouleur() == Color.BLACK) {
                        y.getGauche().setCouleur(Color.BLACK);
                        y.setCouleur(Color.RED);
                        rotationDroite(y);
                        y = n.getParent().getDroit();
                    }
                    y.setCouleur(n.getParent().getCouleur());
                    n.getParent().setCouleur(Color.BLACK);
                    y.getDroit().setCouleur(Color.BLACK);
                    rotationGauche(n.getParent());
                    break;
                }
            } else {
                Noeud y = n.getParent().getGauche();
                if (y.getCouleur() == Color.RED) {
                    y.setCouleur(Color.BLACK);
                    n.getParent().setCouleur(Color.RED);
                    rotationDroite(n.getParent());
                    y = n.getParent().getGauche();
                }
                if (y.getDroit().getCouleur() == Color.BLACK && y.getGauche().getCouleur() == Color.BLACK) {
                    y.setCouleur(Color.RED);
                    n = n.getParent();
                } else {
                    if (y.getGauche().getCouleur() == Color.BLACK) {
                        y.getDroit().setCouleur(Color.BLACK);
                        y.setCouleur(Color.RED);
                        rotationGauche(y);
                        y = n.getParent().getGauche();
                    }
                    y.setCouleur(n.getParent().getCouleur());
                    n.getParent().setCouleur(Color.BLACK);
                    y.getGauche().setCouleur(Color.BLACK);
                    rotationDroite(n.getParent());
                    break;
                }
            }
        }
        n.setCouleur(Color.BLACK);
    }

    public Noeud getRacine() {
        return racine;
    }

    public void setRacine(Noeud racine) {
        this.racine = racine;
    }

    public Noeud getNoeudAjoute() {
        return noeudAjoute;
    }

    public void setNoeudAjoute(Noeud noeudAjoute) {
        this.noeudAjoute = noeudAjoute;
    }
    
}
 