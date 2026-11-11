package act_3t4.service;

import act_3t4.dto.CategoriaRespuesta;
import act_3t4.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<CategoriaRespuesta> listar() {
        return repo.findAllByOrderByNombreAsc().stream()
                .map(c -> new CategoriaRespuesta(c.getId(), c.getNombre(), c.getDescripcion()))
                .toList();
    }
}
