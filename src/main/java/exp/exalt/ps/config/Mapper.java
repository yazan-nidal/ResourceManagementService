package exp.exalt.ps.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;
    public <P,T> T convertForm(P p,Class<T> dest) {
        return modelMapper.map(p,dest);
    }
}
