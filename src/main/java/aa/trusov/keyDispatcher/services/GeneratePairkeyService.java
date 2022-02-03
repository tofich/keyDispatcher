package aa.trusov.keyDispatcher.services;

import aa.trusov.keyDispatcher.entities.Pairkeys;

public interface GeneratePairkeyService {

    String generateCSRAndPrivateKeyAndGetFolderNameToPairkeyFiles(Pairkeys p);

    String getCSR(Pairkeys p);

    String getPrivateKey(Pairkeys p);

}
