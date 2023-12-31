
let url_api_region = "http://localhost:8080/weather.api/v1/regions";
let data_region;
async function fetchAPI_search(url_api_region){

    const response = await fetch(url_api_region);
    data_region = await response.json();
  
  
}

fetchAPI_search(url_api_region);

let resultBox= document.querySelector(".result-box");
let inputBox = document.querySelector(".search-bar_input");

inputBox.addEventListener('keyup', function(event){
    let result=[];
    let input = inputBox.value;

    if(input.length){
        result = data_region.filter((x)=>{
            return x.name.toLowerCase().includes(input.toLowerCase());
        })
        display(result);
    }

    if (event.keyCode === 13) {

        if (result.length > 0) {
            select(document.querySelector('.result-box li'));

        }
    }

    if(!result.length){
        resultBox.innerHTML=`<p style="text-align: center; color: rgb(144, 153, 161);"> No result match</p>`;
    }
})

function display(result){
    let conttent = result.map((x)=>`<li onclick=select(this) id="${x.id_Region}">${x.name}</li>`).join('');
    resultBox.innerHTML=`<ul> ${conttent}</ul>`;
}

function select(li){
    let nameProvince=li.innerHTML;
    myArray=nameProvince.split(",");
    document.querySelector("#header-province").innerHTML=myArray[0];
    // provinceName_global= document.getElementById(li.id).getAttribute("name");

    if(myArray.length==1){
        provinceName_global=myArray[0];
    }else{
        provinceName_global=myArray[1];
    }
    displayForecast(li.id);
    console.log(li.id);    
    
    inputBox.value=li.innerHTML;
    resultBox.innerHTML='';
}
