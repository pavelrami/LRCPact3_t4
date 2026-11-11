package act_3t4.controller;

import act_3t4.dto.CategoriaRespuesta;
import act_3t4.dto.MateriaDTO;
import act_3t4.service.CategoriaService;
import act_3t4.service.MateriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MateriaMvcController {
    private final MateriaService materias;
    private final CategoriaService categorias;
    @Value("${app.nombre}")
    private String nombreAplicacion;

    public MateriaMvcController(MateriaService materias, CategoriaService categorias) {
        this.materias = materias;
        this.categorias = categorias;
    }

    @ModelAttribute("nombreAplicacion")
    public String nombreAplicacion() { return nombreAplicacion; }

    @ModelAttribute("categorias")
    public List<CategoriaRespuesta> categorias() { return categorias.listar(); }

    @GetMapping("/")
    public String inicio() { return "redirect:/materias"; }

    @GetMapping("/materias")
    public String listado(@RequestParam(required = false) Long categoriaId, Model model) {
        model.addAttribute("materias", materias.listar(categoriaId));
        model.addAttribute("categoriaSeleccionada", categoriaId);
        return "materias";
    }

    @GetMapping("/materias/nueva")
    public String nueva(Model model) {
        model.addAttribute("materia", new MateriaDTO());
        model.addAttribute("modoEditar", false);
        return "formulario";
    }

    @GetMapping("/materias/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("materia", materias.paraEditar(id));
        model.addAttribute("modoEditar", true);
        return "formulario";
    }

    @PostMapping("/materias/guardar")
    public String guardar(@ModelAttribute("materia") MateriaDTO dto, Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            if (dto.getId() == null) {
                materias.crear(dto);
                redirectAttributes.addFlashAttribute("mensaje", "Materia creada y guardada en MySQL");
            } else {
                materias.actualizar(dto.getId(), dto);
                redirectAttributes.addFlashAttribute("mensaje", "Materia actualizada en MySQL");
            }
            return "redirect:/materias";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("modoEditar", dto.getId() != null);
            return "formulario";
        }
    }

    @GetMapping("/materias/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("materia", materias.obtener(id));
        return "detalle";
    }

    @PostMapping("/materias/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        materias.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Materia eliminada de la base de datos");
        return "redirect:/materias";
    }
}
