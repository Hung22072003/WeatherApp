

var modalMap = document.querySelector('.map-overlay')
var modalMapclose = document.querySelector('.close-div i')
var modalCalendar = document.querySelector('.calendar-outer-container')
var modalTable = document.querySelector('.table')
var modalChart = document.querySelector('.chart-outer-container')
var modalOneDay =   document.querySelector('.today-outer-container')


var contentNewsElement = document.querySelector(".content_news");
function openMap() {
  contentNewsElement.classList.add("active")
  let masterTimeline = gsap.timeline();

  let mapTimeline = gsap.timeline();
  mapTimeline.to(".map-overlay", {
      display: "flex",
  });
  mapTimeline.from(".map-overlay", {
      duration: 0.5,
      backgroundColor: "rgba(0, 0, 0, 0)",
      ease: "power4.out"
  });
  mapTimeline.from(".map-inner-box", {
    duration: 1,
    opacity: 0,
    x:300,
    ease: "power4.out"
});
  mapTimeline.from(".fade-animate", {
      duration: 0.5,
      opacity: 0,
      ease: "power4.out"
  });

  let calendarTimeline = gsap.timeline();
  calendarTimeline.to("#btnCalendar", {
    display: "flex",
    opacity: 1
  });
  calendarTimeline.from("#btnCalendar", {
      duration: 0.8,
      x: 2000,
      opacity: 0,
      ease: "power4.out"
  });

  let tableTimeline = gsap.timeline();
  tableTimeline.to(".table", {
    display:"flex",
    opacity: 1,
  });
  tableTimeline.from(".table", {
    duration: 1.3,
    opacity: 0,
    y: 300,
    ease: "power4.out"
  });


  masterTimeline.add(mapTimeline, 0);
  masterTimeline.add(calendarTimeline, 0.75);
  masterTimeline.add(tableTimeline, 1.3);

  masterTimeline.reverse();
  masterTimeline.restart();
}

function unselectDate() {
  let selectedDateElement = null; 
  if (selectedDateElement) {
    selectedDateElement.classList.remove('selected');
    selectedDateElement = null;
  }
}


function closeMap(){
  contentNewsElement.classList.remove("active")

  modalMap.style.animation ='todayFadeOut 0.3s ease-in-out';
  modalCalendar.style.animation ='todayFadeOut 0.3s ease-in-out';
  modalTable.style.animation = 'todayFadeOut 0.3s ease-in-out'; 
  modalChart.style.animation = 'todayFadeOut 0.3s ease-in-out';
  modalOneDay.style.animation = 'todayFadeOut 0.3s ease-in-out';

  setTimeout(function () {
  modalMap.style.display ='';
  modalCalendar.style.display ='';
  modalTable.style.display = ''; 
  modalChart.style.display = '';
  modalOneDay.style.display = '';

  modalMap.style.animation ='';
  modalCalendar.style.animation ='';
  modalTable.style.animation = ''; 
  modalChart.style.animation = '';
  modalOneDay.style.animation = '';
  }, 300);
  unselectDate();
}

modalMapclose.addEventListener("click",closeMap);
modalMap.addEventListener("click", function(e){
  if (e.target == e.currentTarget){
    closeMap();
  }
});
document.querySelector(".modal-wrap").addEventListener("click", function(e){
  if (e.target == e.currentTarget){
    closeMap();  
  }
});

var data;
gsap.set (".province", {scale:1});

//thiet lap su kien mouseover cho tung tinh
document.querySelectorAll(".province").forEach((province)=>{
    province.addEventListener("mouseover", (event)=>{
    gsap.to(province, {scale: 1.15, duration:0.5});
    province.style.fillOpacity = "1";
    let name = province.getAttribute('name');
    var tooltip = document.getElementById('tooltip1');

    var mouseX = event.clientX-360;
    var mouseY = event.clientY +50;

    tooltip.style.display = 'block';
    tooltip.style.left = mouseX + 'px';
    tooltip.style.top = mouseY  + 'px';
    tooltip.innerHTML=name;

})
province.addEventListener("mouseout", ()=>{
    gsap.to(province, {scale:1, duration:0.5});
    province.style.fillOpacity = "0.56";
    var tooltip = document.getElementById('tooltip1');
    tooltip.style.display='none';
})

});
var provinceName_global;
//thiet lap su kien click cho tung tinh
document.querySelectorAll(".province").forEach((province) => {


  province.addEventListener("click",()=>{

    const provinceId = province.getAttribute("id");
    const nameProvince= province.getAttribute("name");
    provinceName_global=nameProvince;
    //  document.getElementById('heart-icon').dataset.currentProvince = provinceId;
    displayForecast(provinceId);
    document.querySelector("#header-province").innerHTML=nameProvince;
  });

 });

 var svgObject
