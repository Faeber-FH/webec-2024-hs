# Woche 6: Persistenz Teil 1

## Vorlesungsfolien

[Persistenz Teil 1](06%20Persistenz%20Teil%201.pdf)


## Übungen

Die Vorlage von dieser Woche, «contactlist-persistence» entspricht der Lösung von letzter Woche. Du kannst auch deine eigene Lösung für folgende Übungen weiter verwenden (aber musst zum gegebenen Zeitpunkt die Tests in `ContactServiceTest` auskommentieren).


### 1. Contact als Entity-Klasse (Vorlesung)

Füge dem Projekt «contactlist-persistence» die Dependencies für Spring Data JPA und für die H2-Datenbank hinzu (danach wieder Maven neu laden).

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
```

Mache aus der `Contact`-Klasse eine JPA-Entity, indem du die benötigten Annotationen hinzufügst. Entferne die Methode `setId()`, denn die IDs sollen nun automatisch von der Datenbank generiert werden.

Starte die Web-App und beobachte folgende Ausgabe auf der Konsole:

```
… H2 console available at '/h2-console'. DB available at 'jdbc:h2:mem:3297da63-9949-4c70-9c4c-1c80c52da336'
```

Rufe die URL [localhost:8080/h2-console](http://localhost:8080/h2-console) auf, logge dich bei der in der Konsole angezeigten Datenbank ein (Passwort: leer) und inspiziere die generierten Tabellen und Spalten.


### 2. Ein Repository für Contact (Vorlesung)

Erstelle ein Spring Data Repository für `Contact`. Du brauchst keine Query-Methoden hinzuzufügen; die vordefinierten reichen für diese App.

Ändere den `ContactService` so ab, dass statt der `ArrayList` das neue Repository zum Speichern der Kontakte verwendet wird. Du bekommst eine Instanz des Repositories durch Dependency-Injection. Verwende vorerst folgende Repository-Methoden: `findAll()`, `findById(id)`, `save(entity)`.

Starte die App und überprüfe, dass die Kontakte weiterhin alle angezeigt werden. Öffne nochmals die H2-Console und führe folgende Abfrage aus:

```sql
SELECT * FROM CONTACT
```

Die Tabellen sollten jetzt gefüllt worden sein.


### 3. Kontakte erstellen

Füge ein Formular zur Web-App hinzu, mit welchem man neue Kontakte erstellen kann. Füge vorerst mal nur Felder für Vorname, Nachname, Jobtitel und Firma hinzu. Verlinke das Formular auf der Contacts-Startseite.

Implementiere eine neue Controller-Methode, welche beim Abschicken des Formulars aufgerufen wird. Rufe vom Controller die `ContactService.add()`-Methode auf, um einen neuen Kontakt in der Datenbank zu speichern. Danach soll die Seite mit den Details des soeben hinzugefügten Kontakts angezeigt werden.

**Hinweis:** Mit `return "redirect:/[url]"` kann man in einem Controller einen Status `302` zurückgeben, der den Browser auf die Seite unter `/[url]` weiterleitet.

### 4. Kontakte editieren

Füge unterhalb der Tabelle mit den Kontakt-Details einen «Edit»-Link hinzu, der zu einem weiteren Formular führt, mit welchem der aktuelle Kontakt bearbeitet werden kann. Beschränke dich wiederum auf die vier einfachen Eigenschaften.

Beachte, dass zum Aktualisieren eines vorhandenen Kontakts die gleiche `save(entity)`-Methode des Spring-Data-Repositories verwendet werden kann. Füge der `ContactService`-Klasse zur Klarstellung vielleicht trotzdem eine weitere Methode, z. B. `update(contact)` hinzu.

**Hinweis:** Das HTML für dieses Formular hat viele Ähnlichkeiten zu dem der vorherigen Aufgabe; verwende geeignete Pebble-Konstrukte, um grossflächige Code-Duplizierung zu vermeiden.

### 5. Kontakte löschen

Füge neben dem «Edit»-Link einen «Delete»-Button hinzu, der den entsprechenden Kontakt löscht. Aus Sicherheitsgründen sollte das Löschen via `POST`-Anfrage (d. h. über ein Mini-Formular) gemacht werden, nicht via `GET` (d. h. über einen einfachen Link). Dazu mehr in der Woche «Security».

Erweitere dazu die `ContactService`-Klasse um eine Methode `delete(contact)`, welche die `delete`-Methode des Repositories aufruft.

### 6. E-Mails-Adressen & Telefonnummern editieren (Knacknuss)

Erweitere das Editier-Formular so, dass man E-Mail-Adressen und Telefonnummern löschen und hinzufügen kann. Als Vereinfachung kannst du das so lösen, dass in einem «Durchgang» immer nur eine neue E-Mail/Nummer dazukommen kann.

**Tipp:** Das Löschen kann z. B. über Checkboxes gemacht werden, welche im `name` den Index der entsprechenden E-Mail/Nummer enthalten. Um beliebig viele Anfrage-Parameter zu unterstützen, kann eine Controller-Methode ein `HttpServletRequest`-Objekt mit einer Methode `getParameterMap()` entgegennehmen.
