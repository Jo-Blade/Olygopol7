# overlay.nix
final: prev: {
  myMavenProject = final.callPackage ./myPackage.nix { };
}