//hien thi cac modal khi click vao mot tinh
 function displayForecast(provinceId){

  openMap();
  provinceId = provinceId.toString();
  let districtId= provinceId;
  provinceId = provinceId.slice(0,2);

  let url_region_byID = ' http://localhost:8080/weather.api/v1/regions/'+provinceId;
  fetch(url_region_byID)
  .then(res => res.json())
  .then(result => {
      provinceName_global=result.name;
    })

  //  document.getElementById('heart-icon').dataset.currentProvince = provinceId;
  document.querySelector("#heart-icon").setAttribute("data-index", districtId);
  updateFavoriteButtonInModal();

  var container = document.getElementById("svg-Container");
  container.innerHTML="";
  svgObject = document.createElement('object');
  svgObject.data = '../assets/SVG/DetailProvince/'+provinceId+'.svg';
  svgObject.type = 'image/svg+xml';
  container.appendChild(svgObject);


  let url_API="http://localhost:8080/weather.api/v1/data/forecast/hourly/"+districtId;

  console.log(url_API);
  fetchAPI_data(url_API);


  svgObject.addEventListener('load', function() {
    const svgDocument = svgObject.contentDocument;
    eventDetailProvince(svgDocument);
      if(districtId.length == 4){

        let path = svgDocument.getElementById(districtId);
        path.setAttribute("stroke-width",3);
        path.setAttribute("stroke", "#000000");
        path.setAttribute("stroke-opacity",1);
      }else{

      }
  


        
});
}

window.displayForecast = displayForecast;

//set su kien cho tung huyen trong tinh
 function eventDetailProvince(svgDocument){
  if (!svgDocument) {
    console.error('Content document is null or undefined.');
    return;
  }

  const paths = svgDocument.getElementsByClassName('district');


  // lap qua tung path va them su kien click
  for (const path of paths) {
    path.style.cursor= "pointer";
    path.addEventListener('mouseover', function(event) {
      // alert(path.getAttribute("name"));
      let name = path.getAttribute('name');
      var tooltip = document.getElementById('tooltip2');
  
      var mouseX = event.clientX +100 ;
      var mouseY = event.clientY +120;
    
      tooltip.style.display = 'block';
      tooltip.style.left = mouseX + 'px';
      tooltip.style.top = mouseY  + 'px';
      tooltip.innerHTML=name;
    });

    path.addEventListener('mouseout', function(event){
      var tooltip = document.getElementById('tooltip2');
      tooltip.style.display='none';
    })

    path.addEventListener('click', function(){
      let districtId = path.getAttribute("id");
      let districtName=path.getAttribute("name");
      strokeWidth(path,paths);
      document.querySelector("#header-province").innerHTML=districtName;
      document.querySelector("#heart-icon").setAttribute("data-index", districtId);
      updateFavoriteButtonInModal();
      document.querySelector('.data-table').innerHTML = "";
      let url="http://localhost:8080/weather.api/v1/data/forecast/hourly/"+districtId;
      fetchAPI_data(url);
      console.log(url);

      
      const currentProvinceName = document.querySelector('#header-province').textContent;
      document.querySelector('#today_modal_province-name').innerHTML = currentProvinceName;

    })

    path.addEventListener("contextmenu",function(event){
      event.preventDefault();
      rightMouseClick(path);
      let provinceId = document.querySelector("#heart-icon").getAttribute("data-index").slice(0,2);
      document.querySelector("#heart-icon").setAttribute("data-index", provinceId);
      updateFavoriteButtonInModal();
      document.querySelector('.data-table').innerHTML = "";
      let url="http://localhost:8080/weather.api/v1/data/forecast/hourly/"+provinceId;
      fetchAPI_data(url);
      console.log(url);

      
      const currentProvinceName = document.querySelector('#header-province').textContent;
      document.querySelector('#today_modal_province-name').innerHTML = currentProvinceName;
    })
    
  }
 }

 
