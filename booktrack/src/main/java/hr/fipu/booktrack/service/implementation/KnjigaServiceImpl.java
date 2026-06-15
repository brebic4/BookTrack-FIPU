package hr.fipu.booktrack.service.implementation;

import hr.fipu.booktrack.dto.KnjigaRequest;
import hr.fipu.booktrack.model.Kategorija;
import hr.fipu.booktrack.model.Knjiga;
import hr.fipu.booktrack.model.StatusPosudbe;
import hr.fipu.booktrack.repository.KategorijaRepository;
import hr.fipu.booktrack.repository.KnjigaRepository;
import hr.fipu.booktrack.repository.PosudbaRepository;
import hr.fipu.booktrack.service.KnjigaService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnjigaServiceImpl implements KnjigaService {

    private final KnjigaRepository knjigaRepository;
    private final KategorijaRepository kategorijaRepository;
    private final PosudbaRepository posudbaRepository;

    public KnjigaServiceImpl(KnjigaRepository knjigaRepository,
                             KategorijaRepository kategorijaRepository,
                             PosudbaRepository posudbaRepository) {
        this.knjigaRepository = knjigaRepository;
        this.kategorijaRepository = kategorijaRepository;
        this.posudbaRepository = posudbaRepository;
    }

    @Override
    public List<Knjiga> findAll() {
        return knjigaRepository.findAll(Sort.by("naslov").ascending());
    }

    @Override
    public Knjiga findById(Long id) {
        return knjigaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knjiga nije pronađena."));
    }

    @Override
    public Knjiga create(KnjigaRequest request) {
        Kategorija kategorija = kategorijaRepository.findById(request.getKategorijaId())
                .orElseThrow(() -> new RuntimeException("Kategorija nije pronađena."));

        Knjiga knjiga = new Knjiga();
        knjiga.setNaslov(request.getNaslov());
        knjiga.setAutor(request.getAutor());
        knjiga.setGodinaIzdanja(request.getGodinaIzdanja());
        knjiga.setIsbn(request.getIsbn());
        knjiga.setKategorija(kategorija);
        knjiga.setDostupna(true);

        return knjigaRepository.save(knjiga);
    }

    @Override
    public Knjiga update(Long id, KnjigaRequest request) {
        Knjiga knjiga = findById(id);

        Kategorija kategorija = kategorijaRepository.findById(request.getKategorijaId())
                .orElseThrow(() -> new RuntimeException("Kategorija nije pronađena."));

        knjiga.setNaslov(request.getNaslov());
        knjiga.setAutor(request.getAutor());
        knjiga.setGodinaIzdanja(request.getGodinaIzdanja());
        knjiga.setIsbn(request.getIsbn());
        knjiga.setKategorija(kategorija);

        return knjigaRepository.save(knjiga);
    }

    @Override
    public void delete(Long id) {
        Knjiga knjiga = findById(id);

        boolean imaAktivnuPosudbu = posudbaRepository.existsByKnjigaIdAndStatus(id, StatusPosudbe.AKTIVNA);

        if (imaAktivnuPosudbu) {
            throw new RuntimeException("Knjigu nije moguće obrisati jer ima aktivnu posudbu.");
        }

        knjigaRepository.delete(knjiga);
    }

    @Override
    public List<Knjiga> searchAndFilter(String keyword, Long kategorijaId, Boolean dostupna, String sort) {
        Sort sorting = createSort(sort);

        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasKategorija = kategorijaId != null;
        boolean hasDostupna = dostupna != null;

        if (hasKeyword) {
            return knjigaRepository.findByNaslovContainingIgnoreCaseOrAutorContainingIgnoreCase(
                    keyword,
                    keyword,
                    sorting
            );
        }

        if (hasKategorija && hasDostupna) {
            return knjigaRepository.findByKategorijaIdAndDostupna(kategorijaId, dostupna, sorting);
        }

        if (hasKategorija) {
            return knjigaRepository.findByKategorijaId(kategorijaId, sorting);
        }

        if (hasDostupna) {
            return knjigaRepository.findByDostupna(dostupna, sorting);
        }

        return knjigaRepository.findAll(sorting);
    }

    private Sort createSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by("naslov").ascending();
        }

        return switch (sort) {
            case "naslov_desc" -> Sort.by("naslov").descending();
            case "godina_asc" -> Sort.by("godinaIzdanja").ascending();
            case "godina_desc" -> Sort.by("godinaIzdanja").descending();
            default -> Sort.by("naslov").ascending();
        };
    }

    @Override
    public KnjigaRequest toRequest(Knjiga knjiga) {
        return new KnjigaRequest(
                knjiga.getNaslov(),
                knjiga.getAutor(),
                knjiga.getGodinaIzdanja(),
                knjiga.getIsbn(),
                knjiga.getKategorija().getId()
        );
    }
}