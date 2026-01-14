/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.resource;

import br.edu.infnet.krossby_jogo_quina_backend.exception.BusinessException;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.ApostaDTO;
import br.edu.infnet.krossby_jogo_quina_backend.service.ApostaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/aposta")
public class ApostaResource extends ResourceBase<ApostaDTO, UUID>{

    private final ApostaService apostaService;

    public ApostaResource(ApostaService apostaService) {
        this.apostaService = apostaService;
    }

    @Override
    protected ResponseEntity<ApostaDTO> acaoIncluir(ApostaDTO dto) throws BusinessException {
        return new ResponseEntity<>(apostaService.salvar(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<ApostaDTO> acaoObterPorId(UUID uuid) throws BusinessException {
        return ResponseEntity.ok(apostaService.buscarPorId(uuid));
    }

    @Override
    protected ResponseEntity<ApostaDTO> acaoAlterar(UUID uuid, ApostaDTO dto) throws BusinessException {
        return ResponseEntity.ok(apostaService.alterar(uuid, dto));
    }

    @Override
    protected void acaoExcluir(UUID uuid) throws BusinessException {
        apostaService.remover(uuid);
    }
    @GetMapping(value = "/buscar", produces = {"application/json"})
    public Page<ApostaDTO> acaoBuscar(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return apostaService.buscar(page, size);
    }
}
