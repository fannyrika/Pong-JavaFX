# Jeu de Pong

## Présentation

Ce Pong est un jeu de raquettes programmé en Java 11 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu [Pong](https://fr.wikipedia.org/wiki/Pong), un classique des salles d'arcades dans les années 1970.

Le principe est simple : un terrain (le "*court*") deux raquettes et une balle. Le jeu se joue à deux, chaque joueur pouvant déplacer sa raquette sur un axe haut/bas et ayant pour but de ne pas laisser passer la balle derrière sa raquette (ce qui cause sa défaite immédiate). La balle se déplace à vitesse constante et rebondit sur les parois (limites haute et basse de la fenêtre) et sur les raquettes.

Pour l'instant, seules les fonctionnalités basiques sont implémentées : 2 raquettes contrôlées par 2 paires de touches sur le clavier ; gestion basique des rebonds de la balle et détection de sortie du terrain par les côtés (but marqué).

## Exécution, compilation

Prérequis : vous avez besoin de Java 11.

1. Télécharger le projet en le clonant via git: `git clone https://gaufre.informatique.univ-paris-diderot.fr/cproj2022/pong.git`.
2. Compiler avec gradle. La commande est, dans le répertoire du projet, exécutez `./gradlew build`. Au besoin, cela téléchargera la bonne version de gradle.
3. Exécuter avec `./gradlew run` (cela inclut `./gradlew build` si le projet n'est pas déjà compilé).
4. Jouer ! La raquette de gauche est contrôlée par les touches CONTROL et ALT, alors que celle de droite est contrôlée par les flèches HAUT et BAS.
