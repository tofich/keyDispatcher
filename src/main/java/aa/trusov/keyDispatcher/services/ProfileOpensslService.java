package aa.trusov.keyDispatcher.services;

import aa.trusov.keyDispatcher.entities.ProfileOpenssl;
import aa.trusov.keyDispatcher.repositories.ProfileOpensslRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileOpensslService {

    ProfileOpensslRepository profileOpensslRepository;

    @Autowired
    public void setProfileOpensslRepository(ProfileOpensslRepository profileOpensslRepository) {
        this.profileOpensslRepository = profileOpensslRepository;
    }

    public ProfileOpenssl save(ProfileOpenssl profileOpenssl, String configFilename){
        profileOpenssl.setConfigFileName(configFilename);
        return profileOpensslRepository.save(profileOpenssl);
    }

}
