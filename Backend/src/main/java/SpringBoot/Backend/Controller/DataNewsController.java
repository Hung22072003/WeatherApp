package SpringBoot.Backend.Controller;

import SpringBoot.Backend.Model.ResponseNewsObject;
import SpringBoot.Backend.Model.ResponseObject;
import SpringBoot.Backend.Service.DataNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather.api/v1/news")
@CrossOrigin
public class DataNewsController {
    @Autowired
    private DataNewsService dataNewsService;
    @GetMapping()
    public ResponseEntity<ResponseNewsObject> getNews() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseNewsObject("ok", "Get news successfully", dataNewsService.getDataNews())
        );
    }
}
