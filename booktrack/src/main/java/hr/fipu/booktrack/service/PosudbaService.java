package hr.fipu.booktrack.service;

import hr.fipu.booktrack.dto.PosudbaRequest;
import hr.fipu.booktrack.model.Posudba;

import java.util.List;

public interface PosudbaService {

    List<Posudba> findAll();
    Posudba findById(Long id);
    List<Posudba> findByKnjigaId(Long knjigaId);
    Posudba create(PosudbaRequest request);
    Posudba vratiKnjigu(Long posudbaId);
    boolean kasni(Posudba posudba);
}