package SpringBoot.Backend.Service;

import SpringBoot.Backend.DTO.RegionDTO;
import SpringBoot.Backend.Entity.Region;
import SpringBoot.Backend.Repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionService {
    @Autowired
    RegionRepository regionRepository;
    public RegionDTO getRegionById(Long id) {
        Optional<Region> foundRegion = regionRepository.findById(id);
        return foundRegion.map(RegionDTO::new).orElse(null);
    }

    public List<RegionDTO> getAllRegion() {
        return regionRepository.findAll().stream().map(RegionDTO::new).collect(Collectors.toList());
    }
}
