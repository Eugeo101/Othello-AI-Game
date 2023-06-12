# Othello-AI-Game
# AI Team 11

## Requirements
* language used: Java
## difficulty level
* The game has 3 levels of difficulty by depth of game-tree {Easy: randomAI, Medium: DynamicAI, Hard: DynamicAI}

## Heurstics used:
We got many heuristics depending on the phase of the game, whether early, medium, or hard.

### First: Determine Game Phase:
We determine the phase of the game by the total number of stones in the game board (sc)
If sc < 20: game phase is EarlyGame.
If 20 < sc <= 58: game phase is MediumGame.
If sc > 58: game phase is HardGame.
### Second: Explain Heurstic Cases:
Used to determine the value of leaf nodes in the search tree where:
{easy mode: have randomAI, medium mode: have depth=1 & use DynamicAI, hard mode: have depth=6 
& use DynamicAI}
randomAI: choose random move from the valid moves.
DynamicAI: use heuristics to evaluate leaf nodes in the search tree.
Heuristic Types:
a) Corner: 100 * 𝑚𝑦𝑐𝑜𝑟𝑛𝑒𝑟𝑠−𝑜𝑝𝑒𝑛𝑒𝑛𝑡𝑐𝑜𝑟𝑛𝑒𝑟𝑠
𝑚𝑦𝑐𝑜𝑟𝑛𝑒𝑟𝑠+𝑜𝑝𝑒𝑛𝑒𝑛𝑡𝑐𝑜𝑟𝑛𝑒𝑟𝑠+1
b) Valid Moves: same as corner heuristic but calculate valid moves of user player and the opponent
(AI computer player)
c) Stone Count: same as corner heuristic but calculate the number of stones of the user player and 
the number of stones of the opponent (AI computer player)
d) Parity: which calculate if remanning number of discs is even or odd to determine state of the 
game
