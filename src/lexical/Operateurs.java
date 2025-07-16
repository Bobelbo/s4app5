package app6.src.lexical;

/**
 * Cette interface définit les opérateurs arithmétiques et les parenthèses
 * utilisés dans l'analyse lexicale.
 * Un operateur est essentiellement une operation qui est un seul caractere de long
 */
public interface Operateurs {

    String PLUS = "+";
    String MOINS = "-";
    String MULTIPLICATION = "*";
    String DIVISION = "/";

    String PARENTHESE_OUVRANTE = "(";
    String PARENTHESE_FERMANTE = ")";

    String OPERATEURS = PLUS + MOINS + MULTIPLICATION + DIVISION + PARENTHESE_OUVRANTE + PARENTHESE_FERMANTE;
}
