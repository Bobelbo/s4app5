package app6.src.ast;

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
}
