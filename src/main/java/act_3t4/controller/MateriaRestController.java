package act_3t4.controller;

import act_3t4.dto.MateriaDTO;
import act_3t4.dto.MateriaRespuesta;
import act_3t4.service.MateriaService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materias")
public class MateriaRestController {
    private final MateriaService service;
    public MateriaRestController(MateriaService service) { this.service = service; }

    @GetMapping
    public List<MateriaRespuesta> listar(@RequestParam(required = false) Long categoriaId) {
        return service.listar(categoriaId);
    }

    @GetMapping("/{id}")
    public MateriaRespuesta detalle(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping
    public ResponseEntity<MateriaRespuesta> crear(@RequestBody MateriaDTO dto) {
        MateriaRespuesta creada = service.crear(dto);
        return ResponseEntity.created(URI.create("/api/materias/" + creada.id())).body(creada);
    }

    @PutMapping("/{id}")
    public MateriaRespuesta actualizar(@PathVariable Long id, @RequestBody MateriaDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
