# Jeu de Pong

## Présentation

Ce Pong est un jeu de raquettes programmé en Java 17 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu [Pong](https://fr.wikipedia.org/wiki/Pong), un classique des salles d'arcades dans les années 1970.

Le principe est simple : un terrain (le "*court*") deux raquettes et une balle. Le jeu se joue à deux, chaque joueur pouvant déplacer sa raquette sur un axe haut/bas et ayant pour but de ne pas laisser passer la balle derrière sa raquette (ce qui cause sa défaite immédiate). La balle se déplace à vitesse constante et rebondit sur les parois (limites haute et basse de la fenêtre) et sur les raquettes.

Pour l'instant, seules les fonctionnalités basiques sont implémentées : 2 raquettes contrôlées par 2 paires de touches sur le clavier ; gestion basique des rebonds de la balle et détection de sortie du terrain par les côtés (but marqué).


## Instructions

Dans les instructions ci-dessous, il faut remplacer `myteam` par
- soit le nom de votre équipe dans gaufre, si le dépôt a été forké au nom de votre équipe,
- soit le login du membre de votre equipe qui a forké le dépôt pour tout le monde,
- soit `cproj2022`, si vous voulez cloner directement le dépôt de l'équipe enseignante.

Notez que dans le dernier cas, vous pourrez compiler et exécuter le projet, mais pas faire de `git push`.

### Télécharger Pong

Le plus pratique pour télécharger Pong afin de participer à son developpement, c'est de cloner le dépôt. Depuis la console :

```bash
$ git clone https://gaufre.informatique.univ-paris-diderot.fr/myteam/pong
```

#### Depuis une machine de TP de la Halle aux Farines

Il semble que l'installation de git sur les machines de TP refuse de reconnaître le certificat de gaufre. Heureusement, on peut demander à git d'ignorer la vérification du certificat :


```bash
$ git clone -c http.sslVerify=false https://gaufre.informatique.univ-paris-diderot.fr/myteam/pong
```

Ensuite, vous pouvez enregistrer de façon permanente votre choix d'ignorer la vérification pour ce dépôt :

```bash
$ cd pong
$ git config http.sslVerify false
```
(cela permettra de faire des `git push`, des `git pull` et des `git fetch` sans avoir à préciser à chaque fois `-c http.sslVerify=false`)
## Exécution, compilation

Après avoir téléchargé/cloné les sources, vous pouvez compiler et exécuter le projet à l'aide de gradle.
Le principe c'est que le script `gradlew` dans le répertoire du projet téléchargera puis utilisera la version de gradle qui fonctionne avec le projet.

Pour compiler, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew build`
```

Pour exécuter, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew run`
```



Le projet en lui-même a besoin de Java 17 pour être compilé et exécuté.
### Cas particuliers

#### Sur une machine de TP de la Halle aux Farines, depuis la console

Si vous travaillez depuis une machine des salles de TP de la Halle aux Farines, vous devez d'abord passer certains paramètres à gradle via une variable d'environnement. Cela peut être fait en exécutant 

```bash
$ source SCRIPT/envsetup
```

avant de lancer toute commande gradle (notamment `build` et `run`.

Pour être tranquille, vous pouvez insérer cette commande dans votre fichier `~/.bashrc`, cela vous évitera de devoir la taper à la main à chaque nouvelle session. Pensez à adapter la commande en donnant le chemin absolu vers `envsetup`.

Pour information, les paramètres passés à gradle indiquent :

- le fichier de certificats à utiliser pour télécharger les dépendances via HTTPS
- les paramètres du proxy de la Halle aux Farines
- le chemin vers Java 17

#### Sur une machine de TP de la Halle aux Farines en utilisant Eclipse

Eclipse installé sur les machines de TP contient une distribution de Java 17, et semble savoir passer la bonne configuration à Gradle. Pour travailler avec eclipse, il suffit donc de lancer Eclipse (commande `eclipse`), puis d'importer le projet :

1. File > Import... > Gradle > Existing Gradle Project, Next >
2. choisir le chemin de pong et valider avec Finish

Dans l'onglet "Gradle Tasks", vous trouverez notamment les tâches permettant de compiler et d'exécuter le projet.

#### Sur une machine personnelle avec Java 11 à 16

Si vous souhaitez/devez travailler avec une version ancienne de Java, il n'est pas très difficile de modifier la configuration : changez juste les numéros de version dans `build.gradle`.

Ensuite, vous pouvez travailler comme avec Java 17 (le projet devrait pouvoir tourner tel quel).

Important : faites un commit de `build.gradle` et poussez-le sur votre fork pour que toutes votre équipe travaille avec la même version de Java.

#### Sur une machine personnelle avec Java 8 à 10 (DISCLAIMER : compliqué !)

Je n'ai pas testé, mais Pong devrait pouvoir tourner (peut-être avec quelques modifications mineures). Malheureusement, ça peut être un peu compliqué.

Dans les grandes lignes :

- Commencez par désactiver le plugin JavaFX dans gradle (toujours dans `build.gradle`), car celui-ci ne fonctionne qu'à partir de Java 11. 
- Désormais, gradle ne s'occupe plus de télécharger et installer JavaFX. Il faut donc s'assurer de l'avoir installé d'une autre façon. Si vous avez une distribution de Java sans JavaFX (à noter que Oracle Java 8 contient JavaFX), il faut le télécharger et l'installer séparément, en prenant soin de prendre le même numéro de version.
- Si JavaFX a été installé séparément, il faut le faire savoir à gradle pour qu'il ajoute son répertoire au classpath.
- Si vous utilisez Java 9 ou 10, il y a de la configuration de modules JPMS à faire à la main. Je ne vais expliquer, ni ce que c'est, ni en quoi ça consiste ici. Il est possible de trouver de l'aide dans les forums.

Bref, il est grandement conseillé d'utiliser une version plus récente de Java. Néanmoins, si vous n'avez pas le choix et que vous êtes en difficulté, demandez de l'aide à vos enseignants.

Important : là aussi, il faut ensuite que toute votre équipe travaille avec la même version de Java. Faites un commit de `build.gradle` et poussez-le sur votre fork pour que toutes votre équipe travaille avec la même version de Java.

## Jouer

La raquette de gauche est contrôlée par les touches CONTROL et ALT, alors que celle de droite est contrôlée par les flèches HAUT et BAS.