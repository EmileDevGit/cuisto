package com.moineaux.cuisto.repository

import androidx.lifecycle.LiveData
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.Categorie
import com.moineaux.cuisto.data.Etape
import com.moineaux.cuisto.data.Recette

class RecetteRepository(val db: AppDatabase) {

    suspend fun insererDonneesMock() {
        if (db.recetteDao().getRecettesNonMock().isEmpty()) {


            val pizzaId = getOrCreateCategorie("Pizza", "icone_pizza_cat")
            val saladeId = getOrCreateCategorie("Salade", "icone_salade_cat")
            val burgerId = getOrCreateCategorie("Burger", "icone_burger_cat")
            val patesId = getOrCreateCategorie("Pâtes", "icone_pates_cat")
            val tacosId = getOrCreateCategorie("Tacos", "icone_tacos_cat")
            //


            // DONNES MOCK

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(nomRecette = "Pizza chèvre miel noix", image = "pizzachevremiel", ingredients = "Chèvre, miel, noix", categorieId = pizzaId,isMock = 1),
                listOf(
                    Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four"),
                    Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte"),
                    Etape(recetteId = 1, ordre = 3, description ="Ajouter les ingrédients"),
                    Etape(recetteId = 1, ordre = 4, description = "Cuire 15 min")
                )
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(nomRecette = "Salade de quinoa", image = "saladequinoa", ingredients = "Quinoa, légumes", categorieId = saladeId,isMock = 1),
                listOf(
                    Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four"),
                    Etape(recetteId = 1, ordre = 2, description = "Couper les légumes"),
                    Etape(recetteId = 1, ordre = 3, description = "Mélanger"),
                    Etape(recetteId = 1, ordre = 4, description = "Assaisonner")
                )
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(nomRecette = "Burger steak fromage", image = "burgersteakfromage", ingredients = "Steak, fromage, pain", categorieId = burgerId,isMock = 1),
                listOf(
                    Etape(recetteId = 1, ordre = 1, description = "Cuire le steak"),
                    Etape(recetteId = 1, ordre = 2, description = "Griller le pain"),
                    Etape(recetteId = 1, ordre = 3, description = "Monter le burger"),
                    Etape(recetteId = 1, ordre = 4, description = "Servir chaud")
                )
            )

            db.recetteDao().insertRecetteAvecEtapes(
                Recette(nomRecette = "Pâtes aux champignons", image = "pateschampignons", ingredients = "Pâtes, champignons, crème", categorieId = patesId,isMock = 1),
                listOf(
                    Etape(recetteId = 1, ordre = 1, description = "Faire cuire les pâtes"),
                    Etape(recetteId = 1, ordre = 2, description ="Sauter les champignons"),
                    Etape(recetteId = 1, ordre = 3, description = "Ajouter la crème"),
                    Etape(recetteId = 1, ordre = 4, description = "Mélanger le tout")
                )
            )

            // FIN DONNES MOCK

        }
    }

    suspend fun insererRecetteUtilisateur() {

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
        listOf(
            Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
            Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
            Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate et la mozzarella."),
            Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
        )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza Regina",
                image = "pizza_regina",
                ingredients = "Pâte à pizza, sauce tomate, jambon, champignons, mozzarella",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, le jambon, les champignons et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza aux 4 fromages",
                image = "pizza_4_fromages",
                ingredients = "Pâte à pizza, sauce tomate, mozzarella, chèvre, gorgonzola, parmesan",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, les fromages."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza Végétarienne",
                image = "pizza_vegetarienne",
                ingredients = "Pâte à pizza, sauce tomate, poivrons, oignons, olives, champignons, mozzarella",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, les légumes et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza Pepperoni",
                image = "pizza_pepperoni",
                ingredients = "Pâte à pizza, sauce tomate, pepperoni, mozzarella",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, le pepperoni et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Calzone",
                image = "calzone",
                ingredients = "Pâte à pizza, sauce tomate, jambon, champignons, mozzarella, œuf",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, le jambon, les champignons et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Ajouter l'œuf et cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza Hawaïenne",
                image = "pizza_hawaienne",
                ingredients = "Pâte à pizza, sauce tomate, jambon, ananas, mozzarella",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, le jambon, les ananas et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza au Thon",
                image = "pizza_thon",
                ingredients = "Pâte à pizza, sauce tomate, thon, oignons, olives, mozzarella",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce tomate, le thon, les oignons, les olives et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza Chèvre-Miel",
                image = "pizza_chevre_miel",
                ingredients = "Pâte à pizza, crème fraîche, chèvre, miel, noix",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la crème fraîche, le chèvre, le miel et les noix."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pizza Bolognaise",
                image = "pizza_bolognaise",
                ingredients = "Pâte à pizza, sauce bolognaise, oignons, mozzarella",
                categorieId = pizzaId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four à 200°C."),
                Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte."),
                Etape(recetteId = 1, ordre = 3, description = "Étaler la sauce bolognaise, les oignons et la mozzarella."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire 10-15 minutes.")
            )
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
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Laver et couper la laitue."),
                Etape(recetteId = 1, ordre = 2, description =   "Faire griller le poulet et le couper en dés."),
                Etape(recetteId = 1, ordre = 3, description = "Préparer les croûtons."),
                Etape(recetteId = 1, ordre = 4, description = "Mélanger tous les ingrédients avec la sauce César.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade Niçoise",
                image = "salade_nicoise",
                ingredients = "Laitue, thon, haricots verts, pommes de terre, tomates, œufs durs, olives noires, anchois, vinaigrette",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire les haricots verts"),
                Etape(recetteId = 1, ordre = 2, description = "Couper les légumes en dés."),
                Etape(recetteId = 1, ordre = 3, description = "Mélanger tous les ingrédients."),
                Etape(recetteId = 1, ordre = 4, description ="les pommes de terre et les œufs."),
                Etape(recetteId = 1, ordre = 5, description = "Arroser de vinaigrette."),
                Etape(recetteId = 1, ordre = 6, description = "Couper les légumes et les œufs."),
                Etape(recetteId = 1, ordre = 7, description = "Disposer tous les ingrédients."),
                Etape(recetteId = 1, ordre = 8, description = "Arroser de vinaigrette.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade de Pâtes",
                image = "salade_pates_froides",
                ingredients = "Pâtes, tomates cerises, concombre, feta, olives, basilic, vinaigrette",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire les pâtes et les laisser refroidir."),
                Etape(recetteId = 1, ordre = 2, description = "Couper les légumes et la feta."),
                Etape(recetteId = 1, ordre = 3, description = "Mélanger tous les ingrédients avec la vinaigrette.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade Grecque",
                image = "salade_grecque",
                ingredients = "Concombre, tomates, oignon rouge, feta, olives Kalamata, origan, huile d'olive",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Couper les légumes et la feta."),
                Etape(recetteId = 1, ordre = 2, description = "Mélanger tous les ingrédients."),
                Etape(recetteId = 1, ordre = 3, description = "Arroser d'huile d'olive et saupoudrer d'origan.")
            )
        )

        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade Caprese",
                image = "salade_caprese",
                ingredients = "Tomates, mozzarella de bufflonne, basilic frais, huile d'olive, vinaigre balsamique",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Couper les tomates et la mozzarella en tranches."),
                Etape(recetteId = 1, ordre = 2, description = "Mélanger tous les ingrédients."),
                Etape(recetteId = 1, ordre = 3, description = "Disposer en alternance avec les feuilles de basilic."),
                Etape(recetteId = 1, ordre = 4, description = "Arroser d'huile d'olive et de vinaigre balsamique.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade de Lentilles",
                image = "salade_lentilles",
                ingredients = "Lentilles, carottes, céleri, oignon, persil, vinaigrette à la moutarde",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire les lentilles."),
                Etape(recetteId = 1, ordre = 2, description = "Couper les légumes en petits dés."),
                Etape(recetteId = 1, ordre = 3, description = "Mélanger les lentilles et les légumes avec la vinaigrette.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade de Fruits de Mer",
                image = "salade_fruits_mer",
                ingredients = "Mélange de fruits de mer cuits, céleri, oignon rouge, persil, citron, huile d'olive",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Préparer les fruits de mer (décongeler si nécessaire)."),
                Etape(recetteId = 1, ordre = 2, description = "Hacher le céleri, l'oignon et le persil."),
                Etape(recetteId = 1, ordre = 3, description = "Mélanger tous les ingrédients avec le jus de citron et l'huile d'olive.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade de Haricots Verts et Feta",
                image = "salade_haricots_feta",
                ingredients = "Haricots verts, feta, tomates séchées, pignons de pin, vinaigrette balsamique",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire les haricots verts à la vapeur."),
                Etape(recetteId = 1, ordre = 2, description = "Couper la feta et les tomates séchées."),
                Etape(recetteId = 1, ordre = 3, description = "Faire griller les pignons de pin."),
                Etape(recetteId = 1, ordre = 4, description = "Mélanger tous les ingrédients avec la vinaigrette.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Salade de Poulet Avocat",
                image = "salade_poulet_avocat",
                ingredients = "Poulet cuit, avocat, maïs, oignon rouge, coriandre, jus de lime, huile d'olive",
                categorieId = saladeId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Couper le poulet et l'avocat en dés."),
                Etape(recetteId = 1, ordre = 2, description = "Hacher l'oignon rouge et la coriandre."),
                Etape(recetteId = 1, ordre = 3, description = "Mélanger tous les ingrédients avec le jus de lime et l'huile d'olive.")
            )
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
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Façonner le steak et le cuire à la poêle."),
                Etape(recetteId = 1, ordre = 2, description = "Faire griller le pain."),
                Etape(recetteId = 1, ordre = 3, description = "Assembler le burger avec tous les ingrédients.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Burger Bacon-Fromage",
                image = "burger_bacon_fromage",
                ingredients = "Pain à burger, steak haché, fromage, bacon grillé, oignon caramélisé, sauce BBQ",
                categorieId = burgerId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Façonner le steak et le cuire avec le fromage."),
                Etape(recetteId = 1, ordre = 2, description = "Faire griller le bacon et caraméliser l'oignon."),
                Etape(recetteId = 1, ordre = 3, description = "Faire griller le pain."),
                Etape(recetteId = 1, ordre = 4, description = "Assembler le burger.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Burger Végétarien aux Champignons",
                image = "burger_vegetarien_champignons",
                ingredients = "Pain à burger, grand champignon portobello, fromage de chèvre, roquette, poivron grillé, sauce pesto",
                categorieId = burgerId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Faire griller le champignon et le poivron."),
                Etape(recetteId = 1, ordre = 2, description = "Faire griller le pain."),
                Etape(recetteId = 1, ordre = 3, description = "Assembler le burger avec le fromage de chèvre et la roquette.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Burger Poulet Croustillant",
                image = "burger_poulet_croustillant",
                ingredients = "Pain à burger, filet de poulet pané, salade de chou, sauce tartare",
                categorieId = burgerId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Faire frire ou cuire le filet de poulet pané."),
                Etape(recetteId = 1, ordre = 2, description = "Préparer la salade de chou."),
                Etape(recetteId = 1, ordre = 3, description = "Faire griller le pain."),
                Etape(recetteId = 1, ordre = 4, description = "Assembler le burger.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Burger Mexicain",
                image = "burger_mexicain",
                ingredients = "Pain à burger, steak haché, guacamole, salsa, piment jalapeño, fromage Monterey Jack",
                categorieId = burgerId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Façonner le steak et le cuire avec le fromage."),
                Etape(recetteId = 1, ordre = 2, description = "Préparer le guacamole et la salsa."),
                Etape(recetteId = 1, ordre = 3, description = "Faire griller le pain."),
                Etape(recetteId = 1, ordre = 4, description = "Assembler le burger.")
            )
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
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Faire revenir l'oignon, l'ail, les carottes et le céleri."),
                Etape(recetteId = 1, ordre = 2, description = "Ajouter la viande hachée et cuire."),
                Etape(recetteId = 1, ordre = 3, description = "Incorporer la sauce tomate et laisser mijoter."),
                Etape(recetteId = 1, ordre = 4, description = "Cuire les spaghetti al dente."),
                Etape(recetteId = 1, ordre = 5, description = "Servir la sauce sur les spaghetti.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Carbonara Traditionnelle",
                image = "carbonara_traditionnelle",
                ingredients = "Spaghetti, guanciale ou pancetta, œufs, pecorino romano, poivre noir",
                categorieId = patesId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Couper le guanciale/pancetta en dés et le faire revenir."),
                Etape(recetteId = 1, ordre = 2, description = "Battre les œufs avec le pecorino et le poivre."),
                Etape(recetteId = 1, ordre = 3, description = "Cuire les spaghetti al dente."),
                Etape(recetteId = 1, ordre = 4, description = "Mélanger les pâtes chaudes avec la sauce aux œufs et le guanciale.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Pesto Genovese",
                image = "pesto_genovese",
                ingredients = "Pâtes, basilic frais, pignons de pin, parmesan, pecorino, ail, huile d'olive",
                categorieId = patesId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Préparer le pesto en mixant tous les ingrédients."),
                Etape(recetteId = 1, ordre = 2, description = "Cuire les pâtes al dente."),
                Etape(recetteId = 1, ordre = 3, description = "Mélanger les pâtes avec le pesto.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Lasagnes à la Bolognaise",
                image = "lasagnes_bolognaise",
                ingredients = "Feuilles de lasagnes, sauce bolognaise, béchamel, parmesan",
                categorieId = patesId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Préparer la sauce bolognaise et la béchamel."),
                Etape(recetteId = 1, ordre = 2, description = "Faire cuire les feuilles de lasagnes."),
                Etape(recetteId = 1, ordre = 3, description = "Alterner couches de lasagnes, bolognaise et béchamel dans un plat."),
                Etape(recetteId = 1, ordre = 4, description = "Saupoudrer de parmesan et faire gratiner au four.")
            )
        )
        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Mac and Cheese",
                image = "mac_and_cheese",
                ingredients = "Macaroni, beurre, farine, lait, fromage cheddar, sel, poivre",
                categorieId = patesId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description="Cuire les macaroni al dente."),
                Etape(recetteId = 1, ordre = 2, description = "Préparer une sauce béchamel avec le beurre, la farine et le lait."),
                Etape(recetteId = 1, ordre = 3, description = "Incorporer le fromage à la sauce."),
                Etape(recetteId = 1, ordre = 4, description = "Mélanger les macaroni avec la sauce au fromage.")
            )
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
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire le boeuf"),
                Etape(recetteId = 1, ordre = 2, description = "Ajouter épices"),
                Etape(recetteId = 1, ordre = 3, description = "Assembler")
            )
        )

        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Tacos végétarien",
                image = "tacos_vege",
                ingredients = "Haricots rouges, maïs, avocat",
                categorieId = tacosId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Préparer les ingrédients"),
                Etape(recetteId = 1, ordre = 2, description = "Assembler"),
                Etape(recetteId = 1, ordre = 3, description = "Servir")
            )
        )

        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Tacos au poisson",
                image = "tacos_poisson",
                ingredients = "Poisson pané, salade, sauce",
                categorieId = tacosId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire le poisson"),
                Etape(recetteId = 1, ordre = 2, description = "Préparer garniture"),
                Etape(recetteId = 1, ordre = 3, description = "Assembler")
            )
        )

        db.recetteDao().insertRecetteAvecEtapes(
            Recette(
                nomRecette = "Tacos cheddar bacon",
                image = "tacos_cheddar",
                ingredients = "Fromage cheddar, bacon grillé",
                categorieId = tacosId,
                isMock = 0
            ),
            listOf(
                Etape(recetteId = 1, ordre = 1, description ="Cuire bacon"),
                Etape(recetteId = 1, ordre = 2, description = "Ajouter fromage"),
                Etape(recetteId = 1, ordre = 3, description = "Assembler")
            )
        )
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