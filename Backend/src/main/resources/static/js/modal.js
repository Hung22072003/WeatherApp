var btnOpenSearch = document.querySelector('#Map-button');
var modal1 = document.querySelector('.modal-Search');
var data_chart;
var modalChart = document.querySelector('.chart-outer-container');
var contentNewsElement = document.querySelector(".content_news");

var iconCloseSearch = document.querySelector('#close-Search');

function toggleModal1(e){
    // console.log(e.currentTarget);
    modal1.classList.toggle('hidden');
}
btnOpenSearch.addEventListener('click', function () {
    toggleModal1();
    showModalSearch(modal1);
  });

  iconCloseSearch.addEventListener('click', function () {
    toggleModal1();
    closeModalSearch(modal1);
  });

  function showModalSearch(modal) {
    modal.style.display = 'flex';
    document.getElementById("btn-Search").style.display ='none';
    document.getElementById("svg1").style.display ='none';
    document.getElementById("Map-button").style.display ='none';
    modal.style.animation = 'leftBtnFadeIn 0.3s ease-in-out';
  }

  function closeModalSearch(modal) {
    modal.style.animation = 'leftBtnFadeOut 0.3s ease-in-out';
    setTimeout(function () {
      modal.style.display = 'none';
      document.getElementById("btn-Search").style.display ='block';
    document.getElementById("svg1").style.display ='block';
    document.getElementById("Map-button").style.display ='block';
    }, 300);
    
  }

// btnOpenSearch.addEventListener('click', toggleModal1);
// btnOpenSearch.addEventListener('click', showModal1);
// iconCloseSearch.addEventListener('click',toggleModal1);
// iconCloseSearch.addEventListener('click',closeModal1);


var btnOpenFavorite = document.querySelector('#Favorite-button');
var modal2 = document.querySelector('.modal-Favorite');
var iconCloseFavorite = document.querySelector('#close-Favorite');

function toggleModal2(e){
    // console.log(e.currentTarget);

    modal2.classList.toggle('hidden');
}
btnOpenFavorite.addEventListener('click', function () {
    toggleModal2();
    showModalFavorite(modal2);
  });

  iconCloseFavorite.addEventListener('click', function () {
    toggleModal2();
    closeModalFavorite(modal2);
  });

  function showModalFavorite(modal) {
    modal.style.display = 'flex';
    document.getElementById("btn-Favorite").style.display ='none';
    document.getElementById("svg2").style.display ='none';
    document.getElementById("Favorite-button").style.display ='none';
    modal.style.animation = 'leftBtnFadeIn 0.3s ease-in-out';

  }

  function closeModalFavorite(modal) {
    modal.style.animation = 'leftBtnFadeOut 0.3s ease-in-out';
    setTimeout(function () {
      modal.style.display = 'none';
    document.getElementById("btn-Favorite").style.display ='block';
    document.getElementById("svg2").style.display ='block';
    document.getElementById("Favorite-button").style.display ='block';
    }, 300);
    
  }

var btnOpenCalendar = document.querySelector('#btnCalendar');
var modalCalendar = document.querySelector('.calendar-outer-container');
var iconCloseCalendar = document.querySelector('#close-calendar');
var modalToday = document.querySelector('.today-outer-container');

btnOpenCalendar.addEventListener('click',modal3Handler);

function modal3Handler(){
    if (modalCalendar.style.display === 'none' || modalCalendar.style.display === '') {
        modalCalendar.style.animation = 'calendarFadeIn 0.3s ease-out forwards';
        modalCalendar.style.display = 'block';
        modalChart.style.animation = 'calendarFadeOut 0.3s ease-in-out';

        setTimeout(function () {
          
          modalChart.style.display = 'none';

        }, 300);
        
    }
    else 
    {
        modalCalendar.style.animation = 'calendarFadeOut 0.3s ease-in-out';
        modalToday.style.animation = 'leftBtnFadeOut 0.3s ease-in-out';

        setTimeout(function () {
            modalCalendar.style.display = 'none';
            modalToday.style.display = 'none';

          }, 300);


    }
}


