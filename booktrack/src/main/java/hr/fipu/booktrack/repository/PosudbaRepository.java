package hr.fipu.booktrack.repository;

import hr.fipu.booktrack.model.Posudba;
import hr.fipu.booktrack.model.StatusPosudbe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosudbaRepository extends JpaRepository<Posudba, Long> {

    List<Posudba> findByKnjigaId(Long knjigaId);
    List<Posudba> findByStatus(StatusPosudbe status);
    Optional<Posudba> findByKnjigaIdAndStatus(Long knjigaId, StatusPosudbe status);
    boolean existsByKnjigaIdAndStatus(Long knjigaId, StatusPosudbe status);
    long countByStatus(StatusPosudbe status);
}