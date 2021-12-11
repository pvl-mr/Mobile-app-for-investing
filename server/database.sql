create TABLE analyst(
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    pass VARCHAR(255) NOT NULL,
    code INTEGER
);

create TABLE client(
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    pass VARCHAR(255) NOT NULL,
);

create TABLE stock(
    id SERIAL PRIMARY KEY,
    stockName VARCHAR(255) NOT NULL,
    stockDesc VARCHAR(255) NOT NULL
);

create TABLE bond(
    id SERIAL PRIMARY KEY,
    bondName VARCHAR(255) NOT NULL,
    stockDesc VARCHAR(255) NOT NULL
);


create TABLE portfolio(
    id SERIAL PRIMARY KEY,
    years INTEGER NOT NULL,
    goal VARCHAR(255) NOT NULL,
	analystId INTEGER,
	clientId INTEGER,
    FOREIGN KEY (analystId) REFERENCES analyst (id),
    FOREIGN KEY (clientId) REFERENCES client (id)
);

create TABLE portfolio_stock(
    id SERIAL PRIMARY KEY,
    stockId int NOT NULL,
    portfolioId int NOT NULL,
    count INTEGER,
    FOREIGN KEY (portfolioId) REFERENCES portfolio (id),
    FOREIGN KEY (stockId) REFERENCES stock (id)
);

create TABLE portfolio_bond(
    id SERIAL PRIMARY KEY,
    bondId int NOT NULL,
    portfolioId int NOT NULL,
    count INTEGER,
    FOREIGN KEY(portfolioId) REFERENCES portfolio (id),
    FOREIGN KEY(bondId) REFERENCES bond (id)
);
