package aa.trusov.keyDispatcher.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "pairkeys")
public class Pairkeys {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Length(message = "DNS-имя должно содержать более 1 символа",min = 1)
    @Pattern(regexp = "[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,9}", message = "DNS-имя должно содержать только латинские буквы и/или цифры")
    private String dnsName;
    @Length(message = "Поле должно содержать более 1 символа",min = 1)
    private String organisation;
    @Length(message = "Поле должно содержать более 1 символа",min = 1)
    private String organisationUnit;
    @Length(message = "Поле должно содержать более 1 символа",min = 1)
    private String city;
    @Length(message = "Поле должно содержать более 1 символа",min = 1)
    private String countryUnit;
    @Length(message = "Поле должно содержать не более 2 символов, например RU",max = 2)
    private String country;
    @Length(message = "Поле должно содержать более 1 символа",min = 1)
    @Pattern(regexp = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$", message = "используйте только латинские буквы и/или цифры")
    private String email;
    private String subjectAlternativeName;
    @Length(message = "Поле должно содержать более 1 символа",min = 1)
    private String adminName;
    private String comment;
    @Enumerated(EnumType.STRING)
    private TypeCert typeCert;
    private String pairkeysFolderName;
    private LocalDate create_at;
    private LocalDate validity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "profileOpenssl",referencedColumnName = "id")
    private ProfileOpenssl profileOpenssl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getOrganisationUnit() {
        return organisationUnit;
    }

    public void setOrganisationUnit(String organisationUnit) {
        this.organisationUnit = organisationUnit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryUnit() {
        return countryUnit;
    }

    public void setCountryUnit(String countryUnit) {
        this.countryUnit = countryUnit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectAlternativeName() {
        return subjectAlternativeName;
    }

    public void setSubjectAlternativeName(String subjectAlternativeName) {
        this.subjectAlternativeName = subjectAlternativeName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TypeCert getTypeCert() {
        return typeCert;
    }

    public void setTypeCert(TypeCert typeCert) {
        this.typeCert = typeCert;
    }

    public String getPairkeysFolderName() {
        return pairkeysFolderName;
    }

    public void setPairkeysFolderName(String pairkeysFolderName) {
        this.pairkeysFolderName = pairkeysFolderName;
    }

    public LocalDate getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }
}
