package aa.trusov.keyDispatcher.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "profileOpenssl")
public class ProfileOpenssl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(message = "Название профиля должно содержать более 1 символа и быть уникальным", min = 1)
    private String name;

    @OneToMany(mappedBy = "profileOpenssl", fetch = FetchType.LAZY)
    private Collection<Pairkeys> pairkeys;

    private String description;

    private String configFileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Pairkeys> getPairkeys() {
        return pairkeys;
    }

    public void setPairkeys(Collection<Pairkeys> pairkeys) {
        this.pairkeys = pairkeys;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

}
