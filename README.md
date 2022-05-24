# 4-Semester-TourPlanner
Links:
- Docker:  https://hub.docker.com/r/immxmmi/4-semester-tour-planer
- Git: https://github.com/immxmmi/4-Semester-SWEN2-TourPlanner.git 
- Download: https://mega.nz/file/Sq5AWRCJ#bRfds9eHf9_rATDGGH4ehuLngnFWhQGOW7ZD3JrIYjE
## Installation
- git clone https://github.com/immxmmi/4-Semester-SWEN2-TourPlanner.git
- docker pull immxmmi/4-semester-tour-planer
- docker run --name swe2db -e POSTGRES_USER=swe2user -e POSTGRES_PASSWORD=swe2pw -p 5432:5432 postgres
- Datenbank - Database: swe2db Username: swe2user PW: swe2pw

## NEED TO START ##
- JavaFX 18 (javafx-sdk-18.0.1)
- https://download2.gluonhq.com/openjfx/18.0.1/openjfx-18.0.1_windows-x64_bin-sdk.zip
- Java SE Development Kit 18.0.1.1
- https://www.oracle.com/java/technologies/downloads/


### CONFIGURATIONS ###
- MainApplication -> modify options -> add VM options (ALT + V)
  * --module-path "C:\Program Files\Java\javafx-sdk-18.0.1\lib"  (Where you save JavaFX 18 zip)
  * --add-modules javafx.controls,javafx.fxml
