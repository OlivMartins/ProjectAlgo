# Projet d'algorithmie avancée d'IR2

> auteurs: Paul Ollivier & Olivier Ferreira  
> date :  08/12/2013

## Introduction

Le but de ce projet est de générer une image représentant un graphe, chargé depuis un fichier XML ayant une structure particulière, décrivant un plan, et des obstacles. Ce fichier définit également un point de départ, et un point d'arrivée, dans l'optique de trouver le plus court cheminentre ces points, en tenant compte des obstacles.

## Comment l'utiliser
### Compilation

Ce programme est supposé être compilé avec java version 1.8

Afin de pouvoir lancer le programme, veuillez compiler tous les fichiers source `.java` présents dans le répertoire `src/` vers leurs fichiers `.class` correspondants dans le répertoire `bin/`. Pensez à bien respecter l'arborescence des packages.

Afin de compiler, ce projet aura besoin d'une mise à jour du CLASSPATH afin d'inclure le jar de [JSAP][JSAP_HOMEPAGE], situé dans `lib/`. Un classpath d'exemple pourrait être, si nous sommes situés à la racine du projet :`CLASSPATH="$(pwd)/lib/JSAP-2.1.jar:$(pwd)"`

### Lancer le programme

Pour lancer le programme, se placer dans le répertoire `bin/`, ou sont situés les fichiers `.class` compilés, et lancer Java, en n'oubliant pas de modifier le classpath, toujours pour inclure le jar de [JSAP][JSAP_HOMEPAGE]. Dans ce répertoire bin est un script `launch.sh`, permettant de lancer le programme, incluant la mise à jour du classpath.

## Organisation du code

### Types de données

Nous utilisons plusieurs types de données afin de stocker et manipuler les informations:

Tous les sommets sont représentés par des entiers.

Graph
: Interface définissant les méthodes disponibles sur un graphe. L'implémentation retenue a été ListGraph, qui implémente le graph en gardant dans un tableau de listes les informations sur les liens. 

    public interface Graph {
        public boolean hasPath(int node1, int node2);
        public void addPath(int node1, int node2);
        public int nodeCount();
        public List<Integer> getNeighbours(int n);
        public List<Integer> getAllNodes();
    }

Maze
:  Un Maze est un graphe auquel on associe les informations de départ et d'arrivée, sa taille, etc... C'est le Maze qui va servir le plus pour la génération de l'image finale.

    public class Maze {
        private final Point start;
        private final Point end;
        private final Graph g;
        private final int width, height;
        private int[] shortestPath; 

        /* methods... */
    }

### Méthodes importantes

La classe `MazeLoader` fournit une méthode statique `loadMazeFromFile(Path p)` qui permet le chargement d'un fichier XML représentant un labyrinthe.  La classe `MazeExport`, quant à elle, permet l'export d'un Maze vers un autre format, en l'ocurence, une image.

Le seul algorithme de parcours de graphes est le `DijkstraWalker`, qui, comme son nom l'indique, parcours le graphe suivant l'algorithme de Dijkstra.

## Performances

### "Points chauds"

La partie ou la perte de temps est la plus importante est le chargement du graphe décrit dans le fichier XML dans la mémoire. En effet, pour chaque point du labyrinthe décrit, nous vérifions tous ses voisins, nous avons donc 9*N*, ou *N* est le nombre de points, donc `Hauteur * Largeur`. De plus, nous vérifions que ce point ne se trouve pas être un obstacle. La complexité théorique est donc de `9*Hauteur*Largeur*NbrObstacles`.

Nous avons réussi à réduire d'un facteur d'environ 1/20 le chargement des graphs en évitant de recalculer l'intégralité des obstacles pour chaque point.

Pour cela, nous établissons une liste identité obstacles vide, et nous préchargeons le graphe avec cette liste, en parcourant les obstacles. De cette manière, au lieu de devoir comparer chaque point à tous les obstacles et vérifier son appartenance à un d'entre eux, nous comparons juste avec une référence, et permet donc de passer notre complexité globale en  `9*Hauteur*Largeur + NbrObstacles`. 

Un autre des points chauds était au niveau de la génération de l'image, puisque nous parcourions plusieurs fois N. Nous avons par conséquent définit une taille maximum de l'image 

## Conclusion

Le projet est loin d'être parfait, et des optimisations plus poussées sont possibles. De plus, il ne peut charger les graphes particulièrement grands pour cause de manque de mémoire. En effet, pour un graphe de 40000 x 40000, en supposant qu'un entier seul est stocké sur 4 octets, cela nous donne  environ 6,4 Go de mémoire, uniquement pour la réservation du tableau initial. Nous estimons cependant que la consommation superflue de la mémoire dans l'état actuel est proche du minimum.

La non-séparation du code en modules logiques pourrait réduire l'usage mémoire, par exemple en stockant les informations des obstacles et autres directement dans l'image, ce qui évite le sockage de la liste d'obstacles, par exemple. Il n'est cependant pas forcément souhaitable en termes de maintenabilité logicielle de fusionner l'intégralité des fonctions logiques.

[JSAP_HOMEPAGE]: http://www.martiansoftware.com/jsap/ "JSAP Homepage"