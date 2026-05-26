package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MovimentacaoMetaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MovimentacaoMetaResponseDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MetasRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception.DespesaNotFoundException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Metas;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.MovimentacaoMeta;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.TipoMovimentacaoMeta;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.MetasRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.MovimentacaoMetaRepository;

@Service
public class MetasService {

    private final MetasRepository repository;
    private final SaldoService saldoService;
    private final MovimentacaoMetaRepository movimentacaoRepository;

    public MetasService(
        MetasRepository repository,
        SaldoService saldoService,
        MovimentacaoMetaRepository movimentacaoRepository
    ) {
        this.repository = repository;
        this.saldoService = saldoService;
        this.movimentacaoRepository = movimentacaoRepository;
    }
    
        public List<Metas> findAll() {
        return repository.findAll();
    }

    public Metas incluir(MetasRequestDto dto) {
        double valorMeta = positive(dto.valorMeta(), "O valor da meta deve ser maior que zero.");
        double valorGuardado = nonNegative(dto.valor(), "O valor guardado não pode ser negativo.");
        validateValorGuardado(valorMeta, valorGuardado);

        var metas = new Metas (
            valorMeta,
            0.0,
            dto.metaCategoria()
        );
        var saved = repository.save(metas);
        if (valorGuardado > 0) {
            registrarEntrada(saved.getId(), new MovimentacaoMetaRequestDto(valorGuardado, LocalDate.now(), "Valor inicial"));
            return BuscarPorId(saved.getId());
        }
        return saved;
    }
    
    public Metas BuscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new DespesaNotFoundException(id));
    }

    public Metas atualizar(Long id, MetasRequestDto dto) {
        var metas = BuscarPorId(id);
        double valorMeta = positive(dto.valorMeta(), "O valor da meta deve ser maior que zero.");
        double novoValorGuardado = nonNegative(dto.valor(), "O valor guardado não pode ser negativo.");
        validateValorGuardado(valorMeta, novoValorGuardado);

        double valorAtual = safe(metas.getValor());
        double incremento = novoValorGuardado - valorAtual;
        if (incremento > 0) {
            validateSaldoDisponivel(incremento);
        }

        metas.setValorMeta(valorMeta);
        metas.setValor(novoValorGuardado);
        metas.setMetaCategoria(dto.metaCategoria());

        return repository.save(metas);
    }

    public Metas registrarEntrada(Long id, MovimentacaoMetaRequestDto dto) {
        var meta = BuscarPorId(id);
        double valor = positive(dto.valor(), "O valor para guardar deve ser maior que zero.");
        double novoValor = safe(meta.getValor()) + valor;
        validateValorGuardado(safe(meta.getValorMeta()), novoValor);
        validateSaldoDisponivel(valor);

        meta.setValor(novoValor);
        var saved = repository.save(meta);
        movimentacaoRepository.save(new MovimentacaoMeta(
            saved,
            TipoMovimentacaoMeta.ENTRADA,
            valor,
            dto.data() == null ? LocalDate.now() : dto.data(),
            normalizeDescricao(dto.descricao(), "Dinheiro guardado")
        ));
        return saved;
    }

    public Metas registrarSaida(Long id, MovimentacaoMetaRequestDto dto) {
        var meta = BuscarPorId(id);
        double valor = positive(dto.valor(), "O valor para retirar deve ser maior que zero.");
        double valorAtual = safe(meta.getValor());
        if (valor > valorAtual) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor de saída não pode ser maior que o guardado.");
        }

        meta.setValor(valorAtual - valor);
        var saved = repository.save(meta);
        movimentacaoRepository.save(new MovimentacaoMeta(
            saved,
            TipoMovimentacaoMeta.SAIDA,
            valor,
            dto.data() == null ? LocalDate.now() : dto.data(),
            normalizeDescricao(dto.descricao(), "Dinheiro retirado")
        ));
        return saved;
    }

    public List<MovimentacaoMetaResponseDto> movimentacoes(Long id) {
        BuscarPorId(id);
        return movimentacaoRepository.findByMetaIdOrderByDataDescIdDesc(id).stream()
            .map(MovimentacaoMetaResponseDto::from)
            .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        var metas = BuscarPorId(id);
        repository.delete(metas);
    }

    private double positive(Double value, String message) {
        if (value == null || value <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        return value;
    }

    private double nonNegative(Double value, String message) {
        if (value == null) {
            return 0;
        }
        if (value < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        return value;
    }

    private void validateValorGuardado(double valorMeta, double valorGuardado) {
        if (valorGuardado > valorMeta) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor guardado não pode ser maior que a meta.");
        }
    }

    private void validateSaldoDisponivel(double valorParaGuardar) {
        if (valorParaGuardar <= 0) {
            return;
        }

        if (valorParaGuardar > saldoService.saldoDisponivel()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor maior que o saldo disponível.");
        }
    }

    private double safe(Double value) {
        return value == null ? 0 : value;
    }

    private String normalizeDescricao(String descricao, String fallback) {
        if (descricao == null || descricao.isBlank()) {
            return fallback;
        }
        return descricao.trim();
    }
}

