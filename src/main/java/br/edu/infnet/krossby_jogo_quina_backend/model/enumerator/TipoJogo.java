/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.model.enumerator;

import lombok.Getter;

@Getter
public enum TipoJogo {
    LOTOFACIL(1, "Lotofacil"),
    LOTOMANIA(2, "Lotomania"),
    MEGASENA(3,"Megasena"),
    QUINA(4,"Quina");

    private int codigo;
    private String descricao;

    TipoJogo(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    public static TipoJogo getTipoJogo(int codigo) {
        for (TipoJogo tipoJogo : TipoJogo.values()) {
            if (tipoJogo.getCodigo() == codigo) {
                return tipoJogo;
            }
        }
        throw new IllegalArgumentException("Tipo de Jogo não encontrado");
    }
    public static TipoJogo getTipoJogo(String descricao) {
        for (TipoJogo tipoJogo : TipoJogo.values()) {
            if (tipoJogo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipoJogo;
            }
        }
        throw new IllegalArgumentException("Tipo de Jogo não encontrado");
    }
}
