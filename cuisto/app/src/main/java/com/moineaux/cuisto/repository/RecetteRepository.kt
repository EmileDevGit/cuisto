package com.moineaux.cuisto.repository

import androidx.lifecycle.LiveData
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.Categorie
import com.moineaux.cuisto.data.Recette

class RecetteRepository(val db: AppDatabase) {

    suspend fun insererDonneesMock() {
        if (db.recetteDao().getRecettesNonMock().isEmpty()) {


            val pizzaId = getOrCreateCategorie("Pizza", "icone_pizza_cat")
            val saladeId = getOrCreateCategorie("Salade", "icone_salade_cat")
            val burgerId = getOrCreateCategorie("Burger", "icone_burger_cat")
            val patesId = getOrCreateCategorie("Pâtes", "icone_pates_cat")
            val tacosId = getOrCreateCategorie("Tacos", "icone_tacos_cat")

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Margherita",
                    image = "pizza_margherita",
                    ingredients = "Pâte à pizza, sauce tomate, mozzarella, basilic frais",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four", "Étaler la pâte", "Ajouter les ingrédients", "Cuire 15 min")
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Regina",
                    image = "pizza_regina",
                    ingredients = "Pâte à pizza, sauce tomate, jambon, champignons, mozzarella",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce tomate, le jambon, les champignons et la mozzarella.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza aux 4 fromages",
                    image = "pizza_4_fromages",
                    ingredients = "Pâte à pizza, sauce tomate, mozzarella, chèvre, gorgonzola, parmesan",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce tomate et les 4 fromages.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Végétarienne",
                    image = "pizza_vegetarienne",
                    ingredients = "Pâte à pizza, sauce tomate, poivrons, oignons, olives, champignons, mozzarella",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce tomate et les légumes.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Pepperoni",
                    image = "pizza_pepperoni",
                    ingredients = "Pâte à pizza, sauce tomate, pepperoni, mozzarella",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce tomate, le pepperoni et la mozzarella.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Calzone",
                    image = "calzone",
                    ingredients = "Pâte à pizza, sauce tomate, jambon, champignons, mozzarella, œuf",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 200°C.", "Étaler la pâte en cercle.", "Garnir la moitié de la pâte.", "Replier la pâte et souder les bords.", "Cuire 15-20 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Hawaïenne",
                    image = "pizza_hawaienne",
                    ingredients = "Pâte à pizza, sauce tomate, jambon, ananas, mozzarella",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce tomate, le jambon, l'ananas et la mozzarella.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza au Thon",
                    image = "pizza_thon",
                    ingredients = "Pâte à pizza, sauce tomate, thon, oignons, olives, mozzarella",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce tomate, le thon, les oignons, les olives et la mozzarella.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Chèvre-Miel",
                    image = "pizza_chevre_miel",
                    ingredients = "Pâte à pizza, crème fraîche, chèvre, miel, noix",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la crème fraîche, le chèvre, le miel et les noix.", "Cuire 10-15 minutes.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pizza Bolognaise",
                    image = "pizza_bolognaise",
                    ingredients = "Pâte à pizza, sauce bolognaise, oignons, mozzarella",
                    categorieId = pizzaId,
                    isMock = 0
                ),
                listOf("Préchauffer le four à 220°C.", "Étaler la pâte.", "Étaler la sauce bolognaise, les oignons et la mozzarella.", "Cuire 10-15 minutes.")
            )

