package br.com.projeto.shared.processing;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório JPA para resultados de processamento.
 */
public interface ProcessingResultRepository extends JpaRepository<ProcessingResultEntity, UUID> {
}
