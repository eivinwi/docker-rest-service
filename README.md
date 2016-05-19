## Docker Rest Service
##### Should probably be renamed as it is not really a REST service anymore.

This is a server application used to serve Tasks and installation guides for our Docker workshop.The workshop ~~probably~~
won't make ~~much~~ any sense unless you speak Norwegian, so all the instructions will be in Norwegian.


### Installasjon
Alle instruksjoner forutsetter at man står i mappen 'app'. Krever at du har npm installert, hvilket
på Windows og Mac blant annet kommer med vanlig Docker-installasjon.
```
npm init
npm install
```

NB: På Windows kan det være nødvendig å kjøre eksplisitt følgende kommando også: 'npm install -g http-server'.

##### Kjøring
Workshop-delen består kun av statiske html-sider, og serveren kan lett startes med:
```
http-server .
```
Serveren er nå tilgjengelig på http://localhost:8080.

##### Kjøring med Docker
Alternativt kan workshoppen også kjøres som en Docker container. Dette er en veldig rask og enkel måte å kjøre opp
workshoppen på en Docker host.
```
docker build -t workshop .
docker run -d -p 80:8080 workshop
```

### Server-applikasjonen
Prosjektet inneholder også en Java-applikasjon som er tenkt å fungere som en server som deltakerne skal koble seg på på
forskjellige måter i løpet av workshoppen. Applikasjonen lar folk melde seg på med lagnavn, og følger med på fremgangen til
lag, som blir målt mot hverandre på tid. Denne er for tiden ikke i bruk, fordi det ville ta for mye tid å klargjøre den.


### Client-applikasjonen
Prosjektet inneholder også en klient-applikasjon som hver av deltakerene på workshoppen skal bruke,
[denne er beskrevet her](https://github.com/eivinwi/docker-client).






