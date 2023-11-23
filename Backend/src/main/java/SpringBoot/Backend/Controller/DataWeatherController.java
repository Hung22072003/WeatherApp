package SpringBoot.Backend.Controller;

import SpringBoot.Backend.DTO.DataDetailDTO;
import SpringBoot.Backend.Exception.NotDataException;
import SpringBoot.Backend.Model.ResponseObject;
import SpringBoot.Backend.Service.DataWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/weather.api/v1/data")
public class DataWeatherController {
    @Autowired
    private DataWeatherService dataWeatherService;

    @GetMapping("/forecast/hourly/{id}")
    public ResponseEntity<ResponseObject> getHourlyDataWeatherForecast(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get forecasted data weather successfully", dataWeatherService.getHourlyWeatherDataForecast(id).toArray())
        );
    }

    @GetMapping("history/daily/{id}")
    public ResponseEntity<ResponseObject> getDailyDataWeatherHistory(@RequestParam(value = "start_date", required = false) String start_date, @RequestParam(value = "end_date", required = false)String end_date, @PathVariable("id")Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get history data weather successfully", dataWeatherService.getDailyWeatherDataHistory(id, start_date, end_date).toArray())
        );
    }

    @GetMapping("/forecast/daily/{id}")
    public ResponseEntity<ResponseObject> getDailyDataWeatherForecast(@RequestParam(value = "start_date", required = false) String start_date, @RequestParam(value = "end_date", required = false)String end_date, @PathVariable("id")Long id) {
        List<DataDetailDTO> listData = dataWeatherService.getDailyWeatherDataForecast(id, start_date, end_date);
        if(listData.isEmpty()){
            throw new NotDataException();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get forecast data weather successfully", listData.toArray())
            );
        }
    }
}
