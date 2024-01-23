INSERT INTO personnels (name, username, password, role) VALUES ("John Doe", "john123", "johnpass123", "ROLE_EMP");
INSERT INTO personnels (name, username, password, role) VALUES ("Mary Mia", "mary123", "marypass123", "ROLE_EMP");
INSERT INTO personnels (name, username, password, role) VALUES ("Bill Bio", "bill123", "billpass123", "ROLE_MANAGER");

INSERT INTO customers (name, gender, subscribed, canceled) VALUES ("Andrew", "Male", true, false);
INSERT INTO customers (name, gender, subscribed, canceled) VALUES ("Stef", "Male", false, true);
INSERT INTO customers (name, gender, subscribed, canceled) VALUES ("Kostas", "Male", true, false);
INSERT INTO customers (name, gender, subscribed, canceled) VALUES ("Emely", "Female", false, false);

INSERT INTO contacts (date_set, details, personnels_id, customers_id) VALUES (NOW(), "Details about the first contact", 2, 1);
INSERT INTO contacts (date_set, details, personnels_id, customers_id) VALUES (NOW(), "Details about the second contact", 2, 2);
INSERT INTO contacts (date_set, details, personnels_id, customers_id) VALUES (NOW(), "Details about the third contact", 2, 1);
INSERT INTO contacts (date_set, details, personnels_id, customers_id) VALUES (NOW(), "Details about the fourth contact", 1, 3);
INSERT INTO contacts (date_set, details, personnels_id, customers_id) VALUES (NOW(), "Details about the fifth contact", 1, 4);