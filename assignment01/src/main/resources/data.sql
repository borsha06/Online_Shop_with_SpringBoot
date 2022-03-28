INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (1, 'Pilsner', '/images/pilsner.jpg', 5, 0.5);
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (2, 'CocaCola', '/images/coca-cola.jpg', 4, 0.5);
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (3, 'Pepsi', '/images/pepsi.jpg', 3, 0.5 );
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (4, 'Redbull', '/images/redbull.jpg/', 2, 0.5);
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (5, '7Up', '/images/sevenup.jpg', 3, 0.5 );
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (6, 'Sprite', '/images/sprite.jpg', 3, 0.5);

INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (7, 'Pilsner_crate', '/images/crate.jpg', 5, 0.5);
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (8, 'Cocacola_crate', '/images/crate.jpg', 4, 0.5);
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (9, 'Pepsi_crate', '/images/crate.jpg', 3, 0.5 );
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (10, 'Redbull_crate', '/images/crate.jpg/', 2, 0.5);
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (11, '7Up_crate', '/images/crate.jpg', 3, 0.5 );
INSERT INTO beverages (beverage_id, name, beverage_pic, beverage_price, alcohol_percent) VALUES (12, 'Sprite_crate', '/images/crate.jpg', 3, 0.5);

INSERT INTO bottles (bottle_id, volume, supplier, bottle_in_stock, beverage_id) VALUES (1, 0.5, 'Pilsner', 10, 1);
INSERT INTO bottles (bottle_id, volume, supplier, bottle_in_stock, beverage_id) VALUES (2, 0.5, 'CocaCola', 10, 2);
INSERT INTO bottles (bottle_id, volume, supplier, bottle_in_stock, beverage_id) VALUES (3, 0.5, 'Pepsi', 20, 3);
INSERT INTO bottles (bottle_id, volume, supplier, bottle_in_stock, beverage_id) VALUES (4, 0.5, 'Redbull', 30, 4);
INSERT INTO bottles (bottle_id, volume, supplier, bottle_in_stock, beverage_id) VALUES (5, 0.5, '7Up',23, 5);
INSERT INTO bottles (bottle_id, volume, supplier, bottle_in_stock, beverage_id) VALUES (6, 0.5, 'Sprite',7, 6);


INSERT INTO crates (crate_id, no_of_bottles, crate_in_stock, bottle_id, crate_beverage_id) VALUES (1, 10, 10, 1, 7);
INSERT INTO crates (crate_id, no_of_bottles, crate_in_stock, bottle_id, crate_beverage_id) VALUES (2, 10, 10, 2, 8);
INSERT INTO crates (crate_id, no_of_bottles, crate_in_stock, bottle_id, crate_beverage_id) VALUES (3, 10, 5, 3, 9);
INSERT INTO crates (crate_id, no_of_bottles, crate_in_stock, bottle_id, crate_beverage_id) VALUES (4, 10, 10, 4, 10);
INSERT INTO crates (crate_id, no_of_bottles, crate_in_stock, bottle_id, crate_beverage_id) VALUES (5, 10, 10, 5, 11);
INSERT INTO crates (crate_id, no_of_bottles, crate_in_stock, bottle_id, crate_beverage_id) VALUES (6, 10, 10, 6, 12);