package aa.trusov.keyDispatcher.services;

import aa.trusov.keyDispatcher.entities.Pairkeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aa.trusov.keyDispatcher.repositories.PairkeysRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.apache.tomcat.util.http.FastHttpDateFormat.getCurrentDate;

@Service
public class PairkeysService {

    private PairkeysRepository pairkeysRepository;

    @Autowired
    public void setPairkeysRepository(PairkeysRepository pairkeysRepository) {
        this.pairkeysRepository = pairkeysRepository;
    }

    public Pairkeys getNewPairkeys(){
        Pairkeys pairkeys = new Pairkeys();
        pairkeys.setCountry("RU");
        pairkeys.setCity("Krasnodar");
        pairkeys.setCountryUnit("Krasnodarskiy krai");
        pairkeys.setOrganisation("CB Kuban Credit Ltd");
        pairkeys.setOrganisationUnit("UZI");
        return pairkeys;
    }

    public Pairkeys save(Pairkeys pairkeys){
        pairkeys.setCreate_at(LocalDate.now());
        return pairkeysRepository.save(pairkeys);
    }

    public List<Pairkeys> getAllPairkeys(){
        List<Pairkeys> pairkeys = (List<Pairkeys>) pairkeysRepository.findAllByOrderByIdDesc();
        return pairkeys;
    }

    public List<Pairkeys> getWaitPairkeys(){
        List<Pairkeys> pairkeys = (List<Pairkeys>) pairkeysRepository.findAllByValidityIsNull();
        return pairkeys;
    }

    public Pairkeys getPairkeysById(Long id){
        Pairkeys pairkeys = pairkeysRepository.findById(id).orElse(null);
        return pairkeys;
    }

}
