

function getWeather(zip, apiKey){
    console.log('getWeather fired');
    let weatherSpan = document.getElementById('weatherSpan');
    let weatherSpanLabel = document.getElementById
    ('weatherSpanLabel');
    let weatherSpanButton = document.getElementById
    ('weatherSpanButton');
    console.log (weatherSpan);
    zip = getZipFromNumberZip(zip);
    //API key for openWeatherMaps stored in apiKey.js, which
    // is not tracked by git commits.  Will need to enter an
     //api key into this function to make the weather
//     tracking work
    const response=fetch("http://api.openweathermap.org/data/2.5/weather?zip="+zip+",us&appid="+apiKey);
    response.then(console.log (response));
    response.then(function(response){
        if (response.status === 404){
            alert(`Sorry, there does not seem to be weather data at this trail.  Please check the zip code to see that it is correct`);
        } else {
        const jsonPromise = response.json();
        jsonPromise.then(function(json){
            weatherSpanButton.setAttribute("hidden","");
            weatherSpan.removeAttribute("hidden");
            weatherSpan.innerHTML = "Currently at trail: "+createWeatherString(json);
        });
      }
    });
}

function createWeatherString (weatherJson){
    let returnString = [];
    for (i=0; i<weatherJson.weather.length; i++){
        returnString.push(weatherJson.weather[i].description);
    }
    returnString= returnString.join(", ");
    returnString+='.';
    return returnString;
}

function getZipFromNumberZip (zip){
    let zipString = zip.toString();
    while (zipString.length<5){
        zipString = "0"+zipString;
    }
    return zipString
}