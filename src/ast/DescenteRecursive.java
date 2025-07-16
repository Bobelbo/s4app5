package app6.src.ast;

import app6.src.filelib.Reader;
import app6.src.filelib.Writer;
import app6.src.lexical.AnalLex;
import app6.src.lexical.Terminal;

import static app6.src.lexical.TerminalType.*;

/**
 * Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {
    private final AnalLex analexical;

    /**
     * Constructeur de DescenteRecursive
     */
    public DescenteRecursive(String input) {
        this.analexical = new AnalLex(new Reader(input).toString()); // Initialisation du lecteur de fichier
    }

    /**
     * AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     * Elle retourne une référence sur la racine de l'AST construit
     */
    public ElemAST AnalSynt() {
        ElemAST racine = null;
        ElemAST prevOper = null; // Noeud actuel de l'AST
        ElemAST prevVal = null;

        while(this.analexical.resteTerminal()) {
            NoeudAST noeud = new NoeudAST(this.analexical.prochainTerminal()); // Création d'un nouveau noeud

            switch (noeud.getType()) {
                case OPERATEUR:
                    if (racine == null) {
                        racine = noeud; // Création de la racine si elle n'existe pas
                        racine.gauche = prevVal; // Le noeud de réserve devient le sous-arbre gauche de la racine
                        prevVal = null; // Réinitialisation de la réserve
                    } else {
                        noeud.gauche = prevOper.droite;
                        prevOper.droite = noeud; // Si le précédent est un opérateur, on ajoute le nouveau noeud comme sous-arbre droit
                    }
                    prevOper = noeud;
                    break;

                case NOMBRE, VARIABLE:
                    if (racine != null) {
                        if (prevOper.droite != null) {
                            ErreurSynt("Sous-arbre droit déjà occupé pour l'opérateur : " + prevOper);
                        }
                        prevOper.droite = noeud; // Ajout du noeud comme sous-arbre droit de l'opérateur précédent
                    }
                    prevVal = noeud;
                    break;

                default:
                    ErreurSynt("Type de terminal inconnu : " + noeud);

            }
        }

        return (racine == null) ? prevVal : racine;
    }

    /**
     * ErreurSynt() envoie un message d'erreur syntaxique
     */
    public void ErreurSynt(String s) {
        System.out.println("Erreur syntaxique : " + s);
        System.exit(1); // Arrêt du programme en cas d'erreur lexicale
    }


    // Méthode principale à lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) {

        String toWriteLect = "";
        String toWriteEval = "";

        System.out.println("Debut d'analyse syntaxique");

        if (args.length == 0) {
            args = new String[2];
            args[0] = "src/ExpArith.txt";
            args[1] = "src/ResultatSyntaxique.txt";
        }

        DescenteRecursive dr = new DescenteRecursive(args[0]);

        try {

            ElemAST RacineAST = dr.AnalSynt();

            toWriteLect += "Lecture de l'AST trouve : " + RacineAST.toString() + "\n";
            System.out.println(toWriteLect);

            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
            System.out.println(toWriteEval);

            Writer w = new Writer(args[1], toWriteLect + toWriteEval);              // Écriture de toWrite dans fichier args[1]

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();

            System.exit(51);

        }

        System.out.println("Analyse syntaxique terminee");

    }
}

