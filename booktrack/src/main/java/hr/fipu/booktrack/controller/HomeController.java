package hr.fipu.booktrack.controller;

import hr.fipu.booktrack.service.PosudbaService;
import hr.fipu.booktrack.service.StatistikaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final StatistikaService statistikaService;
    private final PosudbaService posudbaService;

    public HomeController(StatistikaService statistikaService, PosudbaService posudbaService) {
        this.statistikaService = statistikaService;
        this.posudbaService = posudbaService;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("brojKnjiga", statistikaService.brojKnjiga());
        model.addAttribute("brojDostupnih", statistikaService.brojDostupnihKnjiga());
        model.addAttribute("brojPosudenih", statistikaService.brojPosudenihKnjiga());
        model.addAttribute("brojKategorija", statistikaService.brojKategorija());
        model.addAttribute("brojAktivnihPosudbi", statistikaService.brojAktivnihPosudbi());
        model.addAttribute("zadnjePosudbe", posudbaService.findLastFive());
        model.addAttribute("posudbaService", posudbaService);

        return "index";
    }
}