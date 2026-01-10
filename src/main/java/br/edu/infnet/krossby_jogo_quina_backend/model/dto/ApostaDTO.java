/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.model.dto;

import br.edu.infnet.krossby_jogo_quina_backend.model.enumerator.TipoJogo;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Usuario;

import java.util.Date;
import java.util.List;

public record ApostaDTO(Usuario usuario, List<String> aposta, String numeroJogo, Date dataJogo, TipoJogo tipoJogo) {
}
