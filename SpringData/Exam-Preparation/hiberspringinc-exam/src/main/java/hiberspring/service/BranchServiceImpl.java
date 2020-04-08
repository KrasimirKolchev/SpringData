package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.BranchViewDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.repository.BranchRepository;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final TownService townService;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownService townService, ValidationUtil validationUtil,
                             ModelMapper modelMapper, Gson gson) {
        this.branchRepository = branchRepository;
        this.townService = townService;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(Constants.PATH_TO_FILES + "branches.json"));
    }

    @Override
    public String importBranches(String branchesFileContent) {
        BranchViewDTO[] branches = this.gson.fromJson(branchesFileContent, BranchViewDTO[].class);

        StringBuilder sb = new StringBuilder();
        Arrays.stream(branches).forEach(b -> {
            Branch branch = this.modelMapper.map(b, Branch.class);
            branch.setTown(this.townService.getTownByName(b.getTown()));

            if (this.validationUtil.isValid(branch)) {
                sb.append(String.format("Successfully imported Branch %s.", branch.getName()));
                this.branchRepository.saveAndFlush(branch);
            } else {
                sb.append("Error: Invalid data.");
            }

            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public Branch getBranchByName(String name) {
        return this.branchRepository.findBranchByName(name);
    }
}