var btnOpenChart = document.querySelector("#chart-icon");
var xValues=[]
var yValuesTemp=[]
var yValuesPrecip=[]
var yValuesCloud = []
var yValuesWind=[]
var currentChart;
btnOpenChart.addEventListener('click',modal4Handler);
function modal4Handler(){
    if (modalChart.style.display === 'none' || modalChart.style.display === '') 
    {   modalChart.style.animation ='fadeIn 0.3s ease-out forwards';
    setTimeout(function () {
        document.querySelector('.chart-wrapper').style.animation = 'chartFadeIn 0.3s ease-out forwards';
        document.querySelector('.chart-wrapper').style.display = 'block';
      }, 300);
        document.querySelector('.chart-outer-container').style.display = 'block';
        document.getElementById("cc-temp").checked=true;


        modalCalendar.style.animation = 'calendarFadeOut 0.3s ease-in-out';
        modalToday.style.animation = 'leftBtnFadeOut 0.3s ease-in-out';

        setTimeout(function () {
          modalCalendar.style.display='';
          modalToday.style.display='';
        }, 300);


        
        destroychart();
        modalChart.style.display = 'block';
    // document.querySelector('.chart-wrapper').style.display = 'block'

      let id = document.getElementById("heart-icon").getAttribute("data-index");
      let current = new Date();
      let Past14Date = new Date();
      Past14Date.setDate(current.getDate()-13);
      start_date = `${Past14Date.getFullYear()}-${Past14Date.getMonth()+1}-${Past14Date.getDate()}`;
      end_date = `${current.getFullYear()}-${current.getMonth()+1}-${current.getDate()}`;

      let chart_choice = document.getElementsByName("chart-choice");

      for(i=0; i<chart_choice.length; i++){
        chart_choice[i].addEventListener("change", function(){
          let val = this.value;
          switch (val){
              case "temp":
                  showChart(xValues,yValuesTemp, 'line',  'Day','Temperature');
                  break;
              case "wind":
                  showChart(xValues,yValuesWind, 'line',  'Day','Wind');
                  break;
              case "cloud":
                  showChart(xValues,yValuesCloud, 'line',  'Day','Cloud');
                  break;
              case "rain":
                  showChart(xValues,yValuesPrecip, 'bar', 'Day', 'Rain');
                  break;
          }
        })
      }

      fetch_API_chart(id, start_date,end_date);


}
    else {
        modalChart.style.animation = 'leftBtnFadeOut 0.3s ease-in-out';
    setTimeout(function () {
        modalChart.style.display = 'none';
      }, 300);
    }

}

async function fetch_API_chart(id, start, end){

    let url = "http://localhost:8080/weather.api/v1/data/history/daily/"+id+"?"+"start_date="+start+"&end_date="+end;
    const response = await fetch(url);
    var json_data = await response.json();
  
    data_chart =json_data.data;
    assignData();

}

function assignData(){
    xValues=[];
    yValuesTemp=[];
    yValuesPrecip=[];
    yValuesCloud = [];
    yValuesWind=[];
    data_chart.forEach((x)=>{
        xValues.push(x.TimeForecasted.split('-')[2]);
        yValuesTemp.push(x.Temp);
        yValuesPrecip.push(x.Precip);
        yValuesCloud.push(x.Cloud);
        yValuesWind.push(x.Wind);
    })

    showChart(xValues,yValuesTemp, 'line', 'Day', 'Temperature');

}

function showChart(xValues,yValues, type, labelX, labelY){

    destroychart();

    currentChart= new Chart("myChart", {
    type: type,
    data: {
        labels: xValues,
        datasets: [{ 
        data: yValues,
        borderColor: "red",
        fill: true
        }]
    },
    options: {
        legend: {display: false},
        scales: {
        xAxes: [{scaleLabel: {display: true, labelString: labelX} }],
        yAxes: [{ scaleLabel:{display: true, labelString:labelY}}]
        }
    }
    });
}

function destroychart(){
    
    if (currentChart) {
        currentChart.destroy();
    }
}
