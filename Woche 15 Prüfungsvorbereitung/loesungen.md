# Lösungen

### 1. Gültiges HTML

a) gültig

b) gültig

c) ungültig: `alt`-Attribut fehlt

d) ungültig: `<em>` und `<strong>` falsch geschachtelt

e) ungültig: Attribut `font-size` existiert nicht

f) gültig

g) gültig


### 2. CSS-Selektoren

a) `.normal`: (10)

b) `div > em`: (13)

c) `div.footer em`: keines

d) `nav ul, li em`: (3) (7)

e) `ul :not(:first-child)`: (5) (6)

f) `li + li`: (5) (6)


### 3. CSS-Regeln

Mögliche Lösung:

```css
li {
    color: blue;
}

li:nth-child(2) {
    color: gray;
}

em {
    color: green;
}

body > div {
    color: orange;
}

#footer {
    color: red;
}

#footer em {
    color: purple;
}
```


### 4. Spring Controller

```java
@Controller
public class ConvertController {
    
    private final CurrencyConverter converter;
    
    public ConvertController(CurrencyConverter converter) {
        this.converter = converter;
    }
    
    @GetMapping("convert")
    public String convert(double amount,
                          @RequestParam("target-currency") String currency,
                          Model model) {
        var result = converter.convert(amount, currency);
        model.addAttribute("result", result);
        return "conversion-result";
    }
}
```


### 5. Pebble-Template

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Your Shopping Cart</title>
</head>
<body>
    <h1>{{ user }}'s Shopping Cart</h1>
    No responsibility is taken for the correctness of this information.
    <ul>
        {% for item in cart %}
        <li>
            <strong>{{ item.name }}:</strong> {{ item.price }} CHF
        </li>
        {% endfor %}
    </ul>
    Total: {{ cart.size }} Items
</body>
</html>
```


### 6. JPA

```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String pw;
    private int age;
    @ElementCollection
    private Set<String> groups;
    
    User() {}
}
```

```java
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String text;
    @ManyToOne
    private User user;
    
    Post() {}
}
```

```java
@Entity
public class ThumbsUp {
    @Id
    @GeneratedValue
    private Integer id;
    private long time;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    
    ThumbsUp() {}
}
```

**Hinweise:**

* JPA braucht immer einen Konstruktor ohne Parameter. Dieser kann `public` oder `protected` sein.
* Statt `Integer` kann als ID-Typ auch `int` verwendet werden, aber `Integer` hat den Vorteil, dass man eindeutig unterscheiden kann, ob eine Entity bereits eine ID hat oder noch nicht (`null`).


### 7. Spring Data

```java
var sarah = new User("sarah", "ndRsad8", 37);
sarah.setGroups(Set.of("Tree Lovers", "Scientists"));
var mike = new User("mike", "9MZh421", 25);
var anna = new User("anna", "fDqlp31_", 56);
anna.setGroups(Set.of("Marvel Fans"));
userRepo.save(sarah);
userRepo.save(mike);
userRepo.save(anna);

postRepo.save(new Post("Welcome", "It's a ple...", mike));
postRepo.save(new Post("I love this", "Once mor...", sarah));
postRepo.save(new Post("Next steps", "What we...", mike));
```


### 8. REST-APIs

Anfrage:
```
POST /api/books HTTP/1.1
Content-Type: application/json

{
    "name": "Heidi",
    "author": "Johanna Spyri",
    "year": 1880,
    "pages": 224
}
```

Antwort:
```
HTTP/1.1 201 Created
Location: /api/books/13
Content-Type: application/json

```

Anfrage:
```
DELETE /api/books/13 HTTP/1.1
```

Antwort:
```
HTTP/1.1 204 No Content
```
