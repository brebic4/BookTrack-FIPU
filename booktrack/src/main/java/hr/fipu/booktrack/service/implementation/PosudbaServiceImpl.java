package hr.fipu.booktrack.service.implementation;

import hr.fipu.booktrack.dto.PosudbaRequest;
import hr.fipu.booktrack.model.Knjiga;
import hr.fipu.booktrack.model.Posudba;
import hr.fipu.booktrack.model.StatusPosudbe;
import hr.fipu.booktrack.repository.KnjigaRepository;
import hr.fipu.booktrack.repository.PosudbaRepository;
import hr.fipu.booktrack.service.PosudbaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PosudbaServiceImpl implements PosudbaService {

    private static final int BROJ_DANA_POSUDBE = 14;

    private final PosudbaRepository posudbaRepository;
    private final KnjigaRepository knjigaRepository;

    public PosudbaServiceImpl(PosudbaRepository posudbaRepository,
                              KnjigaRepository knjigaRepository) {
        this.posudbaRepository = posudbaRepository;
        this.knjigaRepository = knjigaRepository;
    }

    @Override
    public List<Posudba> findAll() {
        return posudbaRepository.findAll();
    }

    @Override
    public Posudba findById(Long id) {
        return posudbaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Posudba nije pronađena."));
    }

    @Override
    public List<Posudba> findByKnjigaId(Long knjigaId) {
        return posudbaRepository.findByKnjigaId(knjigaId);
    }

    @Override
    public Posudba create(PosudbaRequest request) {
        Knjiga knjiga = knjigaRepository.findById(request.getKnjigaId())
                .orElseThrow(() -> new RuntimeException("Knjiga nije pronađena."));

        if (!knjiga.getDostupna()) {
            throw new RuntimeException("Knjiga trenutno nije dostupna za posudbu.");
        }

        boolean imaAktivnuPosudbu = posudbaRepository.existsByKnjigaIdAndStatus(
                knjiga.getId(),
                StatusPosudbe.AKTIVNA
        );

        if (imaAktivnuPosudbu) {
            throw new RuntimeException("Knjiga već ima aktivnu posudbu.");
        }

        LocalDate danas = LocalDate.now();

        Posudba posudba = new Posudba();
        posudba.setImeKorisnika(request.getImeKorisnika());
        posudba.setEmailKorisnika(request.getEmailKorisnika());
        posudba.setDatumPosudbe(danas);
        posudba.setRokPovrata(danas.plusDays(BROJ_DANA_POSUDBE));
        posudba.setStatus(StatusPosudbe.AKTIVNA);
        posudba.setKnjiga(knjiga);

        knjiga.setDostupna(false);
        knjigaRepository.save(knjiga);

        return posudbaRepository.save(posudba);
    }

    @Override
    public Posudba vratiKnjigu(Long posudbaId) {
        Posudba posudba = findById(posudbaId);

        if (posudba.getStatus() == StatusPosudbe.VRACENA) {
            throw new RuntimeException("Knjiga je već vraćena.");
        }

        posudba.setStatus(StatusPosudbe.VRACENA);
        posudba.setDatumPovrata(LocalDate.now());

        Knjiga knjiga = posudba.getKnjiga();
        knjiga.setDostupna(true);

        knjigaRepository.save(knjiga);

        return posudbaRepository.save(posudba);
    }

    @Override
    public boolean kasni(Posudba posudba) {
        return posudba.getStatus() == StatusPosudbe.AKTIVNA
                && LocalDate.now().isAfter(posudba.getRokPovrata());
    }
}