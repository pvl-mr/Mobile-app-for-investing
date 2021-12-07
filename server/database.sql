create TABLE person(
     id SERIAL PRIMARY KEY,
     firstName VARCHAR(255),
     lastName VARCHAR(255),
     telephoneNumber VARCHAR(255),
     isDeleted boolean DEFAULT FALSE
);

create TABLE pet(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    breed VARCHAR(255),
    age INTEGER,
    personId INTEGER,
    FOREIGN KEY (personId) REFERENCES person (id),
    isDeleted boolean DEFAULT FALSE
);

create TABLE user(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
);