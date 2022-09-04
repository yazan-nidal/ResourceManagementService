package exp.exalt.ps.services;

import exp.exalt.ps.models.STime;
import exp.exalt.ps.repositories.STimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class STimeServices {
    @Autowired
    STimeRepository sTimeRepository;

    public STime setSTime(STime sTime) {
        return sTimeRepository.save(sTime);
    }

    public  STime getSTime(long serverId) {
        return sTimeRepository.findById(serverId).orElse(null);
    }

    public void unSetSTime(long  serverId) {
        sTimeRepository.deleteById(serverId);
    }
}
