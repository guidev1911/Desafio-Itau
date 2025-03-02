package com.guidev.transacao_api.business.service;

import com.guidev.transacao_api.business.services.EstatisticasService;
import com.guidev.transacao_api.business.services.TransacaoService;
import com.guidev.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.guidev.transacao_api.controller.dtos.TransacaoRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {

    @InjectMocks
    EstatisticasService estatisticasService;

    @Mock
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    EstatisticasResponseDTO estatisticas;
    @BeforeEach
    void setUp(){
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void calcularEstatisticasComSucesso(){
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.singletonList(transacao));
        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticas);
    }

    @Test
    void calcularEstatisticasQuandoListaVazia(){

        EstatisticasResponseDTO estatisticaEsperado = new EstatisticasResponseDTO(0L,0.0,0.0,0.0,0.0);
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.emptyList());
        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticaEsperado);
    }
}
