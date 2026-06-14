package hr.fipu.booktrack.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "posudbe")
public class Posudba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime_korisnika", nullable = false, length = 100)
    private String imeKorisnika;

    @Column(name = "email_korisnika", nullable = false, length = 100)
    private String emailKorisnika;

    @Column(name = "datum_posudbe", nullable = false)
    private LocalDate datumPosudbe;

    @Column(name = "rok_povrata", nullable = false)
    private LocalDate rokPovrata;

    @Column(name = "datum_povrata")
    private LocalDate datumPovrata;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPosudbe status;

    @ManyToOne
    @JoinColumn(name = "knjiga_id", nullable = false)
    private Knjiga knjiga;

    public Posudba() {
    }

    public Posudba(String imeKorisnika, String emailKorisnika, LocalDate datumPosudbe,
                   LocalDate rokPovrata, StatusPosudbe status, Knjiga knjiga) {
        this.imeKorisnika = imeKorisnika;
        this.emailKorisnika = emailKorisnika;
        this.datumPosudbe = datumPosudbe;
        this.rokPovrata = rokPovrata;
        this.status = status;
        this.knjiga = knjiga;
    }

    public Long getId() {
        return id;
    }

    public String getImeKorisnika() {
        return imeKorisnika;
    }

    public void setImeKorisnika(String imeKorisnika) {
        this.imeKorisnika = imeKorisnika;
    }

    public String getEmailKorisnika() {
        return emailKorisnika;
    }

    public void setEmailKorisnika(String emailKorisnika) {
        this.emailKorisnika = emailKorisnika;
    }

    public LocalDate getDatumPosudbe() {
        return datumPosudbe;
    }

    public void setDatumPosudbe(LocalDate datumPosudbe) {
        this.datumPosudbe = datumPosudbe;
    }

    public LocalDate getRokPovrata() {
        return rokPovrata;
    }

    public void setRokPovrata(LocalDate rokPovrata) {
        this.rokPovrata = rokPovrata;
    }

    public LocalDate getDatumPovrata() {
        return datumPovrata;
    }

    public void setDatumPovrata(LocalDate datumPovrata) {
        this.datumPovrata = datumPovrata;
    }

    public StatusPosudbe getStatus() {
        return status;
    }

    public void setStatus(StatusPosudbe status) {
        this.status = status;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public boolean kasni() {
        return status == StatusPosudbe.AKTIVNA && LocalDate.now().isAfter(rokPovrata);
    }
}
