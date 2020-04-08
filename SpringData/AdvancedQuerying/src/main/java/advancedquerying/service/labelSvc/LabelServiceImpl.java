package advancedquerying.service.labelSvc;

import advancedquerying.domain.entities.Label;
import advancedquerying.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Label getLabelById(long id) {
        return this.labelRepository.getOne(id);
    }
}
