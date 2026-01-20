/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.loader;

import br.edu.infnet.krossby_jogo_quina_backend.model.dto.JogadorDTO;
import br.edu.infnet.krossby_jogo_quina_backend.model.dto.UsuarioDTO;
import br.edu.infnet.krossby_jogo_quina_backend.model.enumerator.TipoRole;
import br.edu.infnet.krossby_jogo_quina_backend.service.JogadorService;
import br.edu.infnet.krossby_jogo_quina_backend.service.UsuarioService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class InicioLoader implements ApplicationRunner {
    private final UsuarioService usuarioService;
    private final JogadorService jogadorService;
    Logger logger = Logger.getLogger(InicioLoader.class.getName());

    public InicioLoader(UsuarioService usuarioService, JogadorService jogadorService) {
        this.usuarioService = usuarioService;
        this.jogadorService = jogadorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JogadorDTO jogadorDTO1 = new JogadorDTO(null,"Krossby Camacho", "krossby@gmail.com", "6199999999", Arrays.asList("01","20","13","44","25"));
        JogadorDTO jogadorDTO2 = new JogadorDTO(null,"Carlos Costa", "carlos@gmail.com", "41984793814", Arrays.asList("11","20","33","44","45"));
        JogadorDTO jogadorDTO3 = new JogadorDTO(null,"Vera Lucia", "veralucia.the@gmail.com", "41984842578", Arrays.asList("11","09","13","25","45"));
        jogadorService.salvar(jogadorDTO1);
        jogadorService.salvar(jogadorDTO2);
        jogadorService.salvar(jogadorDTO3);
        logger.log(Level.INFO, "Jogadores criados com sucesso!");

        String senha = new BCryptPasswordEncoder().encode("123");
        UsuarioDTO usuarioDTO = UsuarioDTO.builder().nome("Usuario Test").email("user@gmail.com").userLogin("user").userSenha(senha).role(TipoRole.USER).build();
        UsuarioDTO user = usuarioService.salvar(usuarioDTO);
        logger.log(Level.INFO, "Usuario criado com sucesso! -> {0}", user);


    }
}
