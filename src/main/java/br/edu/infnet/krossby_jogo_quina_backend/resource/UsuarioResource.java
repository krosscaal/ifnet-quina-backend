/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.resource;

import br.edu.infnet.krossby_jogo_quina_backend.exception.BusinessException;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.UsuarioDTO;
import br.edu.infnet.krossby_jogo_quina_backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource extends ResourceBase<UsuarioDTO, UUID>{

    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected ResponseEntity<UsuarioDTO> acaoIncluir(UsuarioDTO dto) throws BusinessException {
        return new ResponseEntity<>(usuarioService.salvar(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<UsuarioDTO> acaoObterPorId(UUID uuid) throws BusinessException {
        return ResponseEntity.ok(usuarioService.buscarPorId(uuid));
    }

    @Override
    protected ResponseEntity<UsuarioDTO> acaoAlterar(UUID uuid, UsuarioDTO dto) throws BusinessException {
        return ResponseEntity.ok(usuarioService.alterar(uuid, dto));
    }

    @Override
    protected void acaoExcluir(UUID uuid) throws BusinessException {
        usuarioService.remover(uuid);
    }
}
