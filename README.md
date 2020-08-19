# RunShare

## Share your exercise and experiences in a social setting

### Features
* Create a personal login, with a callsign field that will be your representational avatar through the RunShare experience
* Detail trails that you have personal experience with, and share them with others
  * Name
  * Location
  * Comments Sections
  * Interactive real-time weather reporting for run planning
  * Mapping for trailheads
* Log Run Sessions with various tags for searching and discovery
  * Name your run
  * Tag a Trail
  * Runshare will automatically tag you on a Run Session when logged in
  * Add other runners who shared the Session with you
  * View your Sessions on your personal details page
* Communicate with other Runners and leave comments
  * Tag your comments with other Runner Callsigns, Run Sessions, or Trails and they will appear in those Detail pages
  * View all recent comments on the general comment page
 
### Future implementations
* Adding Trail and Session ratings system
* Allowing uploads of pictures to Runners, Trails, Sessions and Comments
* Additional weather update information
* Navigational data for Trailheads from Runner home locations

### Cloning
* Cloned versions of this app should reference the original repository, and attribute creation to Andrew Koch, with a link to this repo
* The API calls for mapping and weather will need to have a JS file created in the /resources/static/js folder named weatherAPI. This has been left out of the repo for security reasons.  You will need to get API keys from both https://openweathermap.org/  and https://developer.mapquest.com/documentation/directions-api/ In this file, you will need to create 2 functions and enter the api keys you recieve:
    * `function callApiKey (){ return "{api key from
 OpenWeatherAPI}";}` 
    * `function callDistanceApiKey
  () { return "{api key from
 Mapquest}";}`
