/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.repository;

import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Aposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApostaRepository extends JpaRepository<Aposta, UUID> {
}
