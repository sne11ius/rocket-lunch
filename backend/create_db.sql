-- Bei postgresql gibt es eine Trennung zwischen Database und Schema.
-- Leute empfehlen, lieber mehr Schemas als Databases zu haben. Wir verwenden
-- daher das default-Schema (und verlassen uns darauf, dass encoding etc. dort
-- korrekt konfiguriert sind.
-- Hier erzeugen wir demnach nur ein neues Schema.

CREATE SCHEMA rocketlunch;
