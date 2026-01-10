/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.exception;

import java.io.Serial;
import java.util.Locale;

public class UsuarioException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioException(String message) {
        super(message.toUpperCase(Locale.ROOT));
    }
}
