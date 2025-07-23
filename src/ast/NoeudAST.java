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
            case OPERANDE:
                try {
                    // Si c'est un nombre, on le convertit en entier
                    return Integer.parseInt(terminal.toString());
                } catch (NumberFormatException e) {
                    ErreurEvalAST("Impossible d'évaluer une variable");
                }

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
        return  (this.getDroite() != null ? this.getDroite().toString() : "") +
                this.getStringProfondeur() + terminal.toString() + "\n" +
                (this.getGauche() != null ? this.getGauche().toString() : "");
    }
}


