/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.service;

import br.edu.infnet.krossby_jogo_quina_backend.exception.BusinessException;
import br.edu.infnet.krossby_jogo_quina_backend.exception.UsuarioException;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.UsuarioDTO;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Usuario;
import br.edu.infnet.krossby_jogo_quina_backend.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsuarioService implements ServiceBase<UsuarioDTO, UUID> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional
    @Override
    public UsuarioDTO salvar(UsuarioDTO objeto) {
        Usuario usuario = this.dtoToEntity(objeto);
        Usuario usuarioSaved = this.usuarioRepository.save(usuario);
        return this.entityToDto(usuarioSaved);
    }

    private UsuarioDTO entityToDto(Usuario usuarioSaved) {
        return new UsuarioDTO(
                usuarioSaved.getId(),
                usuarioSaved.getNome(),
                usuarioSaved.getEmail(),
                "",
                "");
    }

    private Usuario dtoToEntity(UsuarioDTO objeto) {
        this.verificarUsuario(objeto);
        Usuario usuario = Usuario.builder().build();
        BeanUtils.copyProperties(objeto, usuario, "id");
        return usuario;
    }

    private void verificarUsuario(UsuarioDTO objeto) {
        if (objeto.nome() == null || objeto.nome().isBlank()) {
            throw new BusinessException("Nome precisa ser informado!");
        }
        if (objeto.email() == null || objeto.email().isBlank()) {
            throw new BusinessException("Email precisa ser informado!");
        }
        if (objeto.userLogin() == null || objeto.userLogin().isBlank()) {
            throw new BusinessException("Login precisa ser informado!");
        }
        if (objeto.userSenha() == null || objeto.userSenha().isBlank()) {
            throw new BusinessException("Senha precisa ser informada!");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UsuarioDTO buscarPorId(UUID uuid) {
        Usuario usuario = this.buscarUsuarioPorId(uuid);

        return entityToDto(usuario);
    }

    @Transactional
    @Override
    public UsuarioDTO alterar(UUID idObjeto, UsuarioDTO objeto) {
        Usuario usuarioEntidade = this.buscarUsuarioPorId(idObjeto);
        BeanUtils.copyProperties(objeto, usuarioEntidade);
        return entityToDto(usuarioRepository.save(usuarioEntidade));
    }

    @Transactional
    @Override
    public void remover(UUID uuid) {
        this.buscarUsuarioPorId(uuid);
        this.usuarioRepository.deleteById(uuid);
    }

    public Usuario buscarUsuarioPorId(UUID id) {
        return this.usuarioRepository.findById(id).orElseThrow(()-> new UsuarioException("Usuário Não Encontrado!"));
    }


}
