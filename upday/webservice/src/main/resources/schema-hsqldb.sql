create table NEWS_ARTICLE 
(
    id INTEGER NOT NULL,
    headline VARCHAR(300),
    description VARCHAR(500),
    maintext VARCHAR(1000),
    publishedOn DATE,
    createdOn DATE NOT NULL,
    author_id INTEGER NOT NULL,
    PRIMARY KEY (id)
);

create table NEWS_AUTHOR 
(
    id INTEGER NOT NULL,
    firstname VARCHAR(300),
    lastname VARCHAR(300),
    createdOn DATE NOT NULL,
    PRIMARY KEY (id)
);

create table NEWS_KEYWORD 
(
    id INTEGER NOT NULL,
    name VARCHAR(300),
    description VARCHAR(300),
    createdOn DATE NOT NULL,
    PRIMARY KEY (id)
);

-- mapping table:

create table NEWS_ARTICLE_KEYWORD
(
    article_id INTEGER NOT NULL,
    keyword_id INTEGER NOT NULL
);

-- foreign keys

ALTER TABLE NEWS_ARTICLE
    ADD FOREIGN KEY (AUTHOR_ID) 
    REFERENCES NEWS_AUTHOR(ID);

ALTER TABLE NEWS_ARTICLE_KEYWORD
    ADD FOREIGN KEY (ARTICLE_ID) 
    REFERENCES NEWS_ARTICLE(ID);

ALTER TABLE NEWS_ARTICLE_KEYWORD
    ADD FOREIGN KEY (KEYWORD_ID) 
    REFERENCES NEWS_KEYWORD(ID);
