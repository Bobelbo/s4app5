package app6.src.ast;

import java.util.Objects;

import static app6.src.lexical.Operateurs.*;

/**
 * Builds a single tree from nodes given
 */
public class SyntaxTreeBuilder {
    private ElemAST racine = null;
    private ElemAST prevOper = null;
    private ElemAST prevVal = null;

    public ElemAST buildTree(ElemAST node) {
        switch (node.getType()) {
            case OPERATEUR:
                if (racine == null) {
                    racine = node; // Création de la racine si elle n'existe pas
                    racine.setGauche(prevVal); // Le noeud de réserve devient le sous-arbre gauche de la racine
                    prevVal = null; // Réinitialisation de la réserve
                } else if (
                        prevOper != null && (
                        Objects.equals(prevOper.terminal.toString(), MULTIPLICATION) ||
                        Objects.equals(prevOper.terminal.toString(), DIVISION)))
                {
                    System.out.println("doodad");
                    prevOper.getParent().setDroite(node);
                    node.setGauche(prevOper);
                } else {
                    node.setGauche(prevOper.getDroite());
                    prevOper.setDroite(node); // Si le précédent est un opérateur, on ajoute le nouveau noeud comme sous-arbre droit
                }
                prevOper = node;
                break;

            case NOMBRE, VARIABLE:
                if (racine != null) {
                    prevOper.setDroite(node); // Ajout du noeud comme sous-arbre droit de l'opérateur précédent
                }
                prevVal = node;
                break;

            default:
                throw new IllegalArgumentException("Type de terminal inconnu : " + node);

        }

        return (racine == null) ? prevVal : racine;
    }

    /**
     * This override is used when an operator tree needs to be treated as a variable.
     * @param node that is a number / variable
     * @param override if we override the default behaviour
     * @return
     */
    public ElemAST buildTree(ElemAST node, Boolean override) {
        if (override) {
            if (racine != null) {
                prevOper.setDroite(node); // Ajout du noeud comme sous-arbre droit de l'opérateur précédent
            }
            prevVal = node;
        } else {
            racine = buildTree(node); // Appel de la méthode normale pour construire l'arbre
        }
        return racine;
    }
}
