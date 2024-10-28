# Woche 7: Integration-Testing & Persistenz Teil 2

## Vorlesungsfolien

[Integration-Testing & Persistenz Teil 2](Integration-Testing%20&%20Persistenz%20Teil%202.pdf)


## Übungen


### 1. Integration-Testing vs. Stubbing (Vorlesung)

Kommentiere den `ContactServiceTest` im Projekt von letzter Woche ein und ergänze ihn um einen Stub für `ContactRepository`, sodass die Tests wieder funktionieren.

Kopiere die Testklasse und mache daraus einen Integrations-Test mit `@DataJpaTest`.


### 2. Entities mit Beziehungen (Vorlesung)

Lade das neue Projekt «wishlist-persistence» in deine IDE, starte die Web-App und mache dich mit der Funktionalität und dem Code vertraut.

Mache die Model-Klassen zu JPA-Entities. Welche Beziehungen (`@OneToMany`, `@ManyToOne`, `@ManyToMany`) machen wo Sinn?

Denke auch an die benötigten Maven-Dependencies (siehe letzte Woche).


### 3. Wishlists persistieren

Erstelle Spring-Data-Repositories für `Wishlist` und `Category` und ändere die Implementierung der Service-Klassen so, dass die Daten persistiert werden. Du kannst vorerst die eingebauten Methoden der Repositories verwenden. Denk auch an die Cascade-Arten und überlege wieder, wo welche Sinn machen.

Konfiguriere zudem eine Datei-basierte H2-Datenbank.

Wenn du die App startest, erhältst du möglicherweise (je nachdem, wie du den Service-Layer implementiert hast) eine Exception in `SampleDataAdder`. Untersuche sie und finde eine Lösung dafür.

Prüfe, dass die Persistierung funktioniert, indem du die App startest, einen Wunsch löschst und die App neu startest. Die Änderungen sollten erhalten bleiben.


### 4. Integritätsverletzungen vermeiden

Versuche, eine Kategorie zu löschen, welche noch von mindestens einem Wunsch verwendet wird. Was stellst du fest?

Behebe das Problem auf eine sinnvolle Weise.


### 5. Integration-Test für `WishlistService`

Erstelle einen Integration-Test für `countWishesByCategory` in der Klasse `WishlistService` mittels `@DataJpaTest`.

Verbessere nun deine erste Implementierung von `countWishesByCategory`, indem du ein `WishRepository` erstellst und eine geeignete Query-Methode hinzufügst. Teste die neue Implementierung mit deinem Integration-Test.


### 6. E2E-Tests mit Test-DB

Führe den E2E-Test `HomePageIT` wiederholt aus. Spätestens ab dem zweiten Mal schlägt er fehl, da er eine Datei-basierte Datenbank verwendet! Behebe das Problem mit `@AutoConfigureTestDatabase`.