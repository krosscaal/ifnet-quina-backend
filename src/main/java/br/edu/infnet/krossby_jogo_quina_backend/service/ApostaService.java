/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.service;

import br.edu.infnet.krossby_jogo_quina_backend.Utils;
import br.edu.infnet.krossby_jogo_quina_backend.exception.BusinessException;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.ApostaDTO;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Aposta;
import br.edu.infnet.krossby_jogo_quina_backend.repository.ApostaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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

    public ApostaService(ApostaRepository apostaRepository) {
        this.apostaRepository = apostaRepository;
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
        Aposta entidadeAposta = Aposta.builder().build();
        BeanUtils.copyProperties(dto, entidadeAposta,"aposta");
        entidadeAposta.setNumeroAposta(this.tratarAposta(dto.aposta()));
        return entidadeAposta;
    }
    private ApostaDTO entidadeToDto(Aposta entidade) {
        List<String> aposta = Arrays.stream(entidade.getNumeroAposta().split(",")).toList();

        return new ApostaDTO(
                entidade.getId(),
                entidade.getUsuario(),
                aposta,
                entidade.getNumeroJogo(),
                entidade.getDataJogo(),
                entidade.getTipoJogo());
    }

    private String tratarAposta(List<String> aposta) {
        return Utils.ordenarArrayListaToString(aposta);
    }

    private void validarAposta(ApostaDTO objeto) {
        if (objeto.aposta().isEmpty()) {
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
        if (objeto.numeroJogo() == null) {
            throw new BusinessException("Numero de Jogo precisa ser informado!");
        }
        if (objeto.usuario() == null) {
            throw new BusinessException("Usuario precisa ser informado!");
        }

    }

    @Transactional
    @Override
    public ApostaDTO alterar(UUID idObjeto, ApostaDTO objeto) {
        Aposta apostaObj = this.buscarApostaPorId(idObjeto);
        ApostaDTO updateDTO = new ApostaDTO(
                apostaObj.getId(),
                objeto.usuario(),
                objeto.aposta(),
                objeto.numeroJogo(),
                objeto.dataJogo(),
                objeto.tipoJogo());
        Aposta apostaPreUpdate = this.dtoToEntidade(updateDTO);
        return entidadeToDto(apostaRepository.save(apostaPreUpdate));

    }

    private Aposta buscarApostaPorId(UUID idObjeto) {
        return apostaRepository.findById(idObjeto).orElseThrow(() -> new NoSuchElementException(String.format("Não existe aposta com o id: %s", idObjeto)));
    }

    @Transactional(readOnly = true)
    @Override
    public ApostaDTO buscarPorId(UUID uuid) {
        Aposta aposta = this.buscarApostaPorId(uuid);
        return entidadeToDto(aposta);
    }

    @Override
    public void remover(UUID uuid) {
        this.buscarApostaPorId(uuid);
        apostaRepository.deleteById(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ApostaDTO> listar(Pageable pageable) {
        return apostaRepository.findAll(pageable).map(this::entidadeToDto);
    }

}
