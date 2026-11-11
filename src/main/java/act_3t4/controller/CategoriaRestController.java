package act_3t4.controller;

import act_3t4.dto.CategoriaRespuesta;
import act_3t4.service.CategoriaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {
    private final CategoriaService service;
    public CategoriaRestController(CategoriaService service) { this.service = service; }
    @GetMapping
    public List<CategoriaRespuesta> listar() { return service.listar(); }
}
