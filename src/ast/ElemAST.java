package app6.src.ast;

import app6.src.lexical.Terminal;
import app6.src.lexical.TerminalType;

/**
 * Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {
    // Attributs
    public ElemAST gauche;    // Sous-arbre gauche
    public ElemAST droite;    // Sous-arbre dro
    public ElemAST parent;    // Parent de l'élément AST
    protected Terminal terminal;// Opérateur de l'AST

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
}
