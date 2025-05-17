# Fonctionnalitées
Le système doit ...
- Afficher toutes les recettes
- Afficher les résultat de recherche de recettes par filtre (Nom / Catégories / Ingrédients ... )
- Afficher une recette en détails
- Permettre Ajouter / Modifier / Supprimer une recette
- Permettre Ajouter / Modifier / Supprimer une Catégorie
- Offrir la fonction recherche/Créer sur un maximum de pages
- Stocker les données de l'utilisateur dans une base de données locale SQL



# Liste des requis
## Landing page

- Image de fond
- Bouton Découvrir

## Page d'Accueil

- Liste des recettes
    - Carte vide pour créer ( + )
    - Carte avec image + titre
    - Boutons modifier / supprimer
    - Scroll horizontal 1x1 / 2x1 / 2x2 ( ? )

- Recherche
    - Barre de recherche + Bouton explicite (en haut)
    - Filtre les recettes par leur noms
    - _Dropdown catégorie_

- Bouton créer

## Affichage de la recette
- Photo
- Titre
- Description
- Liste d'ingrédients
- Étapes de préparation
- Bouton Editer
- Bouton Supprimer
- Header réduit pour la recherche
- _Statistiques (temps, portions...)_

## Formulaire nouvelle recette

- Champs
    - Titre de la recette
    - Liste d'ingrédients
    - Catégorie => existante ou nouvelle
    - Ajouter une image
    - Étape(s) et description
    - _Statistiques (portions, prep time...)_
- Bouton Ajouter
- Bouton Retour / Annuler

## SQL

- Table Catégorie
    - id [int]
    - nom [str]

- Table Recette
    - id [int]
    - titre [str]
    - imageUrl [str]
    - description [str]
    - tempsPreparation [str/int]
    - portions [int]
    - id_categorie [int]
    
- Table Ingrédient
    - id [int]
    - nom [str]
    - id_recette [int]

- Table Étape
    - id [int]
    - numero_etape [int]
    - Description [str]
    - id_recette [int] 


# À faire attention
- Supprimer la dernière recette d'une catégorie
- Lors de la création de recette, comment ajouter/supprimer/déplacer une étape?