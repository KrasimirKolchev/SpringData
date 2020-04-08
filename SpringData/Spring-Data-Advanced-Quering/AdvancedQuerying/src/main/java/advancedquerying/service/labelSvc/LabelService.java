package advancedquerying.service.labelSvc;

import advancedquerying.domain.entities.Label;
import org.springframework.stereotype.Service;

@Service
public interface LabelService {
    Label getLabelById(long id);
}
