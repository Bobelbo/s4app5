package app6.src.ast;

import app6.src.lexical.Terminal;

/**
 * Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {

    /**
     * Évaluation d'AST
     */
    public abstract int EvalAST();

    /**
     * ErreurEvalAST() envoie un message d'erreur lors de la construction d'AST
     */
    public void ErreurEvalAST(String s) {
        System.out.println("Erreur d'évaluation de l'AST : " + s);
        System.exit(1); // Arrêt du programme en cas d'erreur d'évaluation
    }
}