//viền đậm khi chọn huyện
 function strokeWidth(path,allDistrict){
  document.querySelector('.chart-outer-container').style.display='none';

  for(const district of allDistrict){
    if(path.getAttribute("id") === district.getAttribute("id")){
      // displayForecast(path.getAttribute("id"));
      path.setAttribute("stroke-width",3);
      path.setAttribute("stroke", "#000000");
      path.setAttribute("stroke-opacity",1);
    }else{
      rightMouseClick(district);
    }
  }

 }

 //bỏ viền đậm
 function rightMouseClick(district){
  document.querySelector("#header-province").innerHTML=provinceName_global;
  district.setAttribute("stroke-width",1);
  district.setAttribute("stroke", "#0000FF");
  district.setAttribute("stroke-opacity",0.4)
 }

//fetch api lưu dữ liệu vào data theo giờ
 async function fetchAPI_data(url){

  const response = await fetch(url);
  var json_data = await response.json();

  data =json_data.data;

  showData();


 }


var daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
let day_after = new Array();

for(var i=0; i<=5; i++){
  var day = new Date();
  day.setDate(day.getDate()+i);
  var weekday = day.getDay();
  let date = day.getDate();
  var st = daysOfWeek[weekday] + " "+ date;
  day_after.push(st);
}

//hien thi du lieu vao bang
 function showData(){

  let colspan=[];
  let count =0;

  data.forEach((x,index,array) =>{
    if(x.TimeForecasted.split(' ')[1].split(':')[0]=="00") {
      colspan.push(count);
      count=0;
    }
    count++;
    if(index === array.length-1){
      colspan.push(count);
    }
  });
  // console.log(colspan);
  let i=0;
  
  let html = 
    `<thead> <tr>
    ${colspan.map((x)=>`<th colspan='${x}'  style="border-bottom: 1px solid #ccc; white-space:nowrap;"> ${day_after[i++]} </th>`).join('')}
    </tr></thead>
    <tbody>
      <tr>${data.map((x)=>`<td> ${x.TimeForecasted.split(' ')[1].split(':')[0]} </td>`).join('')}</tr>
      <tr>${data.map((x)=>`<td><img src="${x.Icon}" alt="" style="max-width:100%; height:50px;display:block; margin:auto;">`).join('')}</tr>
      <tr>${data.map((x)=>`<td>${x.Temp.toFixed(2)}`).join('')}</tr>
      <tr>${data.map((x)=>`<td>${x.Wind}`).join('')}</tr>
      <tr>${data.map((x)=>`<td>${x.Cloud}`).join('')}</tr>
      <tr>${data.map((x)=>`<td>${x.Precip.toFixed(2)}`).join('')}</tr>
    </tbody>`;

    document.querySelector('.data-table').innerHTML = html;
  };

  

//cập nhật data trong local storage
function likeToggle(){
      const curentProvince = document.querySelector("#heart-icon").getAttribute("data-index");
      const currentProvinceName = document.querySelector('#header-province').textContent;
      let favoriteList = JSON.parse(localStorage.getItem('favoriteList')) || [];

      // const isFavorite = favoriteList.includes(curentProvince);
      const isFavorite = favoriteList.some(item => item.id === curentProvince)

      if (!isFavorite){
          // favoriteList.push(curentProvince);
          favoriteList.push({id:curentProvince, name: currentProvinceName})

          localStorage.setItem('favoriteList', JSON.stringify(favoriteList));
          // alert(`${curentProvince} added to favorite list!`);
          updateFavoriteButtonInModal();
          
      }
      else{
          favoriteList = favoriteList.filter(item => item.id !== curentProvince);
          localStorage.setItem('favoriteList', JSON.stringify(favoriteList));
          // alert(`${curentProvince} removed from favorite list!`);
          updateFavoriteButtonInModal();
      }
  
}

//cập nhật trạng thái heart icon trong modal
function updateFavoriteButtonInModal(){
      const curentProvince = document.querySelector("#heart-icon").getAttribute("data-index");
      let favoriteList = JSON.parse(localStorage.getItem('favoriteList')) || [];
      // const isFavorite = favoriteList.includes(curentProvince);
      const isFavorite = favoriteList.some(item => item.id === curentProvince)
      var heartIcon = document.querySelector("#heart-icon");
      if (isFavorite){
        heartIcon.style.color = "pink";
        updateFavoriteProvincesList();
      
      }else{
        heartIcon.style.color = "grey";
        updateFavoriteProvincesList();
      }
}

