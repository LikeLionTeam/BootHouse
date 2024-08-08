package likelion.eight.domain.bootcamp.controller;

import likelion.eight.domain.bootcamp.controller.model.BootcampCreateRequest;
import likelion.eight.domain.bootcamp.model.Bootcamp;
import likelion.eight.domain.bootcamp.service.BootcampService;
import likelion.eight.domain.bootcamp.service.port.BootcampRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bootcamps")
@RequiredArgsConstructor
@Slf4j
public class BootcampController {
    private final BootcampService bootcampService;
    @GetMapping("/create")
    public String showRegisterForm(
            @ModelAttribute(name = "request") BootcampCreateRequest request){
        return "bootcamp/createForm";
    }

    @PostMapping("/create")
    public String createBootcamp(
            @ModelAttribute(name = "request") BootcampCreateRequest request
    ){
        log.info("request :: {}", request.toString());
        log.info("request -- file :: {}", request.getFile().getOriginalFilename());

        bootcampService.createBootcamp(request);

        return "redirect:/bootcamps";
    }

    @GetMapping
    public String showAllBootcamps(Model model){
        List<Bootcamp> bootcamps = bootcampService.findAllBootcamps();
        model.addAttribute("bootcamps", bootcamps);

        return "bootcamp/list";
    }
}
