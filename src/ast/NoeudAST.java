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
                    case PLUS ->            gauche.EvalAST() + droite.EvalAST();
                    case MOINS ->           gauche.EvalAST() - droite.EvalAST();
                    case MULTIPLICATION ->  gauche.EvalAST() * droite.EvalAST();
                    case DIVISION -> {
                        if (droite.EvalAST() == 0) {
                            ErreurEvalAST("Division par zéro");
                        }
                        yield gauche.EvalAST() / droite.EvalAST();
                    }
                    default -> {
                        ErreurEvalAST("Opérateur inconnu : " + terminal);
                        yield 0;
                    }
                };
            case NOMBRE:
                return Integer.parseInt(terminal.toString());

            case VARIABLE:
                return 0; // Pour l'instant, on ne gère pas les variables

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
        return terminal.toString() + "\n" +
                (gauche != null ? "Gauche: " + gauche.toString() + "\n" : "") +
                (droite != null ? "Droite: " + droite.toString() + "\n" : "");
    }

    private Boolean estFeuille() {
        return gauche == null && droite == null;
    }
}


