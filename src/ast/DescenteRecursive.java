package app6.src.ast;

import app6.src.filelib.Reader;
import app6.src.filelib.Writer;
import app6.src.lexical.AnalLex;
import app6.src.lexical.Terminal;

import java.util.ArrayList;
import java.util.List;

import static app6.src.lexical.TerminalType.*;

/**
 * Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {
    private final AnalLex analexical;

    /**
     * Constructeur de DescenteRecursive
     */
    public DescenteRecursive(String input) {
        this.analexical = new AnalLex(new Reader(input).toString()); // Initialisation du lecteur de fichier
    }

    /**
     * AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     * Elle retourne une référence sur la racine de l'AST construit
     */
    public ElemAST AnalSynt() {
        return new RecursiveDescentParser(this.analexical).parse();
    }

    /**
     * ErreurSynt() envoie un message d'erreur syntaxique
     */
    public void ErreurSynt(String s) {
        System.out.println("Erreur syntaxique : " + s);
        System.exit(1); // Arrêt du programme en cas d'erreur lexicale
    }


    // Méthode principale à lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) {

        String toWriteLect = "";
        String toWriteEval = "";

        System.out.println("Debut d'analyse syntaxique");

        if (args.length == 0) {
            args = new String[2];
            args[0] = "src/ExpArith.txt";
            args[1] = "src/ResultatSyntaxique.txt";
        }

        DescenteRecursive dr = new DescenteRecursive(args[0]);

        try {

            ElemAST RacineAST = dr.AnalSynt();

            toWriteLect += "Lecture de l'AST trouve : " + "\n" + RacineAST.toString();
            System.out.println(toWriteLect);

            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
            System.out.println(toWriteEval);

            Writer w = new Writer(args[1], toWriteLect + toWriteEval);              // Écriture de toWrite dans fichier args[1]

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();

            System.exit(51);

        }

        System.out.println("Analyse syntaxique terminee");

    }
}

