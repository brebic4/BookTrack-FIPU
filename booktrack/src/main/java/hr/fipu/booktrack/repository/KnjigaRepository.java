package hr.fipu.booktrack.repository;

import hr.fipu.booktrack.model.Knjiga;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnjigaRepository extends JpaRepository<Knjiga, Long> {

    List<Knjiga> findByNaslovContainingIgnoreCaseOrAutorContainingIgnoreCase(String naslov, String autor, Sort sort);
    List<Knjiga> findByKategorijaId(Long kategorijaId, Sort sort);
    List<Knjiga> findByDostupna(Boolean dostupna, Sort sort);
    List<Knjiga> findByKategorijaIdAndDostupna(Long kategorijaId, Boolean dostupna, Sort sort);
    long countByDostupna(Boolean dostupna);
}