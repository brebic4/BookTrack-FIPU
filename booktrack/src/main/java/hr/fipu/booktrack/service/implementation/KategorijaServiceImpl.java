package hr.fipu.booktrack.service.implementation;

import hr.fipu.booktrack.dto.KategorijaRequest;
import hr.fipu.booktrack.model.Kategorija;
import hr.fipu.booktrack.repository.KategorijaRepository;
import hr.fipu.booktrack.service.KategorijaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorijaServiceImpl implements KategorijaService {

    private final KategorijaRepository kategorijaRepository;

    public KategorijaServiceImpl(KategorijaRepository kategorijaRepository) {
        this.kategorijaRepository = kategorijaRepository;
    }

    @Override
    public List<Kategorija> findAll() {
        return kategorijaRepository.findAll();
    }

    @Override
    public Kategorija findById(Long id) {
        return kategorijaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategorija nije pronađena."));
    }

    @Override
    public Kategorija create(KategorijaRequest request) {
        if (kategorijaRepository.existsByNaziv(request.getNaziv())) {
            throw new RuntimeException("Kategorija s tim nazivom već postoji.");
        }

        Kategorija kategorija = new Kategorija();
        kategorija.setNaziv(request.getNaziv());
        kategorija.setOpis(request.getOpis());

        return kategorijaRepository.save(kategorija);
    }

    @Override
    public Kategorija update(Long id, KategorijaRequest request) {
        Kategorija kategorija = findById(id);

        kategorija.setNaziv(request.getNaziv());
        kategorija.setOpis(request.getOpis());

        return kategorijaRepository.save(kategorija);
    }

    @Override
    public void delete(Long id) {
        Kategorija kategorija = findById(id);

        if (!kategorija.getKnjige().isEmpty()) {
            throw new RuntimeException("Kategoriju nije moguće obrisati jer sadrži knjige.");
        }

        kategorijaRepository.delete(kategorija);
    }
}