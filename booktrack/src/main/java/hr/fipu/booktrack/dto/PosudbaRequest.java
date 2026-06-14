package hr.fipu.booktrack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PosudbaRequest {

    @NotNull(message = "Knjiga mora biti odabrana.")
    private Long knjigaId;

    @NotBlank(message = "Ime korisnika je obavezno.")
    @Size(max = 100, message = "Ime korisnika može imati najviše 100 znakova.")
    private String imeKorisnika;

    @NotBlank(message = "Email korisnika je obavezan.")
    @Email(message = "Email nije ispravnog formata.")
    @Size(max = 100, message = "Email može imati najviše 100 znakova.")
    private String emailKorisnika;

    public PosudbaRequest() {
    }

    public PosudbaRequest(Long knjigaId, String imeKorisnika, String emailKorisnika) {
        this.knjigaId = knjigaId;
        this.imeKorisnika = imeKorisnika;
        this.emailKorisnika = emailKorisnika;
    }

    public Long getKnjigaId() {
        return knjigaId;
    }

    public void setKnjigaId(Long knjigaId) {
        this.knjigaId = knjigaId;
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
}