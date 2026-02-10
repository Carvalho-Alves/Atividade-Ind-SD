package br.com.projeto.shared.processing;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório JPA de resultados de processamento.
 *
 * <p>Papel (Q3): suportar idempotência/deduplicação no ServerApp.
 * Responsabilidade: permitir buscar e gravar {@link ProcessingResultEntity} por {@code messageId}.
 *
 * <p>Observação: não há métodos customizados porque as operações necessárias para a disciplina
 * são cobertas pelos métodos padrão do {@link JpaRepository}.
 */
public interface ProcessingResultRepository extends JpaRepository<ProcessingResultEntity, UUID> {
}
