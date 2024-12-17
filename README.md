
# PG3402-Mikrotjenester-dragepust

## Project Overview

This project is modelled after a role-playing game called Dragepust. The application will primarily help and simplify the character creation process by keeping track of things such as rules, choices and calculations, so that the players can create a new character on the go. With an existing character, the user will be able to interact with them by doing things like: adding or losing health, track spells and magic, and roll dice.

Functionality:

* Create a user, or log in
* Create a new character
* Select an existing character
* Interact with an existing character
* Create a campaign
* Join an existing campaign
* Interact with an existing campaign
* Roll dice for character in character-sheet and campaign-sheet



## How to run via docker
For Frontend
```
cd Frontend/microClient
npm install
npm run dev
```
Build and run in Backend
```
cd docker
docker compose -f .\docker-compose.yml up
```

Forces docker to rebuild images even if they exist 
```
docker compose -f .\docker-compose.yml up --build
```

If any issues occurs, this command removes ALL docker images and volumes which might help
```
docker system prune -a --volumes
```
## How to run backend locally (if full containerization fails)
Run rabbit and consul
```
cd docker
docker compose -f .\docker-compose-local.yml up
```
Start each service individually
Service that needs to be started locally
* api-gateway
* campaign
* diceroller
* items
* messenger
* playerCharacter
* stats
* user


## Example use case
**1: Build and run system:**
```
Cd docker 

docker compose -f .\docker-compose.yml up
```
**2: Build and run frontend**
```
Cd Frontend/microClient 

Npm install 

Npm run dev 
```
**3: Open consul and RabbitMQ in browser**

* Consul at localhost:8500 

* RabbitMQ at localhost:15672 

  * Username: guest 

  *  Password: quest 

**4: Open frontend at localhost:5173**

* Create an account or login 

**5: Create a Character**

   * Name 

   * Species 

   * Profession 

   * Equipment 

**6: Enter click on character**

   * Update hp, willpower and mana (asynchronously (Refresh page) 

   * Roll dice directly with diceroller service (Synchronously) 

**7: Click “back to menu” -> click “Campaign menu” -> click “Create new campaign”**

   * Campaign Name 

   * Campaign Description 

**8: Click on campaign (Which currently has no players)**

   * Messages in chat are sent asynchronously via rabbitmq with a delay to simulate load, this request is load balanced via 3 replicas of campaign service.

**9: Go back to character menu**

   * Character can be added to campaigns via the “Campaign ID” field (CampaignID is the bottom value on a created campaign)

   * Once a character is added to a campaign it should appear when accessing the campaign 

**10: Go to the campaign of the added character**

   * Owner of the campaign can access the character 

   * Owner of the campaign can roll dice for the character 

   * Character rolls are treated the same way as messages (Asynchronously)

**11: Campaign and characters can be deleted**


### User Stories from Arbeidskrav:

* As a user I can create a new character using the character generator, so that I can
  quickly and easily be ready to play (Done)


* As a user I can choose a character from the character selection, so that I can change
  between my favourite characters (Done)


* As a user I can interact with their existing character, adding itemset, health etc., so that I
  can keep updating the character (Done)

### User Stories from Eksamen
In addition, we added the following user stories based on feedback from the arbeidskrav:

* As a user I can create a campaign using the campaign generator, so that I can play with
  keep track of my players(Done)

* As a user I can join an existing campaign, so that I can play with my party(Done)


Architecture diagram from arbeidskrav
![Arbeidskrav Arkitektur](Docs/ArbeidskravArkitektur.PNG)
Final architecture diagram
![Eksamen Arkitektur](Docs/EksamenArkitektur.PNG)





### On Teamwork
As a group of two, we planned and discussed the project specifications together. Because of this we were able to take advantage of the modularity microservices provide, letting us work on different parts of the project at the same time.

Both worked on:
* Character Service
* Frontend

Candidate 10 worked on:
* Setting up Frontend configuration(Routes, HTTP requests, etc.)
* User service
* Stats service


Candidate 11 worked on:
* Campaign Service
* Item service
* Messenger Service
* Dice roller service
* api-gateway
* Setting up Backend configuration (Docker, Consul, RabbitMQ, Postgres, load balancing, consul-importer) 
