package br.edu.infnet.krossby_jogo_quina_backend;

import br.edu.infnet.krossby_jogo_quina_backend.util.GeralUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void ordenarArrayListaToStringTest() {
        List<String> lista = Arrays.asList("10", "02", "30");
        String resultadoEsperado = "02,10,30";
        assertEquals(resultadoEsperado, GeralUtils.ordenarArrayListaToString(lista));
    }

}