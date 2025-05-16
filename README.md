# TP2 - [Cuisto][git-link]

## Les Moineaux
Le mode de communication de l'équipe est un canal Teams
Cette équipe est composée de trois développeurs :
- Kelly Henri Clovis
- Kristopher Solhtalab
- Émile Girard

Responsable des communications: [Émile Girard][email]

## Présentation de l'application
**Cuisto**, à travers son interface intuitive, vous permet de consulter et gérer vos recettes. Son système de stockage local permet de d'enregistrer une grande quantité d'information sans encombrer votre espace de création. Vos recettes sont catégorisées et accessibles au bout de vos doigts à n'importe quel moment.

 C'est l'outil parfait pour conserver vos tradtitions culinaires de manière sûre et pratique!

## Fonctionnalitées
Une analyse détaillée des fonctionnalitées et de l'architecture de l'application
est disponible dans le fichier `analyse.md`
| Responsable | Description |
| :--- | :--- |
| Émile       | Ajouter / Modifier/Supprimer une recette|
| Émile       | Stockage dans une base de donnée locale SQL|
| Kelly       | Afficher toutes les recettes |
| Kelly       | Recherche par filtre (nom, ingrédients, catégorie, ...)|
| Kristopher  | Ajouter / Modifier / Supprimer une catégorie|
| Kristopher  | Afficher une recette en détail|


[//]: # (Links)
[email]: (mailto:1649962@crosemont.qc.ca)
[git-link]:(https://git.dti.crosemont.quebec/egirard/tp2-cahier-de-recettes)

## Diagramme entité-relation

```mermaid
    erDiagram
    "CATÉGORIE" ||--|{ RECETTE : appartenir
    "CATÉGORIE"{
        int id
        string nom
    }
    RECETTE ||--|{ "INGRÉDIENT" : avoir
    RECETTE{
        int id
        string titre
        string imageURL
        string description
        string tempsPreparation
        int portions
        int id_categorie
    }
    "INGRÉDIENT"{
        int id
        string nom
        int id_recette
    }
    "ÉTAPE" }|--|| RECETTE : a
    "ÉTAPE"{
        int id
        int numero_etape
        string description
        int id_recette
    }
```