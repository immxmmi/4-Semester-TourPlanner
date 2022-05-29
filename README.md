<h1 align="center">  Tour Planner Pro</h1>
<div align="center">
  <a href="https://github.com/immxmmi/4-Semester-TourPlanner">
    <img src="git_img/Logo.jpg" alt="Logo" width="1000" height="500">
  </a>

  <p align="center">
    <br/>
     <strong>Momo && Schako</strong>
    <br />
    <br/>
    <a href="https://github.com/immxmmi/4-Semester-TourPlanner/blob/main/doc/TourPlannerDocumentation.pdf">Documentation</a>
    .
    <a href="https://github.com/immxmmi/4-Semester-TourPlanner/issues">Report Bug</a>
    ·
    <a href="https://github.com/immxmmi/4-Semester-TourPlanner/issues">Request Feature</a>
  </p>
</div>


# Links:
- Docker:  https://hub.docker.com/r/immxmmi/4-semester-tour-planer
- Git: https://github.com/immxmmi/4-Semester-TourPlanner.git 

# Start
- JAR 
   - Download TourPro.jar: https://mega.nz/file/KzYFTZRZ#MzVwPVvgcpxhFEWgb0EPYYlIG2uRb-pifgH8_DAdbO4
   - Prerequisites for Jar Version (Java JDK 18)
   *Check Java Version 
     ```sh
      java --version
    ```
   - Download Java JDK 18
   - WINDOWS:
     - https://www.oracle.com/java/technologies/downloads/#jdk18-windows 
   - Linux:
     - https://www.oracle.com/java/technologies/downloads/#jdk18-linux
   - MacOS:
     - https://www.oracle.com/java/technologies/downloads/#jdk18-mac 
   - Start Tour Planner.jar
     *Console Start: [in TourPro File] 
     ```sh
       java --jar "Tour Planner.jar"
    ```
   
- EXE
   - Download TourPro.exe: https://mega.nz/file/j2REyAhJ#EcaovJ5t6wvMWwYgdJpXZDsvsXd7NAkjVEn-ynia2y4

# Developer

## Prerequisites
- JavaFX 18 (javafx-sdk-18.0.1)
- https://download2.gluonhq.com/openjfx/18.0.1/openjfx-18.0.1_windows-x64_bin-sdk.zip
- Java SE Development Kit 18.0.1.1
- https://www.oracle.com/java/technologies/downloads/

## Installation
- git clone https://github.com/immxmmi/4-Semester-SWEN2-TourPlanner.git
- edit config: local Database or use online Database

### Local Database
- docker pull immxmmi/4-semester-tour-planer
- docker run --name swe2db -e POSTGRES_USER=swe2user -e POSTGRES_PASSWORD=swe2pw -p 5432:5432 postgres
- Datenbank - Database: swe2db Username: swe2user PW: swe2pw
 
### Online Database
- https://www.elephantsql.com/

### Start ###
- start App
- MainApplication -> modify options -> add VM options (ALT + V)

### Prerequisites
*VM Options
  ```sh
  --module-path "C:\Program Files\Java\javafx-sdk-18.0.1\lib"  (Where you save JavaFX 18 zip)
  --add-modules javafx.controls,javafx.fxml
  ```


