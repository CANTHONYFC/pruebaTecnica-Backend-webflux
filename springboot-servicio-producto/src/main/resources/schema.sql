CREATE TABLE IF NOT EXISTS producto(id INT NOT NULL AUTO_INCREMENT,nombre VARCHAR(255),precio DECIMAL,create_user INT,PRIMARY KEY (id));
INSERT INTO producto (nombre,precio,create_user)VALUES('Laptop',5000,1);
