package SpringBoot.Backend.Controller;

import SpringBoot.Backend.DTO.IconDTO;
import SpringBoot.Backend.Model.ResponseObject;
import SpringBoot.Backend.Service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather.api/v1/icons")
public class IconController {
    @Autowired
    private IconService iconService;

    @GetMapping("")
    public List<IconDTO> getAllIcons() {
        return iconService.getAllIcon();
    }
}
