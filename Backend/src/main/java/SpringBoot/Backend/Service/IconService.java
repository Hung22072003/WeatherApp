package SpringBoot.Backend.Service;

import SpringBoot.Backend.DTO.IconDTO;
import SpringBoot.Backend.Entity.Icon;
import SpringBoot.Backend.Repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IconService {
    @Autowired
    private IconRepository iconRepository;

    public List<IconDTO> getAllIcon () {
        List<Icon> icons = iconRepository.findAll();
        return icons.stream().map(IconDTO::new).collect(Collectors.toList());
    }

    public IconDTO getIconById(String id) {
        Optional<Icon> foundIcon = iconRepository.findById(id);
        return foundIcon.map(IconDTO::new).orElse(null);
    }

}
