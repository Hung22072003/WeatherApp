const daysTag = document.querySelector(".days");
const currentDate = document.querySelector(".current-date");
const prevNextIcon = document.querySelectorAll(".icons span");

let date = new Date();
let currYear = date.getFullYear();
let currMonth = date.getMonth();

const months = [
  "January", "February", "March", "April", "May", "June", "July",
  "August", "September", "October", "November", "December"
];

const renderCalendar = () => {
  let firstDayofMonth = new Date(currYear, currMonth, 1).getDay();
  let lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate();
  let lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay();
  let lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate();

  const visibleDates = [];
  for (let i = firstDayofMonth; i > 0; i--) {
    visibleDates.push({
      day: lastDateofLastMonth - i + 1,
      month: currMonth === 0 ? 11 : currMonth - 1,
      year: currMonth === 0 ? currYear - 1 : currYear,
      isCurrentMonth: false,
    });
  }
  for (let i = 1; i <= lastDateofMonth; i++) {
    visibleDates.push({
      day: i,
      month: currMonth,
      year: currYear,
      isCurrentMonth: true,
    });
  }
  for (let i = lastDayofMonth, j = 1; i < 6; i++, j++) {
    visibleDates.push({
      day: j,
      month: currMonth === 11 ? 0 : currMonth + 1,
      year: currMonth === 11 ? currYear + 1 : currYear,
      isCurrentMonth: false,
    });
  }

  let liTag = "";

  visibleDates.forEach(({ day, month, year, isCurrentMonth }) => {
    // const isToday = day === date.getDate() && month === date.getMonth() && year === date.getFullYear() ? "active" : "";
    const isToday = day === date.getDate() && month === date.getMonth() && year === date.getFullYear();
    const isGrey = !isCurrentMonth ? "inactive" : "";
    liTag += `<li class="${isToday} ${isGrey}" data-date="${day}" data-month="${month}" data-year="${year}">${day}</li>`;
  });

  currentDate.innerText = `${months[currMonth]} ${currYear}`;
  daysTag.innerHTML = liTag;
}
let selectedDateElement = null;
renderCalendar();

prevNextIcon.forEach(icon => {
  icon.addEventListener("click", () => {
    currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;
    if (currMonth < 0 || currMonth > 11) {
      date = new Date(currYear, currMonth, new Date().getDate());
      currYear = date.getFullYear();
      currMonth = date.getMonth();
    } else {
      date = new Date();
    }
    renderCalendar();
  });
});


document.querySelector('.days').addEventListener('click', event => {
  let modalToday = document.querySelector('.today-outer-container');
  const clickedDateElement = event.target.closest('li');
  if (clickedDateElement) {
    if (selectedDateElement) {
      selectedDateElement.classList.remove('selected');
    }

    selectedDateElement = clickedDateElement;
    selectedDateElement.classList.add('selected');
    
    const clickedDate = new Date(
      parseInt(clickedDateElement.dataset.year),
      parseInt(clickedDateElement.dataset.month),
      parseInt(clickedDateElement.dataset.date)
    );
    
    if (modalToday.style.display === '' || modalToday.style.display === 'none') {
      modalToday.style.animation = 'fadeIn 0.3s ease-out forwards';
      // setTimeout(function () {
        document.querySelector('.today-main-container').style.animation = 'fadeIn 2s ease-in-out';
      // }, 300);
      modalToday.style.display = 'block';
    }
    else{
      
    }
    
    // console.log(`Clicked Date: ${clickedDate.toDateString()}`);
    // const selectedDate = clickedDate.getFullYear() + '-' + (clickedDate.getMonth() +1) + '-' + clickedDate.getDate() ;
    const selectedDate = clickedDate.getFullYear() + '-' + 
  (String(clickedDate.getMonth() + 1).padStart(2, '0')) + '-' + 
  (String(clickedDate.getDate()).padStart(2, '0'));
    // var selectedDate = clickedDate.toISOString().split('T')[0];
    const currentDate = new Date();
    // const currentDateStr = currentDate.toISOString().split('T')[0];


    const isPast = clickedDate < currentDate;
    const isFuture = clickedDate > currentDate;
    const isPresent = clickedDate.toDateString() === currentDate.toDateString();

     const curentProvince = document.querySelector("#heart-icon").getAttribute("data-index");
      const currentProvinceName = document.querySelector('#header-province').textContent;
      document.querySelector('#today_modal_province-name').innerHTML = currentProvinceName;
      document.querySelector('#today_modal_date').innerHTML = clickedDate.toDateString();


      if (isPresent) {
        fetchData(`http://localhost:8080/weather.api/v1/data/current/${curentProvince}`);
      } else if (isPast) {
        fetchData(`http://localhost:8080/weather.api/v1/data/history/oneDay/${curentProvince}?date=${selectedDate}`);
      } else if (isFuture) {
        fetchData(`http://localhost:8080/weather.api/v1/data/forecast/oneDay/${curentProvince}?date=${selectedDate}`);
      }

    // console.log(currentDate);
    // console.log(isPresent);
  }
});

async function fetchData(apiUrl) {
  const response = await fetch(apiUrl);
  var json_data = await response.json();
  const data = json_data.data;
  updateUI(data);

}

function updateUI(data) {
  if (data && data.length > 0) {
    const firstItem = data[0];
    document.querySelector('.no-data-group').style.display= '';
    document.querySelector('.today-main-container').style.display= 'flex';
    const temperatureElement = document.querySelector('.temperature');
    const windElement = document.querySelector('.wind');
    const cloudElement = document.querySelector('.cloud');
    const humidityElement = document.querySelector('.humidity');
    const precipElement = document.querySelector('.precip');
    const iconElement = document.querySelector('.icon');

    const roundedTemperature = Number.isInteger(firstItem.Temp) ? firstItem.Temp : parseFloat(firstItem.Temp).toFixed(2);
    temperatureElement.innerHTML = roundedTemperature + "&deg;" +  "<span id='c-character-temp'>C</span>";

    const roundedWind = Number.isInteger(firstItem.Wind) ? firstItem.Wind : parseFloat(firstItem.Wind).toFixed(2);
    const windInKmPerHour = (roundedWind * 3.6).toFixed(2);
    windElement.innerText = windInKmPerHour + " km/h";

    const roundedCloud = Number.isInteger(firstItem.Cloud) ? firstItem.Cloud : parseFloat(firstItem.Cloud).toFixed(2);
    cloudElement.innerText = roundedCloud + "%";

    const roundedHumidity = Number.isInteger(firstItem.Humidity) ? firstItem.Humidity : parseFloat(firstItem.Humidity).toFixed(2);
    humidityElement.innerText = roundedHumidity + "%";

    const roundedPrecip = Number.isInteger(firstItem.Precip) ? firstItem.Precip : parseFloat(firstItem.Precip).toFixed(2);
    precipElement.innerText = roundedPrecip + " mm";;


    iconElement.src = firstItem.Icon;
    // iconElement.style.display = 'block';


    // console.log(temperatureElement);

    if (firstItem.Icon) {
      iconElement.src = firstItem.Icon;
      iconElement.style.display = 'block';
    } else {
      iconElement.src = '/assets/oneDay-icons/nhietKe.png';
      iconElement.style.display = 'block';
    }
  } else {
    // console.error('No data available.');
    document.querySelector('.today-main-container').style.display= 'none';
    document.querySelector('.no-data-group').style.display= 'block';
  }
}


function unselectDate() {
  if (selectedDateElement) {
    selectedDateElement.classList.remove('selected');
    selectedDateElement = null;
  }
}