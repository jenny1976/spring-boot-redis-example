INSERT INTO NEWS_KEYWORD (ID, NAME, DESCRIPTION, CREATED_ON, updated_on) VALUES (1, 'Nahverkehr', 'Artikel, die S-Bahn, U-Bahn etc. betreffen.', sysdate(), sysdate());
INSERT INTO NEWS_KEYWORD (ID, NAME, DESCRIPTION, CREATED_ON, updated_on) VALUES (2, 'Berlin', 'Berliner Themen.', sysdate(), sysdate());

INSERT INTO NEWS_AUTHOR(ID, FIRSTNAME, LASTNAME, CREATED_ON, updated_on) VALUES (1, 'Lorenz', 'Vossen', sysdate(), sysdate());

INSERT INTO NEWS_ARTICLE (ID, HEADLINE, DESCRIPTION, TEXT, PUBLISHED_ON, CREATED_ON, updated_on) VALUES (1, 'Die S-Bahn baut auch 2016 fleißig weiter', 'Auch 2016 wird bei der S-Bahn fleißig gebaut. Stadt- und Ringbahn sind betroffen. Fußballfans werden verschont – aber nicht alle.', 'Ein Rekordjahr in Sachen Baumaßnahmen hatte die Deutsche Bahn einst für 2015 angekündigt. Seit Donnerstag ist klar: 2016 wird nicht besser. "Es gibte für die Berliner keine Entwarnung", sagt Alexander Kaczmarek, Konzernbevollmächtigter der Bahn für die Hauptstadt.  Zu den bereits bestehenden Bauvorhaben gesellen sich noch einige hinzu. Mal wieder betroffen ist die Stadtbahn – für ein halbes Jahr. Von Mai bis Oktober 2016 rüstet die Bahn den Abschnitt zwischen Friedrichstraße und Westkreuz mit ihrem Zugbeeinflussungssystem ZBS aus, wobei der größte Teil der Arbeiten auf die Strecke Friedrichstraße–Zoo entfällt.', sysdate(), sysdate(), sysdate());

INSERT INTO NEWS_ARTICLE_KEYWORD (ARTICLE_ID, KEYWORD_ID) VALUES (1, 1);

INSERT INTO NEWS_ARTICLE_AUTHOR (ARTICLE_ID, AUTHOR_ID) VALUES (1, 1);