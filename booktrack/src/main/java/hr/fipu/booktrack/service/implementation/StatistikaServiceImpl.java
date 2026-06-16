package hr.fipu.booktrack.service.implementation;

import hr.fipu.booktrack.model.Kategorija;
import hr.fipu.booktrack.model.StatusPosudbe;
import hr.fipu.booktrack.repository.KategorijaRepository;
import hr.fipu.booktrack.repository.KnjigaRepository;
import hr.fipu.booktrack.repository.PosudbaRepository;
import hr.fipu.booktrack.service.StatistikaService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class StatistikaServiceImpl implements StatistikaService {

    private final KnjigaRepository knjigaRepository;
    private final KategorijaRepository kategorijaRepository;
    private final PosudbaRepository posudbaRepository;

    public StatistikaServiceImpl(KnjigaRepository knjigaRepository,
                                 KategorijaRepository kategorijaRepository,
                                 PosudbaRepository posudbaRepository) {
        this.knjigaRepository = knjigaRepository;
        this.kategorijaRepository = kategorijaRepository;
        this.posudbaRepository = posudbaRepository;
    }

    @Override
    public long brojKnjiga() {
        return knjigaRepository.count();
    }

    @Override
    public long brojDostupnihKnjiga() {
        return knjigaRepository.countByDostupna(true);
    }

    @Override
    public long brojPosudenihKnjiga() {
        return knjigaRepository.countByDostupna(false);
    }

    @Override
    public long brojKategorija() {
        return kategorijaRepository.count();
    }

    @Override
    public long brojAktivnihPosudbi() {
        return posudbaRepository.countByStatus(StatusPosudbe.AKTIVNA);
    }

    @Override
    public Map<String, Long> brojKnjigaPoKategorijama() {
        Map<String, Long> rezultat = new LinkedHashMap<>();

        for (Kategorija kategorija : kategorijaRepository.findAll()) {
            rezultat.put(kategorija.getNaziv(), (long) kategorija.getKnjige().size());
        }

        return rezultat;
    }

    @Override
    public Map<String, Long> brojPosudbiPoKategorijama() {
        Map<String, Long> rezultat = new LinkedHashMap<>();

        for (Kategorija kategorija : kategorijaRepository.findAll()) {
            long brojPosudbi = kategorija.getKnjige()
                    .stream()
                    .flatMap(knjiga -> knjiga.getPosudbe().stream())
                    .count();

            rezultat.put(kategorija.getNaziv(), brojPosudbi);
        }

        return rezultat;
    }

    @Override
    public String najposudivanijaKnjiga() {
        return knjigaRepository.findAll()
                .stream()
                .max((k1, k2) -> Integer.compare(k1.getPosudbe().size(), k2.getPosudbe().size()))
                .filter(knjiga -> !knjiga.getPosudbe().isEmpty())
                .map(knjiga -> knjiga.getNaslov() + " (" + knjiga.getPosudbe().size() + " posudbi)")
                .orElse("Nema posudbi");
    }

    @Override
    public String najpopularnijaKategorija() {
        return kategorijaRepository.findAll()
                .stream()
                .max((k1, k2) -> {
                    int brojPosudbiK1 = k1.getKnjige()
                            .stream()
                            .mapToInt(knjiga -> knjiga.getPosudbe().size())
                            .sum();

                    int brojPosudbiK2 = k2.getKnjige()
                            .stream()
                            .mapToInt(knjiga -> knjiga.getPosudbe().size())
                            .sum();

                    return Integer.compare(brojPosudbiK1, brojPosudbiK2);
                })
                .map(kategorija -> {
                    int brojPosudbi = kategorija.getKnjige()
                            .stream()
                            .mapToInt(knjiga -> knjiga.getPosudbe().size())
                            .sum();

                    if (brojPosudbi == 0) {
                        return "Nema posudbi";
                    }

                    return kategorija.getNaziv() + " (" + brojPosudbi + " posudbi)";
                })
                .orElse("Nema kategorija");
    }

    @Override
    public double prosjecanBrojPosudbiPoKnjizi() {
        long brojKnjiga = knjigaRepository.count();
        long brojPosudbi = posudbaRepository.count();

        if (brojKnjiga == 0) {
            return 0;
        }

        return (double) brojPosudbi / brojKnjiga;
    }
}