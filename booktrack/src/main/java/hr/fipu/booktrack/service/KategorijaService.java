package hr.fipu.booktrack.service;

import hr.fipu.booktrack.dto.KategorijaRequest;
import hr.fipu.booktrack.model.Kategorija;

import java.util.List;

public interface KategorijaService {

    List<Kategorija> findAll();
    Kategorija findById(Long id);
    Kategorija create(KategorijaRequest request);
    Kategorija update(Long id, KategorijaRequest request);
    void delete(Long id);
}