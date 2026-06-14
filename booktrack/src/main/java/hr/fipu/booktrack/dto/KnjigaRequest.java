package hr.fipu.booktrack.dto;

import jakarta.validation.constraints.*;

public class KnjigaRequest {

    @NotBlank(message = "Naslov knjige je obavezan.")
    @Size(max = 150, message = "Naslov može imati najviše 150 znakova.")
    private String naslov;

    @NotBlank(message = "Autor knjige je obavezan.")
    @Size(max = 100, message = "Autor može imati najviše 100 znakova.")
    private String autor;

    @NotNull(message = "Godina izdanja je obavezna.")
    @Min(value = 1500, message = "Godina izdanja mora biti veća ili jednaka 1500.")
    @Max(value = 2026, message = "Godina izdanja ne može biti veća od 2026.")
    private Integer godinaIzdanja;

    @Size(max = 30, message = "ISBN može imati najviše 30 znakova.")
    private String isbn;

    @NotNull(message = "Kategorija mora biti odabrana.")
    private Long kategorijaId;

    public KnjigaRequest() {
    }

    public KnjigaRequest(String naslov, String autor, Integer godinaIzdanja, String isbn, Long kategorijaId) {
        this.naslov = naslov;
        this.autor = autor;
        this.godinaIzdanja = godinaIzdanja;
        this.isbn = isbn;
        this.kategorijaId = kategorijaId;
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

    public Long getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(Long kategorijaId) {
        this.kategorijaId = kategorijaId;
    }
}