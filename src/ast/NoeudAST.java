package app6.src.ast;

import app6.src.lexical.Terminal;
import app6.src.lexical.TerminalType;

import static app6.src.lexical.Operateurs.*;

/**
 * Classe représentant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

    /**
     * Constructeur pour l'initialisation d'attributs
     *
     * @param terminal
     */
    public NoeudAST(Terminal terminal) {
        super(terminal);
    }

    /**
     * Évaluation de noeud d'AST
     */
    public int EvalAST() {
        switch (terminal.type) {
            case OPERATEUR:
                if (estFeuille()) {
                    ErreurEvalAST("Opérateur sans opérandes");
                }
                return switch (terminal.toString()) {
                    case PLUS ->            getGauche().EvalAST() + getDroite().EvalAST();
                    case MOINS ->           getGauche().EvalAST() - getDroite().EvalAST();
                    case MULTIPLICATION ->  getGauche().EvalAST() * getDroite().EvalAST();
                    case DIVISION -> {
                        if (getDroite().EvalAST() == 0) {
                            ErreurEvalAST("Division par zéro");
                        }
                        yield getGauche().EvalAST() / getDroite().EvalAST();
                    }
                    default -> {
                        ErreurEvalAST("Opérateur inconnu : " + terminal);
                        yield 0;
                    }
                };
            case NOMBRE:
                return Integer.parseInt(terminal.toString());

            case VARIABLE:
                ErreurEvalAST("Impossible d'évaluer une variable"); // Pour l'instant, on ne gère pas les variables

            default:
                ErreurEvalAST("Type de terminal inconnu : " + terminal);
                return 0; // Ne devrait jamais arriver
        }
    }

    @Override
    public TerminalType getType() {
        return this.terminal.type;
    }

    /**
     * Lecture de noeud d'AST
     */
    @Override
    public String toString() {
        return  (this.getGauche() != null ? this.getGauche().toString() : "") +
                this.getStringProfondeur() + terminal.toString() + "\n" +
                (this.getDroite() != null ? this.getDroite().toString() : "");
    }
}


