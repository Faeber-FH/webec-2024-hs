# Woche 8: Security

## Vorlesungsfolien

[Security](Security.pdf)


## Übungen

Die Vorlage von dieser Woche, «wishlist-security» entspricht der Lösung von letzter Woche. Du kannst auch deine eigene Lösung für folgende Übungen weiter verwenden.


### 1. Hello, Spring Security! (Vorlesung)

Lade das neue Projekt «wishlist-security» in deine IDE. Füge Spring Security als Dependency hinzu und prüfe, dass die App jetzt geschützt ist. Beachte das Passwort, das auf der Konsole ausgegeben wird (Benutzername: «user»).

Konfiguriere Spring Security mittels einer Klasse `WebSecurityConfig` so, dass die Startseite und die Kategorien-Seite öffentlich zugänglich sind, aber die Seiten der einzelnen Wunschlisten nur authentifizierten Usern angezeigt werden. Denk auch an die statischen Inhalte (CSS, Bilder).

Hinweise:

* Das Standard-Login-Formular von Spring erhält man mit der Konfiguration `.formLogin(withDefaults())`
* Das Absenden von POST Requests führt im Moment zu `403 Forbidden`, da wir kein CSRF-Token mitschicken (können wir temporär mit `.csrf(csrf -> csrf.disable())` beheben, müssen wir aber später richtig machen)


### 2. Templates & Security (zum Teil Vorlesung)

Ändere die Pebble-Templates so ab, dass nur eingeloggte User die Links zu den Wunschlisten sehen. Anonyme User sehen die Wunschlisten ohne Links.

Verstecke auch die Formulare zum Erstellen/Löschen von Dingen vor anonymen Usern.


### 3. Einfache Authentifizierung & Rollen

Konfiguriere einen eigenen `InMemoryUserDetailsManager`, der zwei User enthält: einen ohne Rolle und einen mit Rolle `EDITOR`. Dazu kannst du z. B. in der Klasse `WebSecurityConfig` eine Methode hinzufügen, welche mit `@Bean` annotiert ist und diesen `InMemoryUserDetailsManager` zurückgibt.

Konfiguriere die Autorisierung der App so, dass nur `EDITOR`s Listen oder Kategorien ändern können. Denk an die CSRF-Tokens.


### 4. Eigene Login-Page

Konfiguriere eine eigene Login-Page, die sich ins Design der Web-App einfügt. Dazu brauchst du eine 'login.peb'-View, einen einfachen Controller für [/login](http://localhost:8080/login) und die in den Folien gezeigte Konfiguration der `SecurityFilterChain`.

Das Login-Formular muss Felder für `username`, `password` und das CSRF-Token enthalten und einen `POST`-Request zurück an [/login](http://localhost:8080/login) machen. Für das Behandeln dieses `POST`-Requests musst du keinen eigenen Controller schreiben, das macht Spring Security.


### 5. Logout-Button

Füge einen Logout-Button zum Skelett der Seite hinzu. Dazu brauchst du ein Mini-Formular, das einfach einen POST-Request an [/logout](http://localhost:8080/logout) schickt. Der Button soll nur angezeigt werden, wenn ein Benutzer eingeloggt ist.


### 6. Persistente User

Zum Schluss sollst du die User der Applikation in der Datenbank persistieren. Dafür sind einige Schritte nötig, die auch zur Wiederholung der Themen der letzten beiden Wochen dienen.

Erstelle eine JPA-Entity-Klasse `User`, die `UserDetails` implementiert. Füge Attribute für eine generierte ID und für Benutzername, Passwort und Rollen hinzu und implementiere die entsprechenden Methoden. In der `getAuthorities`-Methode kannst du die Rollen als Liste von `SimpleGrantedAuthority`-Objekten zurückgeben (gespeichert sind sie aber als Strings). Zusätzlich musst du einige `boolean`-Methoden von `UserDetails` implementieren, bei denen du überall `true` zurückgeben sollst.

Wenn du eine Collection für das Rollen-Attribut verwendest, braucht es eine spezielle Annotation auf diesem Attribut: `@ElementCollection(fetch = FetchType.EAGER)`. Diese überschreibt den Default-Wert, der dazu führt, dass die Rollen «lazily» geladen werden, was für Spring Security nicht funktioniert.

Erstelle anschließend ein passendes `UserRepository`-Interface. Entferne dann die Methode für den `InMemoryUserDetailsManager` in `WebSecurityConfig` und erstelle stattdessen einen eigenen `UserService`, der `UserDetailsService` implementiert und die Users aus der Datenbank holt.

Zum Schluss sollst du noch dafür sorgen, dass auch ein paar User in der Datenbank vorhanden sind. Füge dazu eine Klasse `InitialUsersAdder` zur App hinzu, die beim Start prüft, ob User vorhanden sind, und ansonsten zwei Beispiel-User erstellt und in die Datenbank einfügt. Orientiere dich am `SampleDataAdder`, der eine ähnliche Funktionalität implementiert. Generiere zufällige Passwörter und verwende `createDelegatingPasswordEncoder`, um diese zu hashen. Gib die Passwörter (im Klartext) auf der Konsole aus.
