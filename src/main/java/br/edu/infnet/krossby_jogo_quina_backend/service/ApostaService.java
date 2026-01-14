/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.service;

import br.edu.infnet.krossby_jogo_quina_backend.Utils;
import br.edu.infnet.krossby_jogo_quina_backend.exception.BusinessException;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.ApostaDTO;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Aposta;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Usuario;
import br.edu.infnet.krossby_jogo_quina_backend.repository.ApostaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ApostaService implements ServiceBase<ApostaDTO, UUID> {

    private final ApostaRepository apostaRepository;
    private final UsuarioService usuarioService;

    public ApostaService(ApostaRepository apostaRepository, UsuarioService usuarioService) {
        this.apostaRepository = apostaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    @Override
    public ApostaDTO salvar(ApostaDTO objeto) {
        Aposta entidadeAposta = this.dtoToEntidade(objeto);
        Aposta apostaPersistida = apostaRepository.save(entidadeAposta);
        return this.entidadeToDto(apostaPersistida);
    }
    private Aposta dtoToEntidade(ApostaDTO dto) {
        this.validarAposta(dto);
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(dto.usuarioId());

        Aposta entidadeAposta = Aposta.builder().build();
        BeanUtils.copyProperties(dto, entidadeAposta,"aposta", "usuario");
        entidadeAposta.setUsuario(usuario);
        entidadeAposta.setNumeroAposta(this.tratarAposta(dto.aposta()));
        return entidadeAposta;
    }
    private ApostaDTO entidadeToDto(Aposta entidade) {
        List<String> aposta = Arrays.stream(entidade.getNumeroAposta().split(",")).toList();

        return new ApostaDTO(
                entidade.getId(),
                entidade.getUsuario().getId(),
                aposta,
                entidade.getNumeroJogo(),
                entidade.getDataJogo(),
                entidade.getTipoJogo());
    }

    private String tratarAposta(List<String> aposta) {
        return Utils.ordenarArrayListaToString(aposta);
    }

    private void validarAposta(ApostaDTO objeto) {
        if (objeto.aposta() == null || objeto.aposta().isEmpty()) {
            throw new BusinessException("Aposta precisa ser informada!");
        }
        if (objeto.aposta().size() != 5) {
            throw new BusinessException("Aposta sem quantidade de numeros requerida!");
        }
        if (objeto.dataJogo() == null) {
            throw new BusinessException("Data de Jogo precisa ser informada!");
        }
        if (objeto.tipoJogo() == null) {
            throw new BusinessException("Tipo de Jogo precisa ser informado!");
        }
        if (objeto.numeroJogo() == null || objeto.numeroJogo().isBlank()) {
            throw new BusinessException("Numero de Jogo precisa ser informado!");
        }
        if (objeto.usuarioId() == null) {
            throw new BusinessException("usuarioId precisa ser informado!");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public ApostaDTO buscarPorId(UUID uuid) {
        Aposta aposta = this.buscarApostaPorId(uuid);
        return entidadeToDto(aposta);
    }

    @Transactional
    @Override
    public ApostaDTO alterar(UUID idObjeto, ApostaDTO objeto) {
        Aposta apostaEntidade = this.buscarApostaPorId(idObjeto);
        BeanUtils.copyProperties(objeto, apostaEntidade, "id", "usuarioId", "aposta");
        apostaEntidade.setNumeroAposta(this.tratarAposta(objeto.aposta()));
        return entidadeToDto(apostaRepository.save(apostaEntidade));
    }

    private Aposta buscarApostaPorId(UUID idObjeto) {
        return apostaRepository.findById(idObjeto).orElseThrow(() -> new NoSuchElementException(String.format("Não existe aposta com o id: %s", idObjeto)));
    }

    @Override
    public void remover(UUID uuid) {
        this.buscarApostaPorId(uuid);
        apostaRepository.deleteById(uuid);
    }

    @Transactional(readOnly = true)
    public Page<ApostaDTO> buscar(Integer page, Integer size) {
        int pagina = page == null ? 0 : Math.max(0, page);
        int tamanho = size == null ? 10 : Math.max(1, size);
        Pageable pageable = PageRequest.of(pagina, tamanho);

        return apostaRepository.findAll(pageable).map(this::entidadeToDto);
    }

}
