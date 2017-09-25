
--
-- Structure for table grukeydiversification_encryptionkey
--

DROP TABLE IF EXISTS grukeydiversification_encryptionkey;
CREATE TABLE grukeydiversification_encryptionkey (
id_encryption_key int AUTO_INCREMENT,
code varchar(255) UNIQUE,
key_value varchar(255) default '' NOT NULL,
PRIMARY KEY (id_encryption_key)
);
