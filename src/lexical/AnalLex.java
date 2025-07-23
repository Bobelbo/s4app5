package app6.src.lexical;

import app6.src.filelib.Reader;
import app6.src.filelib.Writer;

import static app6.src.lexical.Operateurs.OPERATEURS;

/**
 * Cette classe effectue l'analyse lexicale
 */
public class AnalLex {
    private String formule;

    /**
     * Constructeur pour l'initialisation d'attribut(s)
     */
    public AnalLex(String formule) {
        this.formule = formule.replace(" ", "");                           // Initialisation de l'attribut formule
    }

    /**
     * resteTerminal() retourne :
     * false si tous les terminaux de l'expression arithmétique ont été retournés
     * true s'il reste encore au moins un terminal qui n'a pas été retourné
     */
    public boolean resteTerminal() {
        return !formule.isEmpty();
    }

    /**
     * prochainTerminal() retourne le prochain terminal
     * Cette méthode est une implémentation d'un AEF
     */
    public Terminal prochainTerminal() {
        StringBuilder acc = new StringBuilder();

        for (int i = 0; i < formule.length(); i++) {

            char c = formule.charAt(i);

            // Check si Terminal est unique operateur
            if (OPERATEURS.contains(Character.toString(c))) {

                if (!acc.isEmpty()) { // Si on a déjà accumulé des caractères
                    formule = formule.substring(i); // On retire le terminal accumulé de la formule
                } else {
                    acc = new StringBuilder(Character.toString(c)); // On a trouvé un opérateur
                    formule = formule.substring(i + 1); // On retire le terminal trouvé de la formule
                }

                return new Terminal(acc.toString()); // Retourne le terminal trouvé
            }

            acc.append(c);
        }

        formule = ""; // On retire le terminal accumulé de la formule
        return new Terminal(acc.toString()); // Retourne le terminal trouvé
    }

    /**
     * ErreurLex() envoie un message d'erreur lexicale
     */
    public void ErreurLex(String s) {
        System.out.println("Erreur lexicale : " + s);
        System.exit(1); // Arrêt du programme en cas d'erreur lexicale
    }

    // Méthode principale à lancer pour tester l'analyseur lexical
    public static void main(String[] args) {

        StringBuilder toWrite = new StringBuilder();
        System.out.println("Debut d'analyse lexicale");

        if (args.length == 0) {

            args = new String[2];
            args[0] = "src/ExpArith.txt";
            args[1] = "src/ResultatLexical.txt";

        }
        Reader r = new Reader(args[0]);

        AnalLex lexical = new AnalLex(r.toString());            // Création de l'analyseur lexical


        // Exécution de l'analyseur lexical
        Terminal t;

        while (lexical.resteTerminal()) {

            t = lexical.prochainTerminal();
            toWrite.append(t.toString()).append("\n");
            // toWrite contient le resultat de l'analyse lexicale
        }

        System.out.println(toWrite);                            // Écriture de toWrite sur la console
        Writer w = new Writer(args[1], toWrite.toString());     // Écriture de toWrite dans fichier args[1]
        System.out.println("Fin d'analyse lexicale");
    }
}
