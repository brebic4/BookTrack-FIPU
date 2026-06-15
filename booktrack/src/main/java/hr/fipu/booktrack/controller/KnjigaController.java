package hr.fipu.booktrack.controller;

import hr.fipu.booktrack.dto.KnjigaRequest;
import hr.fipu.booktrack.service.KategorijaService;
import hr.fipu.booktrack.service.KnjigaService;
import hr.fipu.booktrack.service.PosudbaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/knjige")
public class KnjigaController {

    private final KnjigaService knjigaService;
    private final KategorijaService kategorijaService;
    private final PosudbaService posudbaService;

    public KnjigaController(KnjigaService knjigaService,
                            KategorijaService kategorijaService,
                            PosudbaService posudbaService) {
        this.knjigaService = knjigaService;
        this.kategorijaService = kategorijaService;
        this.posudbaService = posudbaService;
    }

    @GetMapping
    public String findAll(@RequestParam(required = false) String keyword,
                          @RequestParam(required = false) Long kategorijaId,
                          @RequestParam(required = false) Boolean dostupna,
                          @RequestParam(required = false) String sort,
                          Model model) {

        model.addAttribute("knjige", knjigaService.searchAndFilter(keyword, kategorijaId, dostupna, sort));
        model.addAttribute("kategorije", kategorijaService.findAll());

        model.addAttribute("keyword", keyword);
        model.addAttribute("kategorijaId", kategorijaId);
        model.addAttribute("dostupna", dostupna);
        model.addAttribute("sort", sort);

        return "knjige/lista";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        var knjiga = knjigaService.findById(id);

        model.addAttribute("knjiga", knjiga);
        model.addAttribute("posudbe", posudbaService.findByKnjigaId(id));
        model.addAttribute("posudbaService", posudbaService);

        return "knjige/detalji";
    }

    @GetMapping("/nova")
    public String showCreateForm(Model model) {
        model.addAttribute("knjigaRequest", new KnjigaRequest());
        model.addAttribute("kategorije", kategorijaService.findAll());
        model.addAttribute("title", "Dodaj knjigu");
        model.addAttribute("action", "/knjige");
        return "knjige/forma";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("knjigaRequest") KnjigaRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("kategorije", kategorijaService.findAll());
            model.addAttribute("title", "Dodaj knjigu");
            model.addAttribute("action", "/knjige");
            return "knjige/forma";
        }

        try {
            knjigaService.create(request);
            redirectAttributes.addFlashAttribute("successMessage", "Knjiga je uspješno dodana.");
            return "redirect:/knjige";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("kategorije", kategorijaService.findAll());
            model.addAttribute("title", "Dodaj knjigu");
            model.addAttribute("action", "/knjige");
            return "knjige/forma";
        }
    }

    @GetMapping("/{id}/uredi")
    public String showEditForm(@PathVariable Long id, Model model) {
        var knjiga = knjigaService.findById(id);

        model.addAttribute("knjigaRequest", knjigaService.toRequest(knjiga));
        model.addAttribute("kategorije", kategorijaService.findAll());
        model.addAttribute("title", "Uredi knjigu");
        model.addAttribute("action", "/knjige/" + id);
        return "knjige/forma";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("knjigaRequest") KnjigaRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("kategorije", kategorijaService.findAll());
            model.addAttribute("title", "Uredi knjigu");
            model.addAttribute("action", "/knjige/" + id);
            return "knjige/forma";
        }

        try {
            knjigaService.update(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Knjiga je uspješno uređena.");
            return "redirect:/knjige";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("kategorije", kategorijaService.findAll());
            model.addAttribute("title", "Uredi knjigu");
            model.addAttribute("action", "/knjige/" + id);
            return "knjige/forma";
        }
    }

    @PostMapping("/{id}/obrisi")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            knjigaService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Knjiga je uspješno obrisana.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/knjige";
    }
}