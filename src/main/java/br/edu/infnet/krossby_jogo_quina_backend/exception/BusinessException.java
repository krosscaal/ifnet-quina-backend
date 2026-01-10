/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.exception;

import java.io.Serial;
import java.util.Locale;

public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message.toUpperCase(Locale.ROOT));
    }
}
