# Mini-projet/DM de session 2 : jeu de raquettes

## Présentation

Je vous fournis un jeu de raquettes déjà fonctionnel, programmé en Java 11 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu [Pong](https://fr.wikipedia.org/wiki/Pong), un classique des salles d'arcades dans les années 1970.

Le principe est simple : un terrain (le "*court*") deux raquettes et une balle. Le jeu se joue à deux, chaque joueur pouvant déplacer sa raquette sur un axe haut/bas et ayant pour but de ne pas laisser passer la balle derrière sa raquette (se qui cause sa défaite immédiate). La balle se déplace à vitesse constante et rebondit sur les parois (limites haute et basse de la fenêtre) et sur les raquettes.

Le code proposé fonctionne mais a quelques défauts : notamment il contient des des répétitions et son organisation n'est pas toujours très orientée objet. Il peut donc être amélioré : cela sera le premier objectif de ce DM. L'autre objectif (servant de motivation au premier), est d'ajouter quelques fonctionnalités pour rendre le jeu plus amusant.

## Exécution, compilation

Prérequis : vous avez besoin de Java 11.

1. Télécharger le projet.
    - (préféré) soit via git: `git clone https://gaufre.informatique.univ-paris-diderot.fr/adegorre/cpoo5-session2-2020.git`
    - soit en téléchargeant l'archive zip au lien qui sera donné ultérieurement et en la décompressant dans le répertoire de votre choix. 

2. Compiler avec gradle. La commande est, dans le répertoire du projet, exécutez `./gradlew build`. Au besoin, cela téléchargera la bonne version de gradle.

3. Exécuter avec `./gradlew run` (cela inclut `./gradlew build` si le projet n'est pas déjà compilé).

## Le rendu

Le rendu se fera sur moodle le 28 juin à minuit au plus tard (le lien sera ajouté ici quand il sera créé). Vous pouvez
 
 - soit rendre votre projet dans une archive zip, 
 - soit soumettre un fichier texte donnant l'adresse d'un dépôt git que vous aurez créé en clonant le mien.
 
 Le second mode de rendu est préféré mais non obligatoire.
 
 Dans tous les cas, mettez à jour le fichier readme.md en y ajoutant des explications quant aux modifications que vous aurez apportées.

## Fonctionnalités à ajouter

- raquette contrôlée par l'ordinateur (par exemple en suivant la balle la plus proche)
- plus de raquettes, contrôlées par autant de joueurs différents, ou bien, pourquoi pas, plusieurs raquettes pour un même joueur (faire attention aux conditions de défaite) 
- contrôle plus perfectionné de la raquette (permettre aussi d'avancer et de reculer, déplacements avec plusieurs vitesses possibles, etc.)
- obstacles immobiles et mobiles
- rebonds de travers (pour obstacles en biais)
- effets de la balle (comme au tennis)
- système de décompte de vies, plutôt que défaite immédiate
- bonus pour changer la taille de la raquette, sa maniabilité, ajouter des balles, etc.
- de beaux graphismes (beaucoup moins prioritaire que le reste !)

À vous de proposer d'autres idées !

Attention, tout n'est pas à faire. Ce sont juste des exemples qui pourraient tirer profit du code que vous aurez réorganisé.

## Améliorations attendues du code

Voici quelques pistes d'amélioration proposées (il y a probablement d'autres améliorations possibles).

### Objets animables

Le modèle décrit plusieurs objets physiques dont les propriétés sont mises à jour à chaque évènement déclenché par l'`AnimationTimer` de la classe `GameView`.

Or le code proposé ne représente pas cette notion d'objet animable par un type dédié (*i.e.* une interface dédiée). La méthode `update` de la classe `Court` se contente en effet de mettre à jour un certain nombre de ses attributs, déclarés "en vrac" dans cette classe, ce qui occasionne beaucoup de répétitions (et rend quasiment impossible d'ajouter de nouveaux objets à animer).

**À faire :**

- déterminer quelles entités du modèle du jeu sont des objets animables, et écrire des classes correspondantes (regroupant les attributs en rapport avec un même objet animable) ;
- écrire l'interface `Animable` que pourraient implémenter toutes ces classes ; modifier ces dernières pour qu'elles implémentent effectivement cette interface ;
- remplacer les attributs de `Court` qui auront été repris par les classes implémentant `Animable` par des attributs référençant des instances de ces classes ;
- réécrire la méthode `update` de sorte à ce qu'elle se contente d'appeler une même méthode (déclarée dans `Animable`) sur tous les objets animables connus du modèle.

Ces remaniements du modèle entraîneront forcément des adaptations dans la classe `GameView`. Profitez-en pour factoriser le code de création et de mise à jour des objets graphiques (via création de méthodes auxiliaires, voire de classes dédiées à chaque type d'objet graphique).

### Création des joueurs

Le code servant à déclarer les joueurs (associer des évènements clavier à des variables d'état dans des instances de `RacketController`) est lui-même très répétitif.

Pour éviter cela, on voudrait écrire une méthode `public RacketController makePlayer(KeyCode keyDown, KeyCode keyUp)` qui crée un joueur en ajoutant à la scène de jeu un nouveau gestionnaire pour `KeyPressed` et pour `KeyReleased`. Malheureusement, cela n'est pas possible car cela écraserait le gestionnaire précédemment associé (un seul joueur fonctionnerait).

Suggestion : utiliser, comme dans le code fourni, deux gestionnaires d'évènements uniques pour `KeyPressed` et `KeyReleased`, mais ceux-ci devront être capables de vérifier les touches de tous les joueurs créés. La méthode `makePlayer` devra donc juste instancier un joueur et *l'associer* à ses codes de touches dans des structures de données adéquates (qui seront utilisées par le gestionnaire d'évènements).

Pour organiser tout ça, créez une classe `PlayerFactory` dont le constructeur prend en paramètre la scène de jeu et lui associe les gestionnaires pour les évènements `KeyPressed` et `KeyReleased` (ce qui était fait, auparavant, dans la méthode `start` de la classe `App`). Déplacez-y aussi les structures de données stockant les touches à surveiller, la classe locale `Player`, ainsi que la méthode `makePlayer`.

### Mouvements complexes

Dans le code fourni, une raquette a 3 états : déplacement vers le haut, immobile, déplacement vers le bas.

Ajoutez des déplacements avant et arrière en complétant l'`enum`.

Que faire si en plus de donner le sens du déplacement vous voulez spécifier une vitesse de déplacement (en supposant par exemple que certaines touches du clavier permettraient de se déplacer plus vite, ou bien que la vitesse augmente avec le temps passé à appuyer sur une touche) ?

Indice : une `enum` ne suffit plus, puisqu'il y a un paramètre.

### Création du terrain

Le *court* a des paramètres multiples (largeur, hauteur, position et dimensions des obstacles).
Mettez en œuvre le patron *builder* pour permettre d'instancier un terrain où tout pourrait être paramétré par le client (sans qu'il ait à appeler un constructeur dont les arguments seraient interminables).

Faites de même pour la vue associée (classe `GameView'), classe qui possède elle aussi son propre jeu de paramètres supplémentaires.

Ne vous limitez pas aux paramètres des constructeurs actuels ; tout réglage qui aurait du sens peut être considéré pour les `builders`.