//fetch api theo ngày
async function fetchAPI_data_daily(url) {
  const response = await fetch(url);
  var json_data = await response.json();

  data = json_data.data;
}

//Cập nhật thời tiết trong modal favorite
async function updateFavoriteProvincesList() {
  let favoriteList = JSON.parse(localStorage.getItem('favoriteList')) || [];
  const favoriteProvincesListElement = document.getElementById('favoriteProvincesList');

  const shortDoW = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
  let shortDA = new Array();

  for (var i = 0; i <= 4; i++) {
    var day = new Date();
    day.setDate(day.getDate() + i);
    var weekday = day.getDay();
    var st = shortDoW[weekday];
    shortDA.push(st);
  }


 const html = await Promise.all(favoriteList.map(async(item) => {
  const currentDate = new Date();
    const startDate = currentDate.toISOString().split('T')[0];
    const endDate = new Date(currentDate.getTime() + 4 * 24 * 60 * 60 * 1000)
      .toISOString()
      .split('T')[0];
      const url_API_daily = `http://localhost:8080/weather.api/v1/data/forecast/daily/${item.id}?start_date=${startDate}&end_date=${endDate}`;

    await fetchAPI_data_daily(url_API_daily);


    return `
    <li class="favs-line">
      <div class="favs-title">
        <span><i class="fa-solid fa-heart" id="heart2"></i></span>
        <span class="favs-province-name" onclick="testHeader(${item.id}, '${item.name}')" style="cursor:pointer;">${item.name }</span>   
        <span class="favs-deleter" onclick="removeFavorite(${item.id})"><i class="fa-regular fa-trash-can" style="cursor: pointer;"></i></span>
      </div>
      <div class="favs-weather">
        <table>
          <tbody>
            <tr>
              ${shortDA.map((day, index) => `
                <td>
                  ${day}
                  <img src="${data[index].Icon}" alt="img">
                    <big>${data[index].Temp}°</big>
                </td>
              `).join('')}
            </tr>
          </tbody>
        </table>
      </div>
    </li>`;
  }));

  favoriteProvincesListElement.innerHTML = html.join('');
}

//sự kiện nút xóa favorite
function removeFavorite(id) {
  let favoriteList = JSON.parse(localStorage.getItem('favoriteList')) || [];

  const indexToRemove = favoriteList.findIndex(item => item.id === String(id));
  

  if (indexToRemove !== -1) {
    console.log(`Removing item with id: ${id}`);
    favoriteList.splice(indexToRemove, 1);
    localStorage.setItem('favoriteList', JSON.stringify(favoriteList));
    // alert(`${id} removed from favorite list!`);
    updateFavoriteProvincesList();

  } else {
    console.log(`Item with id ${id} not found.`);
    // console.log(indexToRemove);
  }
}

document.addEventListener('DOMContentLoaded', function() {
  updateFavoriteProvincesList();
});


function testHeader(provinceId, provinceName){
  // console.log(provinceId);
  displayForecast(provinceId);
  document.querySelector("#header-province").innerHTML=provinceName;
}



function RenderDataNews() {
  var url = "http://localhost:8080/weather.api/v1/news"
  fetch(url)
  .then(res => res.json())
  .then(result => {
    var title = `<div class = "content_news__title">${result.info.title}</div>`
    var listli = result.info.data.map(item => {
      return `
        <li class = "content_news__item">
          <div class = "content_news__weather">
            <div class = "content_news__weather-img">
              <img src="${item.Icon}" alt="">
            </div>
            <div class = "content_news__weather-main">
              <span class = "content_news__weather-region">${item.NameRegion}</span>
              <span class = "content_news__weather-temperature">
               ${item.Content_Temperature}
              </span>
            </div>
          </div>

          <div class = "content_news__description">
            ${item.Content_Description}
          </div>
        </li>
      `
    })
    var headerul = `<ul class = "content_news__list">`
    var footerul = `</ul>`

    contentNewsElement.innerHTML = title + headerul + listli.join('') + footerul;
  })
}

RenderDataNews();