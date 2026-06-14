package hr.fipu.booktrack.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "knjige")
public class Knjiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String naslov;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(name = "godina_izdanja")
    private Integer godinaIzdanja;

    @Column(length = 30)
    private String isbn;

    @Column(nullable = false)
    private Boolean dostupna = true;

    @ManyToOne
    @JoinColumn(name = "kategorija_id", nullable = false)
    private Kategorija kategorija;

    @OneToMany(mappedBy = "knjiga")
    private List<Posudba> posudbe = new ArrayList<>();

    public Knjiga() {
    }

    public Knjiga(String naslov, String autor, Integer godinaIzdanja, String isbn, Kategorija kategorija) {
        this.naslov = naslov;
        this.autor = autor;
        this.godinaIzdanja = godinaIzdanja;
        this.isbn = isbn;
        this.kategorija = kategorija;
        this.dostupna = true;
    }

    public Long getId() {
        return id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public void setGodinaIzdanja(Integer godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Boolean getDostupna() {
        return dostupna;
    }

    public void setDostupna(Boolean dostupna) {
        this.dostupna = dostupna;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public List<Posudba> getPosudbe() {
        return posudbe;
    }

    public void setPosudbe(List<Posudba> posudbe) {
        this.posudbe = posudbe;
    }
}