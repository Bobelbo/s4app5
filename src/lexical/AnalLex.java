package app6.src.lexical;

import app6.src.filelib.Reader;
import app6.src.filelib.Writer;

/**
 * Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

    // Attributs
    private final String formule;

    /**
     * Constructeur pour l'initialisation d'attribut(s)
     */
    public AnalLex(String formule) {
        this.formule = formule;                                 // Initialisation de l'attribut formule
    }


    /**
     * resteTerminal() retourne :
     * false si tous les terminaux de l'expression arithmetique ont été retournés
     * true s'il reste encore au moins un terminal qui n'a pas été retourné
     */
    public boolean resteTerminal() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }


    /**
     * prochainTerminal() retourne le prochain terminal
     * Cette methode est une implémentation d'un AEF
     */
    public Terminal prochainTerminal() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }


    /**
     * ErreurLex() envoie un message d'erreur lexicale
     */
    public void ErreurLex(String s) {
        // TODO
    }


    // Méthode principale à lancer pour tester l'analyseur lexical
    public static void main(String[] args) {

        StringBuilder toWrite = new StringBuilder();
        System.out.println("Debut d'analyse lexicale");

        if (args.length == 0) {

            args = new String[2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";

        }
        Reader r = new Reader(args[0]);

        AnalLex lexical = new AnalLex(r.toString());            // Création de l'analyseur lexical


        // Exécution de l'analyseur lexical
        Terminal t;

        while (lexical.resteTerminal()) {

            t = lexical.prochainTerminal();
            toWrite.append(t.chaine).append("\n");
            // toWrite contient le resultat de l'analyse lexicale
        }

        System.out.println(toWrite);                            // Ecriture de toWrite sur la console
        Writer w = new Writer(args[1], toWrite.toString());     // Ecriture de toWrite dans fichier args[1]
        System.out.println("Fin d'analyse lexicale");
    }
}
