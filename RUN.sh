#!/usr/bin/env bash
LWJGL="lwjgl/* lwjgl-opengl/* lwjgl-glfw/* lwjgl-stb/*"
CP=$(echo $LWJGL | sed "s/\([^ ]*\)/lib\/\1/g" | tr ' ' ':')
ARGS=$@

MAINCLASS="Jouer" # classe pour lancer une partie de monopoly

# Si on entre aucun argument, demander d'entrer des pseudo
if (( $# == 0 )); then
  read -p "pseudo Joueur 1 : " j1
  read -p "pseudo Joueur 2 : " j2
  read -p "pseudo Joueur 3 : " j3
  read -p "pseudo Joueur 4 : " j4

  ARGS="$j1 $j2 $j3 $j4"

elif [ $1 == "--taquin" ]; then
  MAINCLASS=jeuTaquin.JouerTaquin # Lancer le jeu du taquin si le premier argument est "--taquin"
  ARGS="$2"
fi

# Executer le projet (on lance la première commande si c'est linux et l'autre si c'est macos)
if [ $(uname -s) == "Linux" ]; then
  java -cp $CP:monopoly.jar $MAINCLASS $ARGS
else
  java -XstartOnFirstThread -cp $CP:monopoly.jar $MAINCLASS $ARGS
fi
