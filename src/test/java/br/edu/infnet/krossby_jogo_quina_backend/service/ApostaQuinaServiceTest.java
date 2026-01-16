package br.edu.infnet.krossby_jogo_quina_backend.service;

import br.edu.infnet.krossby_jogo_quina_backend.util.GeralUtils;
import br.edu.infnet.krossby_jogo_quina_backend.exception.BusinessException;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.ApostaDTO;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Aposta;
import br.edu.infnet.krossby_jogo_quina_backend.model.entity.Usuario;
import br.edu.infnet.krossby_jogo_quina_backend.model.enumerator.TipoJogo;
import br.edu.infnet.krossby_jogo_quina_backend.repository.ApostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ApostaQuinaServiceTest {
    
    @InjectMocks
    private ApostaQuinaService apostaQuinaService;

    @Mock
    private UsuarioService usuarioService;
    
    @Mock
    private ApostaRepository apostaRepository;

    private Usuario usuario;
    private ApostaDTO apostaDTO;
    private Aposta apostaEntidade;

    @BeforeEach
    void setUp() {
        usuario = this.criarUsuario();
        List<String> aposta = Arrays.asList("15", "01", "23", "10", "07");
        apostaDTO = new ApostaDTO(null, usuario.getId(), aposta,"8580", LocalDate.now(), TipoJogo.QUINA);
        String apostaTratada = GeralUtils.ordenarArrayListaToString(aposta);
        apostaEntidade = this.criarAposta();
        apostaEntidade.setNumeroAposta(apostaTratada);
        apostaEntidade.setId(UUID.randomUUID());
    }

    @Test
    @DisplayName("Salvar Aposta")
    void incluirTest() {
        when(usuarioService.buscarUsuarioPorId(any())).thenReturn(usuario);
        when(apostaRepository.save(any())).thenReturn(apostaEntidade);
        ApostaDTO response = apostaQuinaService.salvar(apostaDTO);
        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(usuario.getId(), response.usuarioId());
        assertEquals("8580", response.numeroJogo());
        assertEquals(List.of("01", "07", "10", "15", "23"), response.aposta());
    }
    @Test
    @DisplayName("BusinessException -> Aposta precisa ser informada!")
    void salvarTest_ApostaVazia() {
        List<String> aposta = List.of();
        apostaDTO = new ApostaDTO(null, UUID.randomUUID(), aposta,"8580", LocalDate.now(), TipoJogo.QUINA);
        BusinessException businessException = assertThrows(BusinessException.class, () -> apostaQuinaService.salvar(apostaDTO));
        assertEquals("APOSTA PRECISA SER INFORMADA!", businessException.getMessage());

    }
    @Test
    @DisplayName("BusinessException -> Aposta sem quantidade de números requerida!")
    void salvarTest_ApostaInvalida() {
        List<String> aposta = List.of("01", "07", "10", "15");
        apostaDTO = new ApostaDTO(null, UUID.randomUUID(), aposta,"8580", LocalDate.now(), TipoJogo.QUINA);
        BusinessException businessException = assertThrows(BusinessException.class, () -> apostaQuinaService.salvar(apostaDTO));
        assertEquals("APOSTA SEM QUANTIDADE DE NUMEROS REQUERIDA!", businessException.getMessage());

    }
    @Test
    @DisplayName("BusinessException -> Data de Jogo precisa ser informada!")
    void salvarTest_ApostaInvalida_semDataJogo() {
        List<String> aposta = Arrays.asList("15", "01", "23", "10", "07");
        apostaDTO = new ApostaDTO(null, UUID.randomUUID(), aposta,"8580", null, TipoJogo.QUINA);
        BusinessException businessException = assertThrows(BusinessException.class, () -> apostaQuinaService.salvar(apostaDTO));
        assertEquals("DATA DE JOGO PRECISA SER INFORMADA!", businessException.getMessage());

    }
    @Test
    @DisplayName("BusinessException -> Tipo de Jogo precisa ser informado!")
    void salvarTest_ApostaInvalida_semTipoJogo() {
        List<String> aposta = Arrays.asList("15", "01", "23", "10", "07");
        apostaDTO = new ApostaDTO(null, UUID.randomUUID(), aposta,"8580", LocalDate.now(), null);
        BusinessException businessException = assertThrows(BusinessException.class, () -> apostaQuinaService.salvar(apostaDTO));
        assertEquals("TIPO DE JOGO PRECISA SER INFORMADO!", businessException.getMessage());

    }
    @Test
    @DisplayName("BusinessException -> Número de Jogo precisa ser informado!")
    void salvarTest_ApostaInvalida_semNumeroJogo() {
        List<String> aposta = Arrays.asList("15", "01", "23", "10", "07");
        apostaDTO = new ApostaDTO(null, UUID.randomUUID(), aposta,null, LocalDate.now(), TipoJogo.QUINA);
        BusinessException businessException = assertThrows(BusinessException.class, () -> apostaQuinaService.salvar(apostaDTO));
        assertEquals("NUMERO DE JOGO PRECISA SER INFORMADO!", businessException.getMessage());

    }
    @Test
    @DisplayName("BusinessException -> Usuario precisa ser informado!")
    void salvarTest_ApostaInvalida_semUsuario() {
        List<String> aposta = Arrays.asList("15", "01", "23", "10", "07");
        apostaDTO = new ApostaDTO(null, null, aposta,"8580", LocalDate.now(), TipoJogo.QUINA);
        BusinessException businessException = assertThrows(BusinessException.class, () -> apostaQuinaService.salvar(apostaDTO));
        assertEquals("USUARIO PRECISA SER INFORMADO!", businessException.getMessage());

    }

    @Test
    @DisplayName("Alterar Aposta")
    void alterarTest() {
        List<String> aposta = Arrays.asList("03", "13", "33", "43", "53");
        apostaDTO = new ApostaDTO(apostaEntidade.getId(), usuario.getId(), aposta,"8000", LocalDate.now(), TipoJogo.QUINA);

        Aposta apostaPersistida = Aposta.builder()
                .usuario(apostaEntidade.getUsuario())
                .numeroAposta(GeralUtils.ordenarArrayListaToString(aposta))
                .numeroJogo("8000")
                .dataJogo(apostaEntidade.getDataJogo())
                .tipoJogo(apostaEntidade.getTipoJogo())
                .build();
        apostaPersistida.setId(apostaEntidade.getId());

        when(apostaRepository.findById(any())).thenReturn(Optional.ofNullable(apostaEntidade));
        when(apostaRepository.save(any())).thenReturn(apostaPersistida);
        ApostaDTO response = apostaQuinaService.alterar(apostaEntidade.getId(), apostaDTO);
        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(apostaEntidade.getId(), response.id());
        assertEquals(usuario.getId(), response.usuarioId());
        assertEquals("8000", response.numeroJogo());
        assertEquals(List.of("03", "13", "33", "43", "53"), response.aposta());
    }

    @Test
    @DisplayName("Buscar Aposta")
    void buscarPorIdTest() {
        when(apostaRepository.findById(any())).thenReturn(Optional.ofNullable(apostaEntidade));
        ApostaDTO response = apostaQuinaService.buscarPorId(apostaEntidade.getId());
        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(apostaEntidade.getId(), response.id());
        assertEquals(usuario.getId(), response.usuarioId());
        assertEquals("8580", response.numeroJogo());
        assertEquals(List.of("01", "07", "10", "15", "23"), response.aposta());
    }
    @Test
    @DisplayName("Buscar Aposta deve dar NoSuchElementException")
    void buscarPorIdTest_naoEncontrado() {
        UUID id = UUID.randomUUID();
        when(apostaRepository.findById(any())).thenReturn(Optional.empty());
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> apostaQuinaService.buscarPorId(id));
        assertEquals("Não existe aposta com o id: ".concat(String.valueOf(id)), noSuchElementException.getMessage());
    }
    @Test
    @DisplayName("Remover Aposta")
    void removerTest() {
        when(apostaRepository.findById(any())).thenReturn(Optional.ofNullable(apostaEntidade));
        apostaQuinaService.remover(apostaEntidade.getId());
        verify(apostaRepository).deleteById(apostaEntidade.getId());
    }


    private Usuario criarUsuario() {
        Usuario novoUsuario = Usuario.builder()
                .nome("User Teste")
                .email("teste@gmail.com")
                .userLogin("userteste")
                .userSenha("senha")
                .build();
        novoUsuario.setId(UUID.randomUUID());
        return novoUsuario;
    }
    private Aposta criarAposta() {
        apostaEntidade = Aposta.builder().numeroJogo("8580").dataJogo(LocalDate.now()).tipoJogo(TipoJogo.QUINA).usuario(usuario).build();
        apostaEntidade.setId(UUID.randomUUID());
        return apostaEntidade;
    }
}