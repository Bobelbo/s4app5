package app6.src.filelib;

import java.io.FileInputStream;

/** Classe permettant d'ouvrir et de faire la lecture d'un fichier
 *
 */
public class Reader {
    String _str;

    /** Constructeur prenant dans ses paramètres le nom du fichier à ouvrir
     * @param name String qui est le Nom du Fichier
     */

    public Reader(String name) {
        try {

            FileInputStream fis = new FileInputStream(name);
            int n;

            while ((n = fis.available()) > 0) {

                byte[] b = new byte[n];
                int result = fis.read(b);

                if (result == -1)
                    break;

                _str = new String(b);

            }

            fis.close();

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();

            System.exit(1);

        }
    }

    /** Méthode renvoyant la String
     * @return La string contenu dans le fichier
     */
    public String toString() {
        return _str;
    }

}
