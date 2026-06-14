package hr.fipu.booktrack.repository;

import hr.fipu.booktrack.model.Kategorija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {

    Optional<Kategorija> findByNaziv(String naziv);
    boolean existsByNaziv(String naziv);
}