/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.service;


public interface ServiceBase<T, ID> {
    T salvar(T objeto);
    T buscarPorId(ID id);
    T alterar(ID idObjeto, T objeto);
    void remover(ID id);

}
