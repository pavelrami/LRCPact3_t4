-- Ejecuta DESPUES de iniciar la aplicacion (Hibernate crea las tablas).
USE lrcp_act3;
SHOW TABLES;
DESCRIBE categorias;
DESCRIBE materias;
SHOW CREATE TABLE materias;
SELECT m.id, m.nombre AS materia, c.nombre AS categoria,
       m.semestre, m.categoria_id
FROM materias AS m
INNER JOIN categorias AS c ON c.id = m.categoria_id
ORDER BY m.id;
