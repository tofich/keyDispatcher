package aa.trusov.keyDispatcher.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "templateOpenssl")
public class TemplateConfigOpenssl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Length(message = "Имя профила должно содержать более 1 символа",min = 1)
    private String name;
    @Length(message = "Путь к файлу конфигурации OpenSSL должен содержать более 1 символа",min = 1)
    private String pathToConfigOpenssl;

}
