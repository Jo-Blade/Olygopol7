{ nixpkgs ? import <nixpkgs> {  } }:
 
let
  nixgl = import (fetchTarball https://github.com/guibou/nixGL/archive/main.tar.gz) {};

  pkgs = [
    nixpkgs.gnumake
    nixpkgs.openjdk17
    nixpkgs.python311
    nixgl.auto.nixGLDefault
  ];
 
in
  nixpkgs.stdenv.mkDerivation {
    name = "env";
    buildInputs = pkgs;
  }