            // ***************** SALADE ***************

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade César",
                    image = "salade_cesar",
                    ingredients = "Laitue romaine, croûtons, parmesan, poulet grillé, sauce César",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Laver et couper la laitue.", "Faire griller le poulet et le couper en dés.", "Préparer les croûtons.", "Mélanger tous les ingrédients avec la sauce César.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade Niçoise",
                    image = "salade_nicoise",
                    ingredients = "Laitue, thon, haricots verts, pommes de terre, tomates, œufs durs, olives noires, anchois, vinaigrette",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Cuire les haricots verts, les pommes de terre et les œufs.", "Couper les légumes et les œufs.", "Disposer tous les ingrédients.", "Arroser de vinaigrette.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade de Pâtes",
                    image = "salade_pates_froides",
                    ingredients = "Pâtes, tomates cerises, concombre, feta, olives, basilic, vinaigrette",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Cuire les pâtes et les laisser refroidir.", "Couper les légumes et la feta.", "Mélanger tous les ingrédients avec la vinaigrette.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade Grecque",
                    image = "salade_grecque",
                    ingredients = "Concombre, tomates, oignon rouge, feta, olives Kalamata, origan, huile d'olive",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Couper les légumes et la feta.", "Mélanger tous les ingrédients.", "Arroser d'huile d'olive et saupoudrer d'origan.")
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade Caprese",
                    image = "salade_caprese",
                    ingredients = "Tomates, mozzarella de bufflonne, basilic frais, huile d'olive, vinaigre balsamique",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Couper les tomates et la mozzarella en tranches.", "Disposer en alternance avec les feuilles de basilic.", "Arroser d'huile d'olive et de vinaigre balsamique.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade de Lentilles",
                    image = "salade_lentilles",
                    ingredients = "Lentilles, carottes, céleri, oignon, persil, vinaigrette à la moutarde",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Cuire les lentilles.", "Couper les légumes en petits dés.", "Mélanger les lentilles et les légumes avec la vinaigrette.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade de Fruits de Mer",
                    image = "salade_fruits_mer",
                    ingredients = "Mélange de fruits de mer cuits, céleri, oignon rouge, persil, citron, huile d'olive",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Préparer les fruits de mer (décongeler si nécessaire).", "Hacher le céleri, l'oignon et le persil.", "Mélanger tous les ingrédients avec le jus de citron et l'huile d'olive.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade de Haricots Verts et Feta",
                    image = "salade_haricots_feta",
                    ingredients = "Haricots verts, feta, tomates séchées, pignons de pin, vinaigrette balsamique",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Cuire les haricots verts à la vapeur.", "Couper la feta et les tomates séchées.", "Faire griller les pignons de pin.", "Mélanger tous les ingrédients avec la vinaigrette.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Salade de Poulet Avocat",
                    image = "salade_poulet_avocat",
                    ingredients = "Poulet cuit, avocat, maïs, oignon rouge, coriandre, jus de lime, huile d'olive",
                    categorieId = saladeId,
                    isMock = 0
                ),
                listOf("Couper le poulet et l'avocat en dés.", "Hacher l'oignon rouge et la coriandre.", "Mélanger tous les ingrédients avec le jus de lime et l'huile d'olive.")
            )

            // ****************** BURGER ***************

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Cheeseburger Classique",
                    image = "cheeseburger_classique",
                    ingredients = "Pain à burger, steak haché, fromage cheddar, laitue, tomate, oignon, cornichon, ketchup, moutarde",
                    categorieId = burgerId,
                    isMock = 0
                ),
                listOf("Façonner le steak et le cuire à la poêle.", "Faire griller le pain.", "Assembler le burger avec tous les ingrédients.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Burger Bacon-Fromage",
                    image = "burger_bacon_fromage",
                    ingredients = "Pain à burger, steak haché, fromage, bacon grillé, oignon caramélisé, sauce BBQ",
                    categorieId = burgerId,
                    isMock = 0
                ),
                listOf("Façonner le steak et le cuire avec le fromage.", "Faire griller le bacon et caraméliser l'oignon.", "Faire griller le pain.", "Assembler le burger.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Burger Végétarien aux Champignons",
                    image = "burger_vegetarien_champignons",
                    ingredients = "Pain à burger, grand champignon portobello, fromage de chèvre, roquette, poivron grillé, sauce pesto",
                    categorieId = burgerId,
                    isMock = 0
                ),
                listOf("Faire griller le champignon et le poivron.", "Faire griller le pain.", "Assembler le burger avec le fromage de chèvre et la roquette.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Burger Poulet Croustillant",
                    image = "burger_poulet_croustillant",
                    ingredients = "Pain à burger, filet de poulet pané, salade de chou, sauce tartare",
                    categorieId = burgerId,
                    isMock = 0
                ),
                listOf("Faire frire ou cuire le filet de poulet pané.", "Préparer la salade de chou.", "Faire griller le pain.", "Assembler le burger.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Burger Mexicain",
                    image = "burger_mexicain",
                    ingredients = "Pain à burger, steak haché, guacamole, salsa, piment jalapeño, fromage Monterey Jack",
                    categorieId = burgerId,
                    isMock = 0
                ),
                listOf("Façonner le steak et le cuire avec le fromage.", "Préparer le guacamole et la salsa.", "Faire griller le pain.", "Assembler le burger.")
            )

            // ***************** PATES ****************

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Spaghetti Bolognaise",
                    image = "spaghetti_bolognaise",
                    ingredients = "Spaghetti, viande hachée, sauce tomate, oignon, ail, carottes, céleri",
                    categorieId = patesId,
                    isMock = 0
                ),
                listOf("Faire revenir l'oignon, l'ail, les carottes et le céleri.", "Ajouter la viande hachée et cuire.", "Incorporer la sauce tomate et laisser mijoter.", "Cuire les spaghetti al dente.", "Servir la sauce sur les spaghetti.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Carbonara Traditionnelle",
                    image = "carbonara_traditionnelle",
                    ingredients = "Spaghetti, guanciale ou pancetta, œufs, pecorino romano, poivre noir",
                    categorieId = patesId,
                    isMock = 0
                ),
                listOf("Couper le guanciale/pancetta en dés et le faire revenir.", "Battre les œufs avec le pecorino et le poivre.", "Cuire les spaghetti al dente.", "Mélanger les pâtes chaudes avec la sauce aux œufs et le guanciale.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Pesto Genovese",
                    image = "pesto_genovese",
                    ingredients = "Pâtes, basilic frais, pignons de pin, parmesan, pecorino, ail, huile d'olive",
                    categorieId = patesId,
                    isMock = 0
                ),
                listOf("Préparer le pesto en mixant tous les ingrédients.", "Cuire les pâtes al dente.", "Mélanger les pâtes avec le pesto.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Lasagnes à la Bolognaise",
                    image = "lasagnes_bolognaise",
                    ingredients = "Feuilles de lasagnes, sauce bolognaise, béchamel, parmesan",
                    categorieId = patesId,
                    isMock = 0
                ),
                listOf("Préparer la sauce bolognaise et la béchamel.", "Alterner couches de lasagnes, bolognaise et béchamel dans un plat.", "Saupoudrer de parmesan et faire gratiner au four.")
            )
            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Mac and Cheese",
                    image = "mac_and_cheese",
                    ingredients = "Macaroni, beurre, farine, lait, fromage cheddar, sel, poivre",
                    categorieId = patesId,
                    isMock = 0
                ),
                listOf("Cuire les macaroni al dente.", "Préparer une sauce béchamel avec le beurre, la farine et le lait.", "Incorporer le fromage à la sauce.", "Mélanger les macaroni avec la sauce au fromage.")
            )

            //****************** TACOS ****************

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Tacos au boeuf épicé",
                    image = "tacos_boeuf_epice",
                    ingredients = "Boeuf haché, épices, salade",
                    categorieId = tacosId,
                    isMock = 0
                ),
                listOf("Cuire le boeuf", "Ajouter épices", "Assembler")
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Tacos végétarien",
                    image = "tacos_vege",
                    ingredients = "Haricots rouges, maïs, avocat",
                    categorieId = tacosId,
                    isMock = 0
                ),
                listOf("Préparer les ingrédients", "Assembler", "Servir")
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Tacos au poisson",
                    image = "tacos_poisson",
                    ingredients = "Poisson pané, salade, sauce",
                    categorieId = tacosId,
                    isMock = 0
                ),
                listOf("Cuire le poisson", "Préparer garniture", "Assembler")
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(
                    nomRecette = "Tacos cheddar bacon",
                    image = "tacos_cheddar",
                    ingredients = "Fromage cheddar, bacon grillé",
                    categorieId = tacosId,
                    isMock = 0
                ),
                listOf("Cuire bacon", "Ajouter fromage", "Assembler")
            )

        }
    }
    suspend fun recupererToutesLesRecettes(): List<Recette> {
        return db.recetteDao().toutRecuperer()
    }
    suspend fun recupererRecettesMock(): List<Recette> {
        return db.recetteDao().recupererRecettesMock()
    }
    fun getRecettesParCategorie(idCategorie: Long): LiveData<List<Recette>> {
        return db.recetteDao().getRecettesParCategorie(idCategorie)
    }
    private suspend fun getOrCreateCategorie(nom: String, image: String): Long {
        val existante = db.categorieDao().getCategorieParNom(nom)
        return existante?.id ?: db.categorieDao().insert(Categorie(nom = nom, image = image))
    }

}