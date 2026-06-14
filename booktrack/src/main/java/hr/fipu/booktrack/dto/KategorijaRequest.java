package hr.fipu.booktrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class KategorijaRequest {

    @NotBlank(message = "Naziv kategorije je obavezan.")
    @Size(max = 100, message = "Naziv kategorije može imati najviše 100 znakova.")
    private String naziv;

    @Size(max = 500, message = "Opis može imati najviše 500 znakova.")
    private String opis;

    public KategorijaRequest() {
    }

    public KategorijaRequest(String naziv, String opis) {
        this.naziv = naziv;
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}