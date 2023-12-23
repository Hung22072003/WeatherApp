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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataWeatherService {
    private RestTemplate restTemplate;
    private final String key = "45efb5d14d934c24b2e7c58b6e23f97f";
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
        String appid = "136a3755681cb9ef118f5fa90e4edd9b";
        String url_api = "https://api.openweathermap.org/data/2.5/forecast?units=metric" + "&appid=" + appid;
        RegionDTO regionDTO = regionService.getRegionById(id);
        if(regionDTO == null) throw new RegionNotFoundException();
        url_api = url_api + "&lon=" + regionDTO.getLongtitude() + "&lat=" + regionDTO.getLatitude();
        List<DataDetailDTO> listData = null;
        try {
            URL url = new URL(url_api);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu
            connection.setRequestMethod("GET");

            // Đọc dữ liệu từ InputStream
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            String json = response.toString().replace("3h", "precip");

            ObjectMapper objectMapper = new ObjectMapper();

            // Chuyển đổi JSON thành đối tượng MyObject
            ResponseHourlyWeather myObject = objectMapper.readValue(json, ResponseHourlyWeather.class);
            listData = Arrays.stream(myObject.getList()).map(DataDetailDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public List<DataDetailDTO> getOneDayWeatherDataHistory(Long id, String date) {
        String dateFormat = "yyyy-MM-dd";

        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end_date = startDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        RegionDTO regionDTO = regionService.getRegionById(id);
        if(regionDTO == null) throw new RegionNotFoundException();

        String url_api = "https://api.weatherbit.io/v2.0/history/daily?units=metric" + "&key=" + key +"&start_date=" + date + "&end_date=" + end_date;

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

    public List<DataDetailDTO> getOneDayWeatherDataForecast(Long id, String date) {
        String dateFormat = "yyyy-MM-dd";

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
            if(LocalDate.parse(dataDetailDTO.getTimeForecasted(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isEqual(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                IconDTO iconDTO = iconService.getIconById(dataDetailDTO.getIcon());
                CompositKey compositKey = new CompositKey(dataDetailDTO.getTimeForecasted(), eDataWeather);
                DataDetail dataDetail = new DataDetail(dataDetailDTO, compositKey, iconDTO);
                dataDetail.setData(eDataWeather);
                insertDataDetailRepository.insertWithEntityManager(dataDetail);
            }
        }

        return dataDetailRepository.findAllBydata(eDataWeather).stream().map(DataDetailDTO::new).collect(Collectors.toList());
    }


    public List<DataDetailDTO> getCurrentWeatherDataForecast(Long id) {
        String dateFormat = "yyyy-MM-dd";

        RegionDTO regionDTO = regionService.getRegionById(id);
        if(regionDTO == null) throw new RegionNotFoundException();

        String url_api = "https://api.weatherbit.io/v2.0/current?units=metric" + "&key=" + key;
        url_api = url_api + "&lon=" + regionDTO.getLongtitude() + "&lat=" + regionDTO.getLatitude();

        ResponseDailyWeather rw = restTemplate.getForObject(url_api, ResponseDailyWeather.class);

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
}
