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
        ElemAST racine = null; // Racine de l'AST
        List<SyntaxTreeBuilder> refRacinesParenthese = new ArrayList<>();
        // 0 est le root builder
        refRacinesParenthese.add(new SyntaxTreeBuilder());
        int profondeurRacine = 0;

        try {
            while (this.analexical.resteTerminal()) {
                Terminal terminal = this.analexical.prochainTerminal(); // Récupération du prochain terminal

                if (terminal.type == PARENTHESE_OUVRANTE) {
                    profondeurRacine++;
                    refRacinesParenthese.add(profondeurRacine, new SyntaxTreeBuilder());
                } else if (terminal.type == PARENTHESE_FERMANTE) {
                    profondeurRacine--;
                    racine = refRacinesParenthese.get(profondeurRacine).buildTree(racine);
                } else {
                    racine = refRacinesParenthese.get(profondeurRacine).buildTree(new NoeudAST(terminal));
                }
            } // End of while

            if (profondeurRacine != 0) {
                ErreurSynt("Parenthèses non équilibrées");
            }
        }
        catch (IllegalArgumentException e) {
            ErreurSynt(e.getMessage());
        }

        return racine;
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

