
# Language: Scala

# Compiler: Intellij IDEA

You are free to choose three levels,  separately based on Greedy Method, Traditional search, Min-Max Algorithm.

Just run it and you will have fun and lost in the chessing world!!

# Record
# Completed:
  1. UI interface
  2. Ability to play against players and players
  3. Can repent any time
  4. Simple AI, implement player vs AI

# Not completed:
  1. Ability to judge the outcome and score
  2. Ability to save the game and load the game
  3. Record names and scores for different players and generate leaderboards
  4. Implement networking features
  5. Can start new games multiple times

# Design and approach
  1. A two-dimensional array for storage
  2. For Repentance operation, we use stack to store each step's location
  3. Main class implements the creation and initialization of the framework by calling Chessbutton, etc., and then transfers the control to MyChessPosition to start the game.
  4. Three levels work well through Greedy algorithm, Direct search and Min-max algorithm.
  
# To be improved: 
   1. background pictures. a little disgusting.
   2. Timer delay.



    

