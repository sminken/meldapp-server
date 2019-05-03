# meldApp Server

Server ten behoeve van het versturen van notificaties aan Android gebruikers. De server heeft event listeners op de Firebase database en
stuurt een notificatie naar het toestel van de gebruiker indien nodig. Wanneer de server onverhoopt offline is geweest stuurt deze alsnog een notificatie d.m.v. de ScheduledExecutorService.

Server ten behoeve van de eindopdracht <b>CPP java/ Android programmeur bij de Open Universiteit Nederland</b>. (december 2018). De server opzetten was een gitlab issue voor mijzelf.  Er is gebruikgemaakt van <b>Slack</b> voor regelmatig overleg en <b>Gitlab</b> voor versiebeheer. 
Voor de database wordt gebruikgemaakt van de <b>Firebase</b> realtime database (Google). Voor het verzenden van notificaties is gebruik gemaakt van deze server die eventlisteners heeft op de database. 
Dit project is vormgegeven in een IntelliJ project.
## Getting Started


Om te draaien voor uit:
```
./gradlew :database:run
```

### Prerequisites

De server kan gedraaid worden vanuit de command line of vanuit IntelliJ met behulp van bovenstaande opdracht.

## Running the tests

Voor de klassen Database, Gebruiker en Melding zijn testklassen aangemaakt.

### Architectuur

Gezien het gering aantal klassen is ervoor gekozen de drie klassen in een enkele package te plaatsten. De testklassen bevinden zich in de test folder.

## Built With

* IntelliJ
* Gradle
* Firebase
* Java

## Versioning

Versiebeheer is verwijderd i.v.m. met de privacy van de overige teamleden.

## Auteurs

anoniem

