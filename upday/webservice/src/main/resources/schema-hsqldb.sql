create table NEWS_ARTICLE 
(
    ID INTEGER NOT NULL,
    HEADLINE VARCHAR(300),
    DESCRIPTION VARCHAR(500),
    TEXT VARCHAR(3000),
    PUBLISHED_ON DATE,
    CREATED_ON DATE NOT NULL,
    UPDATED_ON DATE NOT NULL,
    PRIMARY KEY (id)
);

create table NEWS_AUTHOR 
(
    ID INTEGER NOT NULL,
    FIRSTNAME VARCHAR(300),
    LASTNAME VARCHAR(300),
    CREATED_ON DATE NOT NULL,
    UPDATED_ON DATE NOT NULL,
    PRIMARY KEY (id)
);

create table NEWS_KEYWORD 
(
    ID INTEGER NOT NULL,
    NAME VARCHAR(300),
    DESCRIPTION VARCHAR(300),
    CREATED_ON DATE NOT NULL,
    UPDATED_ON DATE NOT NULL,
    PRIMARY KEY (id)
);

-- mapping tables:

create table NEWS_ARTICLE_KEYWORD
(
    article_id INTEGER NOT NULL,
    keyword_id INTEGER NOT NULL
);

create table NEWS_ARTICLE_AUTHOR
(
    article_id INTEGER NOT NULL,
    author_id INTEGER NOT NULL
);

-- foreign keys

ALTER TABLE NEWS_ARTICLE_KEYWORD
    ADD FOREIGN KEY (ARTICLE_ID) 
    REFERENCES NEWS_ARTICLE(ID);

ALTER TABLE NEWS_ARTICLE_KEYWORD
    ADD FOREIGN KEY (KEYWORD_ID) 
    REFERENCES NEWS_KEYWORD(ID);

ALTER TABLE NEWS_ARTICLE_AUTHOR
    ADD FOREIGN KEY (ARTICLE_ID) 
    REFERENCES NEWS_ARTICLE(ID);

ALTER TABLE NEWS_ARTICLE_AUTHOR
    ADD FOREIGN KEY (AUTHOR_ID) 
    REFERENCES NEWS_AUTHOR(ID);

-- indices

-- TODO!
