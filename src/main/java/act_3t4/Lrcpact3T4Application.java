package act_3t4;

import act_3t4.entity.Categoria;
import act_3t4.entity.Materia;
import act_3t4.repository.CategoriaRepository;
import act_3t4.repository.MateriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lrcpact3T4Application {
    public static void main(String[] args) {
        SpringApplication.run(Lrcpact3T4Application.class, args);
    }

    // Solamente agrega ejemplos la primera vez, cuando no existen categorias.
    @Bean
    CommandLineRunner inicializarDatos(CategoriaRepository categorias, MateriaRepository materias) {
        return args -> {
            if (categorias.count() == 0) {
                Categoria programacion = categorias.save(new Categoria("Programacion", "Desarrollo de software"));
                Categoria gestion = categorias.save(new Categoria("Gestion", "Planeacion y administracion"));
                Categoria ciencias = categorias.save(new Categoria("Ciencias", "Fundamentos cientificos"));
                materias.save(new Materia("Programacion web", 7, "Desarrollo de aplicaciones web", programacion));
                materias.save(new Materia("Gestion de Proyectos de Software", 7, "Administracion de proyectos", gestion));
                materias.save(new Materia("Lenguajes y Automatas II", 7, "Compiladores y automatas", programacion));
            }
        };
    }
}
