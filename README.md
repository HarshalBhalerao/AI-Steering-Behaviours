# AI Steering Behaviour Game -> Pacman fights back!

## Description of the logic and design of my game
The logic is as follows:
1. The ghost(monster) will chase pacman (Seek Steering behaviour). 
2. To keep the game simple, I implemented a score system. When the score reaches greater than or equal to 1000. A holy cross will spawn.
3. The pacman will get the holy cross and the ghost will run away from pacman (Fleeing Steering behaviour).

The game has a health system implemented.
1. Pacman has 100 health and loses 2 health when ghost's model intersects the player's model (pacman hasn't acquired the cross).
2. Ghost has 200 health and it loses 2 health when player's model intersects the ghost's model (pacman has acquired the cross).
3. When cross is acquired, the pacman gains full health and ghost starts fleeing away. 
4. In both the points 1 and 2 mentioned above, the chaser which could be pacman or ghost loses no health (depending on whether the cross 
has been acquired or not). It is always the chased one which loses health.  


### Orbs and Flocking behaviour (Complex Steering Behaviour)
The complex behaviour implemented in my game is Flocking. I am using filled white circles to signify orbs. And these orbs are moving in a
flocking pattern. Once they reach one of the edges of the game screen, their position changes and they are shifted to the opposite edge
to ensure continuity.


### Player and Ghost boundaries
Unlike orbs, the pacman and ghost have a set boundary which is invisible. This was done so that the players don't exploit the game 
mechanics. The player can click any specific key and can keep heading in that direction, in such a case the ghost would never be able to 
catch upto the player. The boundaries have been implemented in such a way that when the player or ghost reaches the boundary, their 
position is shifted inwards by a few pixels. This was done because during the implementation of the game, I noticed that during the ghost 
fleeing, it would get into one of the 4 corners and would break the boundary, heading to infinity. 

Another important thing to mention is the boundary difference between player and ghost. The ghost boundary is smaller than the player's 
boundary, this is because when the ghost flees, it is likely to be cornered. And because the player boundary is bigger, it is likely that
the player would head towards the ghost and cross the ghost's boundary which will give a chance for the ghost to flee away.

### Animation
Ghost image has no animation but the pacman does.
It is quite simple, we have a full circle pacman and we have 4 different pacmans with open mouth pointing in 4 directions. I just place 
the full circle pacman image over the open mouth pacman and vice-versa. When the player presses any WASD key, the open mouth image 
changes to point to that specific direction.

### Start and end state of the game
When the user hits run, the game directly starts. There are 2 end states.
1. If the player loses all its health, then the message "You Lose!" is displayed on the screen with the score.
2. If the player wins by making the ghost lose all its health, then the message "You Win !" is displayed on the screen with the score.

In both the cases, the game is present in the background. The reason why this was done is because 
1. If the player plays the game, all the player would care about is winning and thus would not notice the flocking behaviour in the 
background or how the seek or flee behaviour functions. 
2. Having a menu screen or end game screen for a game like this one where all the player can do is start a new game would be a waste of 
time.
 

### Final words for this section
All the logic and design of the game mentioned above was done to make sure the game functions smoothly and that the computer AI looks 
intelligent even if it performs simple steering behaviour like seek and flee.


## Instructions on how to compile and run the game

I have provided all the files this repo. I successfully ran my code in Powershell.
If you are running this in WSL then you need Windows X server configured. 
- Clone this repository.
- Navigate to the folder `AI-Steering-Behaviours`.
- To compile the project in java, run command: `javac .\main\Main.java`
- To execute the project in java, run command: `java main.Main`
- To run .jar file, run command: `java -jar ./PacmanGame.jar`. Or this .jar file could be run by simply double clicking on it.
- In case the jar file returns an error (like java.lang.UnsupportedClassVersionError), you can create a new jar file with this command:
    ```
    jar -cfvm PacmanGame.jar .\Manifest.txt .\main\*.class .\gameObjects\*.class .\background\*.class .\images\booster\*.png .\images\ghost\*.png .\images\player\*.png
    ```


## Description of steering behaviours implemented

### Seek
It is the opposite of fleeing behaviour and it to acheive it we do the following:
1. We get the direction vector to the target object. This can be done by subtracting the current coordinates from the target coordinate. 
2. We normalize this direction vector. Normalizing is the process of making the current existing vector to unit length 1.
3. Now, we have the direction. So we need to head to that direction with a specific speed. So we multiply this normalized direction 
vector with our fixed velocity. This will give us the velocity to head in that specific direction.
4. To get the ghost to seek the player, we need to update its coordinates. So we update the coordinates by adding the velocity calculated 
above to the current coordinates. 

### Flee
It is the opposite of seeking behaviour and thus we just change its direction by multiplying it by -1.

### Flocking
A more detailed explaination could be found in Orbs.java. Each and every line is explained in detail. But the overview is as follows:
1. Cohesion: A single boid will keep track of its neighbouring boids and will make sure to stay together with other local boids' or 
flockmate's center of mass.
2. Separation: Each boid avoids running into other neighbouring boids by applying opposite force. Thus avoiding collision.
3. Align: Each boid will ensure to match the velocity of its local flockmates. 