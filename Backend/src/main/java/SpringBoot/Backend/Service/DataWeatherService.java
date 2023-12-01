package SpringBoot.Backend.Service;

import SpringBoot.Backend.DTO.DataDetailDTO;
import SpringBoot.Backend.DTO.IconDTO;
import SpringBoot.Backend.DTO.RegionDTO;
import SpringBoot.Backend.Entity.*;
import SpringBoot.Backend.Exception.DateFormatException;
import SpringBoot.Backend.Exception.DateOrderException;
import SpringBoot.Backend.Exception.NotDataException;
import SpringBoot.Backend.Exception.RegionNotFoundException;
import SpringBoot.Backend.Model.ResponseDailyWeather;
import SpringBoot.Backend.Model.ResponseHourlyWeather;
import SpringBoot.Backend.Repository.DataDetailRepository;
import SpringBoot.Backend.Repository.DataWeatherRepository;
import SpringBoot.Backend.Repository.InsertDataDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataWeatherService {
    private RestTemplate restTemplate;
    private final String key = "f9eb34a7805f4d8981fb06bb5bbdc20f";
    @Autowired
    private DataWeatherRepository dataWeatherRepository;
    @Autowired
    private DataDetailRepository dataDetailRepository;

    @Autowired
    IconService iconService;
    @Autowired
    RegionService regionService;
    @Autowired
    InsertDataDetailRepository insertDataDetailRepository;

    public DataWeatherService() {
        restTemplate = new RestTemplate();
    }

    private boolean isValidDateFormat(String dateString, String dateFormat) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private  boolean isDateWithinRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    public List<DataDetailDTO> getHourlyWeatherDataForecast(Long id) {
        String url_api = "https://api.weatherbit.io/v2.0/forecast/hourly?hours=240&units=metric" + "&key=" + key;
        RegionDTO regionDTO = regionService.getRegionById(id);
        if(regionDTO == null) throw new RegionNotFoundException();
        url_api = url_api + "&lon=" + regionDTO.getLongtitude() + "&lat=" + regionDTO.getLatitude();

        ResponseHourlyWeather rw = restTemplate.getForObject(url_api, ResponseHourlyWeather.class);
        assert rw != null;
        List<DataDetailDTO> listData = Arrays.stream(rw.getData()).map(DataDetailDTO::new).collect(Collectors.toList());


        Region region = new Region(regionDTO);
        DataWeather eDataWeather = new DataWeather();
        eDataWeather.setRegion(region);
        dataWeatherRepository.save(eDataWeather);

        for(DataDetailDTO dataDetailDTO: listData) {
            IconDTO iconDTO = iconService.getIconById(dataDetailDTO.getIcon());
            CompositKey compositKey = new CompositKey(dataDetailDTO.getTimeForecasted(), eDataWeather);
            DataDetail dataDetail = new DataDetail(dataDetailDTO, compositKey, iconDTO);
            dataDetail.setData(eDataWeather);
            insertDataDetailRepository.insertWithEntityManager(dataDetail);
        }

        return dataDetailRepository.findAllBydata(eDataWeather).stream().map(DataDetailDTO::new).collect(Collectors.toList());
    }

    public List<DataDetailDTO> getDailyWeatherDataHistory(Long id, String start_date, String end_date) {
        String dateFormat = "yyyy-MM-dd";
        if(!isValidDateFormat(start_date, dateFormat) || !isValidDateFormat(start_date, dateFormat)) throw new DateFormatException();

        LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        end_date = endDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(!startDate.isBefore(endDate)) throw new DateOrderException();

        RegionDTO regionDTO = regionService.getRegionById(id);
        if(regionDTO == null) throw new RegionNotFoundException();

        String url_api = "https://api.weatherbit.io/v2.0/history/daily?units=metric" + "&key=" + key +"&start_date=" + start_date + "&end_date=" + end_date;

        url_api = url_api + "&lon=" + regionDTO.getLongtitude() + "&lat=" + regionDTO.getLatitude();

        ResponseDailyWeather rw = restTemplate.getForObject(url_api, ResponseDailyWeather.class);
        assert rw != null;

        List<DataDetailDTO> listData = Arrays.stream(rw.getData()).map(DataDetailDTO::new).collect(Collectors.toList());


        Region region = new Region(regionDTO);
        DataWeather eDataWeather = new DataWeather();
        eDataWeather.setRegion(region);
        dataWeatherRepository.save(eDataWeather);

        for(DataDetailDTO dataDetailDTO: listData) {
            CompositKey compositKey = new CompositKey(dataDetailDTO.getTimeForecasted(), eDataWeather);
            DataDetail dataDetail = new DataDetail(dataDetailDTO, compositKey);
            dataDetail.setData(eDataWeather);
            insertDataDetailRepository.insertWithEntityManager(dataDetail);
        }

        return dataDetailRepository.findAllBydata(eDataWeather).stream().map(DataDetailDTO::new).collect(Collectors.toList());
    }

    public List<DataDetailDTO> getDailyWeatherDataForecast(Long id, String start_date, String end_date) {
        String dateFormat = "yyyy-MM-dd";
        if(!isValidDateFormat(start_date, dateFormat) || !isValidDateFormat(start_date, dateFormat)) throw new DateFormatException();

        LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if(!startDate.isBefore(endDate)) throw new DateOrderException();
        RegionDTO regionDTO = regionService.getRegionById(id);
        if(regionDTO == null) throw new RegionNotFoundException();

        String url_api = "https://api.weatherbit.io/v2.0/forecast/daily?units=metric" + "&key=" + key;
        url_api = url_api + "&lon=" + regionDTO.getLongtitude() + "&lat=" + regionDTO.getLatitude();

        ResponseDailyWeather rw = restTemplate.getForObject(url_api, ResponseDailyWeather.class);

        assert rw != null;
        List<DataDetailDTO> listData = Arrays.stream(rw.getData()).map(DataDetailDTO::new).collect(Collectors.toList());


        Region region = new Region(regionDTO);
        DataWeather eDataWeather = new DataWeather();
        eDataWeather.setRegion(region);
        dataWeatherRepository.save(eDataWeather);

        for(DataDetailDTO dataDetailDTO: listData) {
            if(isDateWithinRange(LocalDate.parse(dataDetailDTO.getTimeForecasted(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),startDate,endDate)) {
                IconDTO iconDTO = iconService.getIconById(dataDetailDTO.getIcon());
                CompositKey compositKey = new CompositKey(dataDetailDTO.getTimeForecasted(), eDataWeather);
                DataDetail dataDetail = new DataDetail(dataDetailDTO, compositKey, iconDTO);
                dataDetail.setData(eDataWeather);
                insertDataDetailRepository.insertWithEntityManager(dataDetail);
            }
        }

        return dataDetailRepository.findAllBydata(eDataWeather).stream().map(DataDetailDTO::new).collect(Collectors.toList());
    }
}
