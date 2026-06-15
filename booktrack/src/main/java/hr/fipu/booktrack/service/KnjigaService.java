package hr.fipu.booktrack.service;

import hr.fipu.booktrack.dto.KnjigaRequest;
import hr.fipu.booktrack.model.Knjiga;

import java.util.List;

public interface KnjigaService {

    List<Knjiga> findAll();
    Knjiga findById(Long id);
    Knjiga create(KnjigaRequest request);
    Knjiga update(Long id, KnjigaRequest request);
    void delete(Long id);
    List<Knjiga> searchAndFilter(String keyword, Long kategorijaId, Boolean dostupna, String sort);
    KnjigaRequest toRequest(Knjiga knjiga);
}