package com.guidev.transacao_api.controller;

import com.guidev.transacao_api.business.services.EstatisticasService;
import com.guidev.transacao_api.controller.dtos.EstatisticasResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
public class EstatisticasController {

    private final EstatisticasService estatisticasService;
    @GetMapping
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            @RequestParam(value = "intervaloBusca", required = false, defaultValue = "60")Integer intervaloBusca){
        return ResponseEntity.ok(estatisticasService.calcularEstatisticasTransacoes(intervaloBusca));

    }
}
