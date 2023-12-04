package SpringBoot.Backend.Controller;

import SpringBoot.Backend.DTO.RegionDTO;
import SpringBoot.Backend.Service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather.api/v1/regions")
@CrossOrigin
public class RegionController {
    @Autowired
    private RegionService regionService;
    @GetMapping("/{id}")
    RegionDTO getRegionById(@PathVariable Long id) {
        return regionService.getRegionById(id);
    }

    @GetMapping()
    List<RegionDTO> getAllRegion() {
        return regionService.getAllRegion();
    }
}
