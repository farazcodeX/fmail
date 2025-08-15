USE pedantic_taussig;
CREATE TABLE users (
    id INT  AUTO_INCREMENT PRIMARY KEY ,
    name NVARCHAR(50) NOT NULL ,
    fmail NVARCHAR(50) NOT NULL UNIQUE ,
    password NVARCHAR(50) NOT NULL
);