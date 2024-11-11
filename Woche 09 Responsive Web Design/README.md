# Woche 9: Responsive Web Design

## Vorlesungsfolien

[Responsive Web Design](Responsive%20Web%20Design.pdf)


## Übungen

### 1. Erste Schritte mit Responsive (Vorlesung)

Lade die neue Vorlage in deine IDE, starte die App und betrachte sie im Responsive Design Mode in deinem Browser.
Wähle als Gerät ein Smartphone mit 360 – 400 px Breite.

1. Füge das in den Folien gezeigte `<meta>`-Element innerhalb von `<head>` zum App-Layout hinzu und beobachte die Änderung im Browser.

2. Mache einen ersten Schritt Richtung Responsive Design, indem du die Titelgrösse für `<h1>` anpasst. Definiere die
   Grösse relativ zum Viewport, aber nur bis zu einem bestimmten Maximum. 
   Überprüfe im Browser, dass die Titelgrösse jetzt responsive ist.



### 2. Einfache Media Query (Vorlesung)

Der Text der App ist allgemein zu gross für kleine Geräte. Implementiere mittels einer Media Query folgende Regeln:

* Für «kleine» Geräte wird als Grundschriftgrösse 100 % verwendet.
* Für «grosse» Geräte wird hingegen 120 % verwendet (wie bisher).

Entscheide, ab welcher Breite ein Gerät als «gross» gilt und wähle zwischen einer `min-width`- und einer `max-width`-Regel.


### 3. Contact-List vollständig responsive machen

Passe die Contact-List-App weiter an, sodass sie vollständig responsive ist. Auf grösseren Geräten soll sie ungefähr
wie bisher aussehen, aber soll auch gut für kleine Geräte (bis zu 360 × 640 px klein) funktionieren.

Beachte folgende Aspekte:

* Die Kontaktliste hat auf kleinen Geräten nicht neben dem Hauptinhalt Platz. Schiebe sie bei Bedarf nach unten.
  Denke an das Flex-Layout (siehe [Woche 2](../Woche%2002%20CSS)).

* Die Kontakt-Details und die Formulare sind ebenfalls zu breit für kleine Geräte. 
  Versuche, das Problem einmal mit Media Queries und einmal mit Grid Layout zu lösen.
  Eventuell musst du dazu das HTML-Template ändern.

* Bei einer Breite von etwa 360 px sieht das Menü oben unpassend aus, da es nur ein kleines bisschen kleiner als die 
  Gerätebreite ist. Ändere es so ab, dass es auf «kleinen» Geräten die ganze Breite ausfüllt.
