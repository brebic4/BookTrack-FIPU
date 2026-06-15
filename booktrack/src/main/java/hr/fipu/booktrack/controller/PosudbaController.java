package hr.fipu.booktrack.controller;

import hr.fipu.booktrack.dto.PosudbaRequest;
import hr.fipu.booktrack.service.KnjigaService;
import hr.fipu.booktrack.service.PosudbaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posudbe")
public class PosudbaController {

    private final PosudbaService posudbaService;
    private final KnjigaService knjigaService;

    public PosudbaController(PosudbaService posudbaService,
                             KnjigaService knjigaService) {
        this.posudbaService = posudbaService;
        this.knjigaService = knjigaService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("posudbe", posudbaService.findAll());
        model.addAttribute("posudbaService", posudbaService);
        return "posudbe/lista";
    }

    @GetMapping("/nova")
    public String showCreateForm(@RequestParam(required = false) Long knjigaId, Model model) {
        PosudbaRequest request = new PosudbaRequest();

        if (knjigaId != null) {
            request.setKnjigaId(knjigaId);
        }

        model.addAttribute("posudbaRequest", request);
        model.addAttribute("knjige", knjigaService.findAll());
        return "posudbe/forma";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("posudbaRequest") PosudbaRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("knjige", knjigaService.findAll());
            return "posudbe/forma";
        }

        try {
            posudbaService.create(request);
            redirectAttributes.addFlashAttribute("successMessage", "Knjiga je uspješno posuđena.");
            return "redirect:/posudbe";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("knjige", knjigaService.findAll());
            return "posudbe/forma";
        }
    }

    @PostMapping("/{id}/vrati")
    public String vratiKnjigu(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            posudbaService.vratiKnjigu(id);
            redirectAttributes.addFlashAttribute("successMessage", "Knjiga je uspješno vraćena.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/posudbe";
    }
}