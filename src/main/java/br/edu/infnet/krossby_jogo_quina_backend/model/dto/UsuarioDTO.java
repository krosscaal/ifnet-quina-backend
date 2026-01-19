/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.model.dto;

import br.edu.infnet.krossby_jogo_quina_backend.model.enumerator.TipoRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static br.edu.infnet.krossby_jogo_quina_backend.util.CentroDeMensagens.*;
@Getter
@Setter
@Builder
public class UsuarioDTO {
    private UUID id;
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    private String nome;
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Email(message = FORMATO_E_MAIL_INCORRETO)
    private String email;
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    private String userLogin;
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    private String userSenha;
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Enumerated(EnumType.STRING)
    private TipoRole role;

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", role=" + role +
                '}';
    }
}
