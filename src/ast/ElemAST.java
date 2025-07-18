package app6.src.ast;

import app6.src.lexical.Terminal;
import app6.src.lexical.TerminalType;

/**
 * Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {
    // Attributs
    private ElemAST gauche;      // Sous-arbre gauche
    private ElemAST droite;      // Sous-arbre dro
    private ElemAST parent;      // Parent de l'élément AST
    protected Terminal terminal; // Opérateur de l'AST

    public ElemAST getGauche() {
        return gauche;
    }

    public ElemAST getDroite() {
        return droite;
    }

    public ElemAST getParent() {
        return parent;
    }

    public void setGauche(ElemAST gauche) {
        this.gauche = gauche;
        if (gauche != null) {
            gauche.parent = this; // Met à jour le parent du sous-arbre gauche
        }
    }

    public void setDroite(ElemAST droite) {
        this.droite = droite;
        if (droite != null) {
            droite.parent = this; // Met à jour le parent du sous-arbre droit
        }
    }

    /**
     * Constructeur pour l'initialisation d'attributs
     */
    public ElemAST(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Évaluation d'AST
     */
    public abstract int EvalAST();

    /**
     * getType() retourne le type de l'élément AST
     */
    public abstract TerminalType getType();

    /**
     * ErreurEvalAST() envoie un message d'erreur lors de la construction d'AST
     */
    public void ErreurEvalAST(String s) {
        System.out.println("Erreur d'évaluation de l'AST : " + s);
        System.exit(1); // Arrêt du programme en cas d'erreur d'évaluation
    }

    public Boolean estFeuille() {
        return gauche == null && droite == null;
    }

    protected int profondeur() {
        return parent == null ? 0 : parent.profondeur() + 1;
    }

    protected String getStringProfondeur() {
        return "=".repeat(Math.max(0, profondeur()));
    }
}
