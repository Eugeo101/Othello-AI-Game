#Library import
from tkinter import *
from math import *
from time import *
from random import *
from copy import deepcopy

#Simple heuristic. Compares number of each tile.
def dumbScore(array,player):
	score = 0
	#Set player and opponent colours
	if player==1:
		colour="b"
		opponent="w"
	else:
		colour = "w"
		opponent = "b"
	#+1 if it's player colour, -1 if it's opponent colour
	for x in range(8):
		for y in range(8):
			if array[x][y]==colour:
				score+=1
			elif array[x][y]==opponent:
				score-=1
	return score

#Heuristic that weights corner tiles and edge tiles as positive, adjacent to corners (if the corner is not yours) as negative
#Weights other tiles as one point
def decentHeuristic(array,player):
	score = 0
	cornerVal = 25
	adjacentVal = 5
	sideVal = 5
	#Set player and opponent colours
	if player==1:
		colour="b"
		opponent="w"
	else:
		colour = "w"
		opponent = "b"
	#Go through all the tiles	
	for x in range(8):
		for y in range(8):
			#Normal tiles worth 1
			add = 1
			
			#Adjacent to corners are worth -3
			if (x==0 and y==1) or (x==1 and 0<=y<=1):
				if array[0][0]==colour:
					add = sideVal
				else:
					add = -adjacentVal


			elif (x==0 and y==6) or (x==1 and 6<=y<=7):
				if array[7][0]==colour:
					add = sideVal
				else:
					add = -adjacentVal

			elif (x==7 and y==1) or (x==6 and 0<=y<=1):
				if array[0][7]==colour:
					add = sideVal
				else:
					add = -adjacentVal

			elif (x==7 and y==6) or (x==6 and 6<=y<=7):
				if array[7][7]==colour:
					add = sideVal
				else:
					add = -adjacentVal


			#Edge tiles worth 3
			elif (x==0 and 1<y<6) or (x==7 and 1<y<6) or (y==0 and 1<x<6) or (y==7 and 1<x<6):
				add=sideVal
			#Corner tiles worth 15
			elif (x==0 and y==0) or (x==0 and y==7) or (x==7 and y==0) or (x==7 and y==7):
				add = cornerVal
			#Add or subtract the value of the tile corresponding to the colour
			if array[x][y]==colour:
				score+=add
			elif array[x][y]==opponent:
				score-=add
	return score
