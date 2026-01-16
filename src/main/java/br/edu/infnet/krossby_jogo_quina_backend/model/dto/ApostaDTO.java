/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.model.dto;

import br.edu.infnet.krossby_jogo_quina_backend.model.enumerator.TipoJogo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static br.edu.infnet.krossby_jogo_quina_backend.util.CentroDeMensagens.NAO_NULL;
import static br.edu.infnet.krossby_jogo_quina_backend.util.CentroDeMensagens.NAO_VAZIO;

public record ApostaDTO(
        UUID id,
        @NotNull(message = NAO_NULL)
        UUID usuarioId,
        @NotNull(message = NAO_NULL)
        List<String> aposta,
        @NotNull(message = NAO_NULL)
        @NotBlank(message = NAO_VAZIO)
        String numeroJogo,
        @NotNull(message = NAO_NULL)
        LocalDate dataJogo,
        @NotNull(message = NAO_NULL)
        TipoJogo tipoJogo) {
}
