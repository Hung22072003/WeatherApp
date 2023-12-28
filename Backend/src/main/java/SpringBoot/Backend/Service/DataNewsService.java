package SpringBoot.Backend.Service;

import SpringBoot.Backend.DTO.DataNewsDTO;
import SpringBoot.Backend.DTO.DataNewsDetailDTO;
import SpringBoot.Backend.DTO.IconDTO;
import SpringBoot.Backend.Entity.CompositKeyNews;
import SpringBoot.Backend.Entity.DataNews;
import SpringBoot.Backend.Entity.DataNewsDetail;
import SpringBoot.Backend.Repository.DataNewsDetailRepository;
import SpringBoot.Backend.Repository.DataNewsRepository;
import SpringBoot.Backend.Repository.DataWeatherRepository;
import SpringBoot.Backend.Repository.InsertDataNewsDetailRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataNewsService {
    private String url = "http://vnmha.gov.vn/nchmf-new/show-day-and-night-weather";
    @Autowired
    private DataNewsRepository dataNewsRepository;
    @Autowired
    private DataNewsDetailRepository dataNewsDetailRepository;
    @Autowired
    IconService iconService;
    @Autowired
    ImageStorageService imageStorageService;
    @Autowired
    InsertDataNewsDetailRepository insertDataNewsDetailRepository;

    public DataNewsDTO getDataNews() {
        List<DataNewsDetailDTO> listData = new ArrayList<DataNewsDetailDTO>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsContentLeft = document.getElementsByClass("content-left");
            Elements elementsContentRight = document.getElementsByClass("content-right");
            Elements elementsTagH2 = document.getElementsByClass("show-weather").get(0).getElementsByTag("h2");

            String title = document.getElementsByClass("col-md-9 col-sm-12").get(0).getElementsByTag("h2").get(0).html();
            DataNews dataNews = new DataNews(title);
            dataNewsRepository.save(dataNews);
            for(int i = 0; i < elementsContentLeft.size(); i++) {
                Elements elementsTagImg = elementsContentLeft.get(i).getElementsByTag("img");
                Elements elementsTagP = elementsContentRight.get(i).getElementsByTag("p");
                String img = elementsTagImg.get(0).absUrl("src");
                String id = getIdImg(img);
                IconDTO iconDTO = iconService.getIconById(id);
                URL url_img = new URL(img);
                if(iconDTO == null) {
                    iconDTO =  imageStorageService.storeFile(new MockMultipartFile(id+".png",  id+".png", "image", url_img.openStream()), id);
                }
                iconDTO = iconService.getIconById(id);
                String NameRegion = elementsTagH2.get(i).html();
                String Content_Temperature = elementsTagP.get(0).html();
                String Content_Discription = elementsTagP.get(1).html();

                CompositKeyNews compositKeyNews = new CompositKeyNews(NameRegion, dataNews);
                DataNewsDetail dataNewsDetail = new DataNewsDetail(compositKeyNews,Content_Temperature, Content_Discription, iconDTO);
                insertDataNewsDetailRepository.insertWithEntityManager(dataNewsDetail);
            }

            listData = dataNewsDetailRepository.findAllBydata(dataNews).stream().map(DataNewsDetailDTO::new).collect(Collectors.toList());
            return new DataNewsDTO(title, listData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdImg(String img) {
        int index = 0;
        for (int i = 0; i < img.length(); i++) {
            if(Character.isDigit(img.charAt(i))){
                index = i;
                break;
            }
        }
        return img.substring(index,img.length()-4);
    }
}
