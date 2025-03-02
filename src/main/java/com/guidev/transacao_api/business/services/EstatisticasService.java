package com.guidev.transacao_api.business.services;

import com.guidev.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.guidev.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticasTransacoes(Integer intervaloBusca) {
        log.info("iniciada busca de estatísticas de transações" + intervaloBusca);

        Long start = System.currentTimeMillis();

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);

        if(transacoes.isEmpty()){
            return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        DoubleSummaryStatistics estatisticasTransacoes = transacoes
                .stream()
                .mapToDouble(TransacaoRequestDTO::valor)
                .summaryStatistics();

        Long finish = System.currentTimeMillis();
        Long tempoRequisicao = finish - start;

        System.out.println("Tempo de requisição: "+ tempoRequisicao + " milissegundos");

        log.info("Estatísticas retornadas com sucesso");

        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(),
                estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(),
                estatisticasTransacoes.getMin(),
                estatisticasTransacoes.getMax());
    }
}
