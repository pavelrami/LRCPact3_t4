package act_3t4.dto;

public record MateriaRespuesta(Long id, String nombre, Integer semestre,
                               String descripcion, Long categoriaId,
                               String categoriaNombre) { }
