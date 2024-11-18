# Woche 10: Webservices mit REST

## Vorlesungsfolien

[Webservices mit REST](Webservices%20mit%20REST.pdf)


## Übungen

Das Projekt «contactlist-rest» in der Vorlage dieser Woche entspricht der Lösung von letzter Woche. Du kannst auch deine eigene Lösung für folgende Übungen weiter verwenden.


### 1. Zugriff auf REST-API (Vorlesung)

In der Vorlage dieser Woche findest du zudem das Projekt «rest-clients» und darin das Programm `WeatherClient`. Dieses verwendet eine [öffentliche REST-API](https://open-meteo.com/) zum Abrufen des aktuellen Wetterberichts.

Studiere das Programm und dessen Ausgabe. Ändere auch mal den Ort und beobachte die Änderungen in der Ausgabe.

Erweitere das Programm so, dass es weitere Wetterdaten abfragt und auf der Konsole ausgibt, z. b. Luftfeuchtigkeit und Windgeschwindigkeit. Ziehe die Dokumentation der API zu Rate: https://open-meteo.com/en/docs


### 2. REST-Endpunkt mit Spring implementieren (Vorlesung)

Erstelle einen ersten einfachen API-Endpunkt, welcher es erlaubt, unter [/api/contacts](http://localhost:8080/api/contacts) die gesamte Liste von Kontakten abzurufen.

Erstelle dazu einen rest-controller namens `ContactsRestController` und füge eine entsprechende Methode hinzu. Verwende den `ContactService` und erweitere ihn wie benötigt.

Teste den Endpunkt einmal im Browser, einmal mittels `curl` oder IntelliJ-HTTP-Datei und schliesslich noch mit dem Programm `ContactsClient` im Projekt «rest-clients».


### 3. Integration-Test für den REST-Endpunkt

Erstelle einen Integration-Test für den REST-Endpunkt. Verwende dafür `@SpringBootTest`, `@AutoConfigureTestDatabase` sowie `HttpClient` und `ObjectMapper`. Du kannst dich am `ContactsClient` orientieren.


### 4. `RestClient`-Klasse

Die Klasse `HttpClient` aus der Standardbibliothek von Java ist etwas umständlich zu verwenden. Spring bietet mit `RestClient` eine einfachere Alternative, welche unter der Haube auch gleich das Data-binding mittels `ObjectMapper` erledigt.

Informiere dich in der [Spring-Dokumentation](https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-restclient) über die Verwendung von `RestClient` und passe den oben geschriebenen Integration-Test entsprechend an. Folgendes Codestück hilft dir dabei, den `RestClient` sinnvoll zu initialisieren:

```java
@LocalServerPort
int port;

RestClient client;

@BeforeEach
public void setup() {
    client = RestClient.create("http://localhost:" + port + "/api/");
}
```

Danach kannst du bei der Verwendung vom `client` die URL relativ zum Basis-URL angeben, z. B.:

```java
var contacts = client.get()
        .uri("contacts")
        .retrieve().body(Contact[].class);
```


### 5. REST-Endpunkte für einzelne Kontakte

Erweitere die REST-API um weitere Endpunkte, sodass einzelne Kontakte unter `/api/contacts/{id}` abgefragt, überschrieben und gelöscht werden können. Dafür brauchst du `@GetMapping`, `@PutMapping` und `@DeleteMapping`.

Erweitere den Integration-Test entsprechend. Beachte, dass per Default dieselbe Testdatenbank für _alle_ Tests verwendet wird. Annotiere Test-Methoden, welche die Datenbank verändern, deshalb mit `@DirtiesContext`. Damit lädt Spring nach dem Test den Kontext neu und die Datenbank wird zurückgesetzt.


### 6. REST-Endpunkt für das Erstellen von Kontakten

Füge zum Schluss noch einen Endpunkt hinzu, um mittels `POST` auf `/api/contacts` neue Kontakte zu erstellen. Die Antwort soll mittels `Location`-Header die URL des neu erstellten Kontakts zurückgeben. Verwende dafür `@PostMapping` und als Rückgabewert ein Objekt vom Typ `ResponseEntity<String>`, welches du mit `ResponseEntity.created(…).…` erstellen kannst.

Erweitere den Integration-Test wiederum entsprechend.