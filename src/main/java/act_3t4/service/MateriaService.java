package act_3t4.service;

import act_3t4.dto.MateriaDTO;
import act_3t4.dto.MateriaRespuesta;
import act_3t4.entity.Categoria;
import act_3t4.entity.Materia;
import act_3t4.repository.CategoriaRepository;
import act_3t4.repository.MateriaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MateriaService {
    private final MateriaRepository materias;
    private final CategoriaRepository categorias;

    public MateriaService(MateriaRepository materias, CategoriaRepository categorias) {
        this.materias = materias;
        this.categorias = categorias;
    }

    @Transactional(readOnly = true)
    public List<MateriaRespuesta> listar(Long categoriaId) {
        List<Materia> datos = (categoriaId == null)
                ? materias.findAllByOrderByIdAsc()
                : materias.findByCategoria_IdOrderByIdAsc(categoriaId);
        return datos.stream().map(this::respuesta).toList();
    }

    @Transactional(readOnly = true)
    public MateriaRespuesta obtener(Long id) {
        return respuesta(buscar(id));
    }

    @Transactional(readOnly = true)
    public MateriaDTO paraEditar(Long id) {
        Materia m = buscar(id);
        MateriaDTO d = new MateriaDTO();
        d.setId(m.getId());
        d.setNombre(m.getNombre());
        d.setSemestre(m.getSemestre());
        d.setDescripcion(m.getDescripcion());
        d.setCategoriaId(m.getCategoria().getId());
        return d;
    }

    @Transactional
    public MateriaRespuesta crear(MateriaDTO dto) {
        validar(dto);
        Categoria categoria = buscarCategoria(dto.getCategoriaId());
        Materia m = materias.save(new Materia(dto.getNombre().trim(), dto.getSemestre(),
                dto.getDescripcion(), categoria));
        return respuesta(m);
    }

    @Transactional
    public MateriaRespuesta actualizar(Long id, MateriaDTO dto) {
        validar(dto);
        Materia m = buscar(id);
        m.setNombre(dto.getNombre().trim());
        m.setSemestre(dto.getSemestre());
        m.setDescripcion(dto.getDescripcion());
        m.setCategoria(buscarCategoria(dto.getCategoriaId()));
        return respuesta(materias.save(m));
    }

    @Transactional
    public void eliminar(Long id) {
        Materia materia = buscar(id);
        materias.delete(materia);
    }

    private Materia buscar(Long id) {
        return materias.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe materia con ID " + id));
    }

    private Categoria buscarCategoria(Long id) {
        return categorias.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe categoria con ID " + id));
    }

    private void validar(MateriaDTO d) {
        if (d == null || d.getNombre() == null || d.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (d.getNombre().trim().length() > 140)
            throw new IllegalArgumentException("El nombre no debe superar 140 caracteres");
        if (d.getSemestre() == null || d.getSemestre() < 1 || d.getSemestre() > 12)
            throw new IllegalArgumentException("El semestre debe estar entre 1 y 12");
        if (d.getCategoriaId() == null)
            throw new IllegalArgumentException("Selecciona una categoria");
        if (d.getDescripcion() != null && d.getDescripcion().length() > 500)
            throw new IllegalArgumentException("La descripcion no debe superar 500 caracteres");
    }

    private MateriaRespuesta respuesta(Materia m) {
        return new MateriaRespuesta(m.getId(), m.getNombre(), m.getSemestre(),
                m.getDescripcion(), m.getCategoria().getId(), m.getCategoria().getNombre());
    }
}
