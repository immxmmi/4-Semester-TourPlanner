package at.technikum.tourplanner.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TourStatistics {
    //COUNT
    int numberOfTourlogs;

    //LEVEL
    int numberOfLevelEasy;
    int numberOfLevelNormal;
    int numberOfLevelHard;
    int numberOfLevelExpert;

    //STARS
    int numberOfStarsNone;
    int numberOfStarsOne;
    int numberOfStarsTwo;
    int numberOfStarsThree;
    int numberOfStarsFour;
    int numberOfStarsFive;

    //AVG
    double avgTotalTime;
    double avgDistance;

}
