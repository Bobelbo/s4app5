package app6.src.lexical;

import static app6.src.lexical.Operateurs.*;

/**
 * Cette classe identifie les terminaux reconnus et retournes par
 * l'analyseur lexical
 */
public class Terminal {

    // Constantes et attributs
    private final String chaine;

    public final TerminalType type;


    /**
     * Un ou deux constructeurs (ou plus, si vous voulez)
     * pour l'initalisation d'attributs
     */
    public Terminal(String chaine) {
        this.chaine = chaine;

        switch(chaine) {
            case PLUS:
            case MOINS:
            case MULTIPLICATION:
            case DIVISION:
                this.type = TerminalType.OPERATEUR;
                break;

            case PARENTHESE_OUVRANTE:
            this.type = TerminalType.PARENTHESE_OUVRANTE;
                break;

            case PARENTHESE_FERMANTE:
                this.type = TerminalType.PARENTHESE_FERMANTE;
                break;

            default:
                if (chaine.matches("\\d+")) { // Vérifie si c'est un nombre
                    this.type = TerminalType.NOMBRE;
                } else if (chaine.matches("^[A-Z]([A-Za-z]|_(?!_|$))*$")) { // Vérifie si c'est une variable
                    this.type = TerminalType.VARIABLE;
                } else {
                    throw new IllegalArgumentException("Terminal inconnu: " + chaine);
                }
                break;
        }
    }

    @Override
    public String toString() {
        return this.chaine;
    }
}

