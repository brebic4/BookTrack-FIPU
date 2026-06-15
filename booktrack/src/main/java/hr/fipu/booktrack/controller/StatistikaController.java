package hr.fipu.booktrack.controller;

import hr.fipu.booktrack.service.StatistikaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatistikaController {

    private final StatistikaService statistikaService;

    public StatistikaController(StatistikaService statistikaService) {
        this.statistikaService = statistikaService;
    }

    @GetMapping("/statistika")
    public String index(Model model) {
        model.addAttribute("brojKnjiga", statistikaService.brojKnjiga());
        model.addAttribute("brojDostupnih", statistikaService.brojDostupnihKnjiga());
        model.addAttribute("brojPosudenih", statistikaService.brojPosudenihKnjiga());
        model.addAttribute("brojKategorija", statistikaService.brojKategorija());
        model.addAttribute("brojAktivnihPosudbi", statistikaService.brojAktivnihPosudbi());
        model.addAttribute("knjigePoKategorijama", statistikaService.brojKnjigaPoKategorijama());
        model.addAttribute("posudbePoKategorijama", statistikaService.brojPosudbiPoKategorijama());

        return "statistika/index";
    }
}