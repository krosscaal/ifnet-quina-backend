/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.config.clients;

import br.edu.infnet.krossby_jogo_quina_backend.model.enumerator.TipoJogo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "api-loteria-caixa-quina", url = "${api.caixa.url}")
public interface ApiLoteriaCaixaQuinaFeignClient {

    @GetMapping(value = "/quina")
    ApiCaixaQuinaResponse obterLoteriaQuinaCaixa();

    @GetMapping(value = "/quina/{jogo}")
    ApiCaixaQuinaResponse obterLoteriaQuinaCaixa(@PathVariable("jogo") String jogo);


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    class ApiCaixaQuinaResponse {
        private String dataApuracao;
        private String dataProximoConcurso;
        private Integer numeroConcursoAnterior;
        private Integer numeroConcursoProximo;
        private List<String> dezenasSorteadasOrdemSorteio;
        private List<String> listaDezenas;
        private Boolean acumulado;
        private String valorEstimadoProximoConcurso;
        private Integer numero;
        private TipoJogo tipoJogo;

    }
}
