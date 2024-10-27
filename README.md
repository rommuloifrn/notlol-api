# notlol

![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

Based on the Riot Games API, returns the time since the last match of a player by his gamename and tagline.

## How to run this

Firstly you will need Java SDK 17 and a riot developer key. Go to src/resources and put your key there:

    api_key=PUT_YOUR_KEY_HERE_DO_YOU_UNDERSTAND

then you can go to root dir and run the command:

    ./mvnw spring-boot:run

## Goals

- [ ] Return the time since the last march of a player
- [ ] Refactor the spaghetti code
- [ ] Document the endpoints
- [ ] Implement accounts with Riot RSO ...?