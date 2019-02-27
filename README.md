# Stormbound Analyzer

## Prerequisites
1. Java 8 or higher JDK
2. [JUnit](https://junit.org/junit5/docs/current/user-guide/#overview) Platform Console Standalone Jar (for testing)

## Installation

1. Clone the repo

  `git clone -b master https://github.com/bdavidhicks/stormbound-analyzer.git`

2. Change directory to the new repo

  `cd stormbound-analyzer`

3. Download JUnit Platform Console Standalone Jar from [Maven](https://search.maven.org/search?q=a:junit-platform-console-standalone)

4. Copy the file to the repo root folder.

  `cp YOUR_DOWNLOAD_FOLDER\junit-platform-console-standalone-1.4.0.jar YOUR_REPOSITORY_FOLDER\stormbound-analyzer`

5. Install Java JDK and set the path environment variable. More to come on this...

6. Run the command below from the repo root folder to compile the Java files.

  `javac -d out Faction.java Rarity.java Choice.java Position.java Tile.java Board.java Player.java Card.java Deck.java Hand.java Spell.java Summon.java Unit.java Structure.java Base.java PlayerBase.java UnitTypes/*.java Neutral/*.java Swarm/*.java Ironclad/*.java Game.java GameRunner.java`

7. Run the following command from the repo root folder to compile the tests.

  `javac -d out -cp out:junit-platform-console-standalone-1.4.0.jar tests/*.java`

8. Run the following command from the repo root folder to run the tests.

  `java -jar junit-platform-console-standalone-1.4.0.jar --class-path out/ --scan-classpath`

9. Run the following command from the repo root folder to run the game.

  `java -cp out com.stormboundanalyzer.GameRunner`
