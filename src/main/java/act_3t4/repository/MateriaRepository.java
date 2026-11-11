package act_3t4.repository;

import act_3t4.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findAllByOrderByIdAsc();
    List<Materia> findByCategoria_IdOrderByIdAsc(Long categoriaId);
}
