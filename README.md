# Pokemon Software Engineering Challenge

This is an application simulating a Pokedex in the form of a REST API 
that returns Pokemon information, using external APIs such as
https://pokeapi.co for Pokemon information, and https://funtranslations.com
for the translations.

## Technologies Used

- Java 17
- SpringBoot 3.5.5
- Mapstruct
- Maven
- GitHub Actions
- Docker

## Requirements

Only Docker is required.

## How to Run the Project

From the root of the project (where the `Dockerfile` is located), run:

#### For Build
```bash 
docker build -t pokeapp:latest . 
```

#### For Execute
```bash
docker run -p 5000:5000 pokeapp:latest
```

#### URL
Now the service is running on: http://localhost:5000

## Endpoints
### 1. Basic Pokemon Information
**GET** `/pokemon/{name}`

Given a Pokemon name, returns standard Pokemon description and additional information.

**Example Call:**
```bash
http://localhost:5000/pokemon/bulbasaur
```

**Example Response:**
```json 
{
  "name": "bulbasaur",
  "description": "A strange seed was planted on its back at birth. The plant sprouts and grows with this POKéMON.",
  "habitat": "grassland",
  "isLegendary": false
}
```

### 2. Translated Pokemon Description
**GET** `/pokemon/translated/{name}`

Given a Pokemon name, return basic information and translated Pokemon description, with the following rules:
1. If the pokemons habitat is `cave` or the `isLegandary` status is true, Yoda Translation will be applied.
2. For all the other cases, Shakespeare Translation will be applied.

**Example Call:**
```bash
http://localhost:5000/pokemon/translated/bulbasaur
```

**Example Response:**
```json 
{
  "name": "bulbasaur",
  "description": "A strange seed wast planted on its back at birth. The plant sprouts and grows with this pokémon.",
  "habitat": "grassland",
  "isLegendary": false
}
```

### Note

If for any reason there's an error calling the needed API to do this translations, this application will response with the standard description.

## Changes for Production

1. I'm not using ENV variables for the integration with the APIs because are fully public and this is an MVP, but for production I would put this variables as env variables.
2. I would do validations of the PokeAPI data responses, to ensure data integrity.
3. The endpoints should start with /v1 to give version visibility for the clients using the API.
4. If the product scales a lot, I would configure a Redis Cache instead of using a default Cache, to ensure horizontal scaling and a stateless product. 

## Author

This project was developed by `Germán Furfori` as part of a technical evaluation.