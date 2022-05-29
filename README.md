# 4-Semester-TourPlanner
Links:
- Docker:  https://hub.docker.com/r/immxmmi/4-semester-tour-planer
- Git: https://github.com/immxmmi/4-Semester-SWEN2-TourPlanner.git 
- Download JAR: https://mega.nz/file/KzYFTZRZ#MzVwPVvgcpxhFEWgb0EPYYlIG2uRb-pifgH8_DAdbO4
- Download EXE: https://mega.nz/file/j2REyAhJ#EcaovJ5t6wvMWwYgdJpXZDsvsXd7NAkjVEn-ynia2y4
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
- 1a start APP
- 1b MainApplication -> modify options -> add VM options (ALT + V)
  * --module-path "C:\Program Files\Java\javafx-sdk-18.0.1\lib"  (Where you save JavaFX 18 zip)
  * --add-modules javafx.controls,javafx.fxml


### DOC ###

#Technical Aspects & Design Implementation
For this project we have tried following the MVVM (Model View ViewModel) – Pattern, while also strictly abiding to the SOLID – Principles. Therefore, the project is structured in several packages, each following its own component or feature towards the program.
The biggest three overarching packages, following the MVVM – pattern, are:
• The model package: This is where we declared and initialized the base attributes of our classes or objects. In this case, for example, attributes of Tours (tourID, title, distance, time etc.) while also using Enums for certain standardized values such as the type of Transporter or Level difficulty of the Tour. Essentially, it is our data and apps domain model.
• The ViewModel package: The ViewModel implements properties and commands to which the view can data bind to. This defines the functionality provided by the UI to the user and also notifies the View of any state changes through notification events. In the example of our project, the View Model is responsible for coordinating the Views or UIs interaction with any of our Model classes of the Tour.
• The View package: The View is responsible for defining the structure, layout and appearance of what the user sees on the screen. Each View is defined by FXML. The view does not contain business logic. In the case of our project, we made an overarching package called View, where we again branched into 2 packages, the ViewModel and the Controller, the Controller representing the implementation of the View Concept of the MVVM – pattern.
Furthermore, within this project, we have packages purely for the database (binding and operations), where within the database package we also have packages responsible for dealing with saving files from within the local computer through paths. For database operations we used the concept of Data Access Objects (DAO).
We also created packages for utility tools that we used. Such as: Hash generators, text colors, serializers (to/from JSON), date and data formatting. In addition to these common tools, we also created a ThreadFactory or ThreadMaker class. With the usage of threads, we wanted to increase the speed of the program so more processes can be run concurrently to result in a more efficient program.
Significantly, we also used a config file to easily edit and define fixed configurations. The configurations include database credentials, file paths, fixed window size settings of the program, versioning and the ID credential required for the MapQuest API usage.
Furthermore, at first, we used a local SQL database such as Postgres within a Docker container. However later during development we played around with an online database in the hopes of achieving the objective of everyone being able to use the program without requiring a local database setup as it can be quite bothersome for certain conditions. With this we wanted to achieve coherence. We found several free online host options for SQL databases but eventually settled with ElephantSQL, which offers PostgreSQL as a Service. The free option offers us 20MB and 5 concurrent connections which is more than sufficient in our case. Lastly, for logging our project, we used Log4J.
Mahmoud Ismail & Schako Khanaqa Software Engineering 2
In terms of unique features, we created a visual display of tour log statistics in terms of bar charts. This can be found within a tour, once it has been selected and further information of the tour is shown. Furthermore, we find the solution of an online SQL database to be unique and above the requirements of this project. As we offer both a traditional local SQL database with Postgres, as well as an online database, we count the addition of the online option to be a unique feature. Another unique feature is the possibility of choosing where to save the file from the program by asking the user to choose the path. What we are most proud of though is the inclusion of a .jar file of the application. Meaning it is an executable which can be opened without any setup by anyone on any computer and it will work as designed.
For collaborative and concurrent working we used GitHub. We set up a GitHub repository where we both would push and pull our progress to each other.
External Libraries and/or APIs included in the project:
• GSON
• Jackson
• Log4J
• MapQuest API
• Junit
• API Guardian
• Mockito
• Lombok
• SLF4J
• OpenTest
• Ikonli
• Core Maven dependencies
GitHub Link: https://github.com/immxmmi/4-Semester-TourPlanner
Docker Link: https://hub.docker.com/r/immxmmi/4-semester-tour-planer
Online Database: postgres://zqiwlukj:WVjZqzj-Jn-OzEPJ6ngmOYhhcHen4VyC@rogue.db.elephantsql.com/zqiwlukj
Failures & Selected Solutions
During the development of the project, we did not end up with failures. Everything we set out to achieve, we were able to successfully implement. However, naturally, we did have some complications with certain areas of the project, of which we will share instead. We had some complications with the Logger SLF4J, which is why we just ended up using the simpler Log4J instead. Furthermore, it also took a bit getting the hang of exporting Java objects onto a PDF document with the correct layout we wanted.
However, the biggest nuisance was JavaFX. JavaFX required a lot of dependencies to function properly. Consequently, at first it was difficult getting accustomed to how JavaFX works exactly with object binding and event triggers. Getting used to the workflow of JavaFX took a little time.
The biggest culprit of JavaFX was the SceneController, it was difficult coordinating the construction and achieving a certain structure which works dynamically.
Mahmoud Ismail & Schako Khanaqa Software Engineering 2
However, with the experience from last semester regarding the MonsterCardTrading Game, it was not such an intense challenge as most concepts generally did not feel foreign anymore. Understanding and using frameworks became more natural and faster.
