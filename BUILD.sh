#!/usr/bin/env bash
LWJGL="lwjgl/* lwjgl-opengl/* lwjgl-glfw/* lwjgl-stb/*"
DOSSIERS="src src/logiqueMonopoly src/interfacegraphique src/moteurGraphique/drawable src/moteurGraphique/drawable/instance src/moteurGraphique/glThread src/moteurGraphique/glType src/moteurGraphique/vecteur src/moteurGraphique/window src/jeuTaquin"
CP=$(echo $LWJGL | sed "s/\([^ ]*\)/lib\/\1/g" | tr ' ' ':')

# Télécharger lwjgl si absent
if [ ! -f lwjgl.zip ]; then
  wget https://github.com/LWJGL/lwjgl3/releases/download/3.3.2/lwjgl.zip
fi

# Extraire que les bibliothèques utilisées dans le projet si absentes
if [ ! -d lib ]; then
  mkdir lib
  for d in $LWJGL; do
    unzip lwjgl.zip $d -d lib
  done
fi

# Compiler le projet si absent
if [ ! -f monopoly.jar ]; then
  for d in $DOSSIERS; do
    javac -cp $CP:$(echo "$DOSSIERS" | tr ' ' ':') $d/*.java 
  done

  jar -c -f monopoly.jar -C src/ .

  for d in $DOSSIERS; do
    rm $d/*.class
  done
fi

tar -c -f olygopol7.tar monopoly.jar RUN.sh lib res
