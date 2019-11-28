interface Animal {}
class Cat implements Animal {}
class Bird implements Animal {}

interface Predator {
    default String eat(Animal animal) { return "Predator eat an animal"; }
    default String eat(Cat cat) { return "Predator eat a cat"; }
    default String eat(Bird bird) { return "Predator eat a bird"; }
}
class Lion implements Predator {
    public String eat(Animal animal) { return "Lion eat an animal"; }
    public String eat(Cat cat) { return "Lion eat a cat"; }
    public String eat(Bird bird) { return "Lion eat a bird"; }
}

public class PredatorApp {
    public static void log(Object o) { System.out.println(o); }

    public static void main(String[] args) {
        Predator p = new Lion();
        Cat a = new Cat();
        log( p.eat(a) );
    }
}

