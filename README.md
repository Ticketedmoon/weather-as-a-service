# Weather-As-A-Service
A simple web application that allows the user to fetch the current weather for a city. This application also exports a public RESTful API for client-query.

![Application Image](./src/main/resources/assets/application-ui.PNG)

### Date
17th of May, 2021

### Location of deployed application
https://weather-as-a-service-app.herokuapp.com/

### Time spent
Core Goal of developing a Rest API: ~1 hour  
Finding real country/city/weather APIs to integrate with: ~1 hour  
Build simple UI: ~2 hours  
Deploying the application: ~2 hours (Springboot/React/Webpack project deployment on Heroku)

### Assumptions made
N/A

### Shortcuts/Compromises made
The API that I am using to get country and city information is a little finicky you may find out if you try certain 
countries/city combinations. I would have integrated with a better API for data quality and greater service reliability
otherwise! Sometimes you may notice if you select a certain country, that the cities won't be found - this is just a data issue
on their end I believe.

### Stretch goals attempted
- Attempted building a simple UI, with React/Typescript/Webpack and node.
- Deployed the application with the link in the readme.
- Proxied a real weather API for my service (http://api.weatherstack.com, however this is limited per month on the free-tier).

I did not add authentication to the interface for this project.

### Instructions to run assignment locally
- Ensure you maven installed on your system.
- You should then be able to clone the project from Github.
- Go to the root directory of the project where the `pom.xml` is located and type `maven clean package`.
- You should then be able to `cd` into the generated `target/` folder and run `java -jar [app-name].jar`
- **Note**: It is required that an API key is specified in the vm options of the jar execution, this will look like as follows:
`java -Dweather-api-access-key={YOUR_KEY_HERE} -jar [app-name].jar`

### What did you not include in your solution that you want us to know about?
I would have completed the authentication stretch goal using spring-security. Also, perhaps would have found a better 
country/city API to integrate with for this project, for data quality and robustness.

### Other information about your submission that you feel it's important that we know if applicable.
### Your feedback on this technical challenge
It was a good task, definitely applicable to the real world!
