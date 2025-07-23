package app6.src.ast;

import app6.src.lexical.AnalLex;
import app6.src.lexical.Terminal;

import java.util.List;
import static app6.src.lexical.Operateurs.*;
import static app6.src.lexical.TerminalType.*;

/**
 * Parseur à descente récursive pour construire un arbre syntaxique
 * Grammaire:
 * P = {
 *   E → T[+E|-E]
 *   T → F[*T|/T]
 *   F → (E)
 *   F → id
 * }
 */
public class RecursiveDescentParser {
    private final AnalLex parser;
    private Terminal currentToken;

    public RecursiveDescentParser(AnalLex parser) {
        this.parser = parser;
        this.currentToken = parser.prochainTerminal(); // Initialiser le premier token
    }

    /**
     * Avance au token suivant
     */
    private void advance() {
        if (parser.resteTerminal()) {
            currentToken = parser.prochainTerminal();
        } else {
            currentToken = null;
        }
    }

    /**
     * Vérifie si le token actuel correspond au type attendu
     */
    private boolean match(String expectedValue) {
        return currentToken != null &&
                currentToken.toString().equals(expectedValue);
    }

    /**
     * Consomme un token spécifique ou lève une exception
     */
    private void consume(String expectedValue) {
        if (!match(expectedValue)) {
            throw new RuntimeException("Token attendu: " + expectedValue +
                    ", trouvé: " + (currentToken != null ? currentToken.type : "EOF"));
        }
        advance();
    }

    /**
     * Point d'entrée principal du parseur
     * P → E
     */
    public ElemAST parse() {
        if (!parser.resteTerminal()) {
            return null;
        }
        ElemAST result = parseExpression();

        // Vérifier qu'on a consommé tous les tokens
        if (currentToken != null) {
            throw new RuntimeException("Tokens inattendus après la fin de l'expression: " + currentToken);
        }

        return result;
    }

    /**
     * Parse une expression
     * E → T[+E|-E]
     */
    private ElemAST parseExpression() {
        ElemAST left = parseTerm();

        // Gestion des opérateurs + et -
        if (currentToken != null && currentToken.type == OPERATEUR) {
            if (match(PLUS) || match(MOINS)) {
                ElemAST operator = new NoeudAST(currentToken);
                advance();

                ElemAST right = parseExpression(); // Récursion à droite

                // Construction du nœud opérateur
                operator.setGauche(left);
                operator.setDroite(right);
                return operator;
            }
        }

        return left;
    }

    /**
     * Parse un terme
     * T → F[*T|/T]
     */
    private ElemAST parseTerm() {
        ElemAST left = parseFactor();

        // Gestion des opérateurs * et /
        if (currentToken != null && currentToken.type == OPERATEUR) {
            if (match(MULTIPLICATION) || match(DIVISION)) {
                ElemAST operator = new NoeudAST(currentToken);
                advance();

                ElemAST right = parseTerm(); // Récursion à droite

                // Construction du nœud opérateur
                operator.setGauche(left);
                operator.setDroite(right);
                return operator;
            }
        }

        return left;
    }

    /**
     * Parse un facteur
     * F → (E) | id
     */
    private ElemAST parseFactor() {
        // Cas F → (E)
        if (currentToken != null && match("(")) {
            consume("("); // Consomme '('
            ElemAST expr = parseExpression(); // Parse l'expression entre parenthèses
            consume(")"); // Consomme ')'
            return expr;
        }

        // Cas F → id (opérande)
        if (currentToken != null && currentToken.type == OPERANDE) {
            ElemAST operand = new NoeudAST(currentToken);
            advance();
            return operand;
        }

        // Erreur si aucun cas ne correspond
        throw new RuntimeException("Facteur attendu, trouvé: " +
                (currentToken != null ? currentToken : "EOF"));
    }

    /**
     * Méthode utilitaire pour afficher l'arbre (parcours pré-ordre)
     */
    public void printTree(ElemAST node, String prefix, boolean isLast) {
        if (node == null) return;

        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.terminal);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        if (node.getDroite() != null) {
            printTree(node.getDroite(), childPrefix, node.getGauche() == null);
        }
        if (node.getGauche() != null) {
            printTree(node.getGauche(), childPrefix, true);
        }
    }

    /**
     * Version simplifiée pour remplacer SyntaxTreeBuilder
     */
    public static class SyntaxTreeBuilder {
        public static ElemAST buildTree(AnalLex lexParser) {
            RecursiveDescentParser parser = new RecursiveDescentParser(lexParser);
            return parser.parse();
        }
    }
}