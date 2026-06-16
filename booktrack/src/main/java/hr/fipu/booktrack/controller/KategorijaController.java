package hr.fipu.booktrack.controller;

import hr.fipu.booktrack.dto.KategorijaRequest;
import hr.fipu.booktrack.service.KategorijaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kategorije")
public class KategorijaController {

    private final KategorijaService kategorijaService;

    public KategorijaController(KategorijaService kategorijaService) {
        this.kategorijaService = kategorijaService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("kategorije", kategorijaService.findAll());
        model.addAttribute("activePage", "kategorije");

        return "kategorije/lista";
    }

    @GetMapping("/nova")
    public String showCreateForm(Model model) {
        model.addAttribute("kategorijaRequest", new KategorijaRequest());
        model.addAttribute("title", "Dodaj kategoriju");
        model.addAttribute("action", "/kategorije");
        model.addAttribute("activePage", "kategorije");

        return "kategorije/forma";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("kategorijaRequest") KategorijaRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Dodaj kategoriju");
            model.addAttribute("action", "/kategorije");
            return "kategorije/forma";
        }

        try {
            kategorijaService.create(request);
            redirectAttributes.addFlashAttribute("successMessage", "Kategorija je uspješno dodana.");
            return "redirect:/kategorije";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("title", "Dodaj kategoriju");
            model.addAttribute("action", "/kategorije");
            return "kategorije/forma";
        }
    }

    @GetMapping("/{id}/uredi")
    public String showEditForm(@PathVariable Long id, Model model) {
        var kategorija = kategorijaService.findById(id);

        KategorijaRequest request = new KategorijaRequest(
                kategorija.getNaziv(),
                kategorija.getOpis()
        );

        model.addAttribute("kategorijaRequest", request);
        model.addAttribute("title", "Uredi kategoriju");
        model.addAttribute("action", "/kategorije/" + id);
        model.addAttribute("activePage", "kategorije");

        return "kategorije/forma";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("kategorijaRequest") KategorijaRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Uredi kategoriju");
            model.addAttribute("action", "/kategorije/" + id);
            return "kategorije/forma";
        }

        try {
            kategorijaService.update(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Kategorija je uspješno uređena.");
            return "redirect:/kategorije";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("title", "Uredi kategoriju");
            model.addAttribute("action", "/kategorije/" + id);
            return "kategorije/forma";
        }
    }

    @PostMapping("/{id}/obrisi")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            kategorijaService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Kategorija je uspješno obrisana.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/kategorije";
    }
}