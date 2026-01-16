/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.util;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

public class GeralUtils {
    private static final Pattern SOMENTE_NUMEROS = Pattern.compile("^\\d+$");
    private static final Pattern EMAIL_VALIDO = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z]+$");

    private GeralUtils() {}

    public static String ordenarArrayListaToString(List<String> lista) {
        lista.sort(null);
        return StringUtils.collectionToCommaDelimitedString(lista);

    }
    public static boolean contemNumeros(String campo) {
        if (campo == null || campo.trim().isEmpty()) {
            return true;
        }
        return !SOMENTE_NUMEROS.matcher(campo).matches();
    }

    public static boolean emailValido(String email) {
        return EMAIL_VALIDO.matcher(email).matches();
    }


}
