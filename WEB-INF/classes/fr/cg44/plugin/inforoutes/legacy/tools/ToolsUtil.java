package fr.cg44.plugin.inforoutes.legacy.tools;

import org.apache.log4j.Logger;

import com.jalios.util.Util;

/**
 * Class utilitaire.
 */
public final class ToolsUtil {

  private ToolsUtil() {
  }


  /**
   * Détermine si une valeur est présente dans un tableau.
   * 
   * @param array
   *          Tableau à parcourir.
   * @param value
   *          Valeur à vérifier.
   * @return True si la valeur est présente dans le tableau, false sinon.
   */
  public static boolean inArray(String[] array, String value) {
    if (Util.notEmpty(array)) {
      for (String string : array) {
        if (string.equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Détermine si un caractère est présent dans un tableau.
   * 
   * @param array
   *          Tableau à parcourir.
   * @param value
   *          Valeur à vérifier.
   * @return True si la valeur est présente dans le tableau, false sinon.
   */
  public static boolean inArray(char[] array, char value) {
    if (Util.notEmpty(array)) {
      for (char character : array) {
        if (character == value) {
          return true;
        }
      }
    }
    return false;
  }
  
}