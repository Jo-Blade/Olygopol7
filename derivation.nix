{ pkgs ? import <nixpkgs> {} }:
let
  mvn2nix = import
    (fetchTarball "https://github.com/fzakaria/mvn2nix/archive/master.tar.gz")
    { };
  mavenRepository =
   mvn2nix.buildMavenRepositoryFromLockFile { file = ./mvn2nix-lock.json; };
inherit (pkgs) lib stdenv openjdk17 maven makeWrapper libglvnd;
inherit (stdenv) mkDerivation;
in mkDerivation rec {
  pname = "Olygopol7";
  version = "1.0-SNAPSHOT";
  name = "${pname}-${version}";
  src = lib.cleanSource ./.;

  nativeBuildInputs = [ openjdk17 maven makeWrapper ];

  buildPhase = ''
    echo "Building with maven repository ${mavenRepository}"
    mvn package --offline -Dmaven.repo.local=${mavenRepository}
  '';

  installPhase = ''
    # create the bin directory
    mkdir -p $out/bin

    # create a symbolic link for the lib directory
    ln -s ${mavenRepository} $out/lib

    # copy out the JAR
    # Maven already setup the classpath to use m2 repository layout
    # with the prefix of lib/
    cp target/${name}.jar $out/

    # create a wrapper that will automatically set the classpath
    # this should be the paths from the dependency derivation
    makeWrapper ${openjdk17}/bin/java $out/bin/${pname} \
          --set LD_LIBRARY_PATH ${libglvnd}/lib \
          --add-flags "-jar $out/${name}.jar"

    # creer un wrapper pour lancer le jeu de taquin
    makeWrapper ${openjdk17}/bin/java $out/bin/${pname}-taquin \
          --set LD_LIBRARY_PATH ${libglvnd}/lib \
          --add-flags "-cp $out/${name}.jar jeuTaquin.JouerTaquin"
  '';
}
