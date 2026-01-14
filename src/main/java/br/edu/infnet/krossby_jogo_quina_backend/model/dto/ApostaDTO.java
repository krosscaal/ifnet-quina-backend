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

public record ApostaDTO(
        UUID id,
        @NotNull
        UUID usuarioId,
        @NotNull
        List<String> aposta,
        @NotBlank
        String numeroJogo,
        @NotNull
        LocalDate dataJogo,
        @NotNull
        TipoJogo tipoJogo) {
}
