/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceBase<T, ID> {
    T salvar(T objeto);
    T alterar(ID idObjeto, T objeto);
    T buscarPorId(ID id);
    void remover(ID id);
    List<T> listar(Pageable pageable);

}
