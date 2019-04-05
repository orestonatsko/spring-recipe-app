CREATE SCHEMA IF NOT EXISTS `sfg_dev` DEFAULT CHARACTER SET utf8 ;
USE `sfg_dev` ;

CREATE TABLE IF NOT EXISTS `sfg_dev`.`category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `sfg_dev`.`unit_of_measure` (
  `uom_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`uom_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `sfg_dev`.`difficulty` (
  `difficulty_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`difficulty_id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `sfg_dev`.`note` (
  `note_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_note` TEXT(1000) NULL,
  PRIMARY KEY (`note_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `sfg_dev`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `difficulty_id` INT NOT NULL,
  `note_id` INT NOT NULL,
  `description` VARCHAR(100) NULL,
  `directions` TEXT(3000) NULL,
  `prep_time` INT NULL,
  `cook_time` INT NULL,
  `servings` INT NULL,
  PRIMARY KEY (`recipe_id`),
  INDEX `fk_recipe_difficulty_idx` (`difficulty_id` ASC) VISIBLE,
  INDEX `fk_recipe_note1_idx` (`note_id` ASC) VISIBLE,
  CONSTRAINT `fk_recipe_difficulty`
    FOREIGN KEY (`difficulty_id`)
    REFERENCES `sfg_dev`.`difficulty` (`difficulty_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_note1`
    FOREIGN KEY (`note_id`)
    REFERENCES `sfg_dev`.`note` (`note_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `sfg_dev`.`ingredient` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `uom_id` INT NOT NULL,
  `description` VARCHAR(100) NULL,
  `amount` DECIMAL(2) NULL,
  PRIMARY KEY (`ingredient_id`),
  INDEX `fk_ingredient_recipe1_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `fk_ingredient_unit_of_measure1_idx` (`uom_id` ASC) VISIBLE,
  CONSTRAINT `fk_ingredient_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `sfg_dev`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingredient_unit_of_measure1`
    FOREIGN KEY (`uom_id`)
    REFERENCES `sfg_dev`.`unit_of_measure` (`uom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `sfg_dev`.`recipe_category` (
  `category_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`category_id`, `recipe_id`),
  INDEX `fk_Category_has_recipe_recipe1_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `fk_Category_has_recipe_Category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_Category_has_recipe_Category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `sfg_dev`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Category_has_recipe_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `sfg_dev`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO difficulty(name)
				VALUES('EASY'),
					    ('MODERATE'),
              ('HARD');
                      

INSERT INTO category (description) VALUES ('American');
INSERT INTO category (description) VALUES ('Italian');
INSERT INTO category (description) VALUES ('Mexican');
INSERT INTO category (description) VALUES ('Fast Food');

INSERT INTO unit_of_measure (description) VALUES ('Teaspoon');
INSERT INTO unit_of_measure (description) VALUES ('Tablespoon');
INSERT INTO unit_of_measure (description) VALUES ('Cup');
INSERT INTO unit_of_measure (description) VALUES ('Pinch');
INSERT INTO UNIT_OF_MEASURE (description) VALUES ('Ounce');
INSERT INTO UNIT_OF_MEASURE (description) VALUES ('Dash');

INSERT INTO recipe(difficulty_id, note_id, description, directions, prep_time, cook_time, servings)
			VALUES(1, 1, 'Ідеальні Гваламоле', "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon 2 Mash with a fork: Using a fork, roughly mash the avocado. (Don\'t overdo it! The guacamole should be a little chunky.)
                3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.
                Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.
                Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.
                4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.
                Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.", 5, 30, 1),
                (2, 2, 'Курячі тако', "1 Prepare a gas or charcoal grill for medium-high, direct heat.
                2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.
                Set aside to marinate while the grill heats and you prepare the rest of the toppings.
                3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.
                4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.
                Wrap warmed tortillas in a tea towel to keep them warm until serving.
                5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.", 5, 30, 2);
			
INSERT INTO ingredient(recipe_id, uom_id, description, amount)
				VALUES(1, 5, 'ripe avocados', 1.00),
					  (1, 1, 'Kosher salt', 2.00),
					  (1, 2,'fresh lime juice or lemon juice', 2.00),
                      (1, 1, 'minced red onion or thinly sliced green onion', 1.00),
                      (1, 6, 'serrano chiles, stems and seeds removed, minced', 2.00),
                      (1, 1, 'Cilantro', 3.00),
					  (2, 1, 'Ancho Chili Powder', 2.00),
					  (2, 3, 'Dried Oregano', 1.00),
                      (2, 5, 'Dried Cumin', 1.00),
                      (2, 1, 'Sugar', 1.00),
                      (2, 4, 'Clove of Garlic, Choppedr', 1.00),
                      (2, 2, 'Salt', 1.00);

INSERT INTO recipe_category(category_id, recipe_id)
			VALUES(1, 1),
				  (2, 2);

INSERT INTO note(recipe_note)
			VALUES("We have a family motto and it is this: Everything goes better in a tortilla.
                Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.
                Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!
                First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.
                Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!"),
				  ("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.
                Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.
                The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.
                To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.");
	