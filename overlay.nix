# overlay.nix
final: prev: {
  myMavenProject = final.callPackage ./derivation.nix { };
}
