interface Animal {
    String hasBeenEatenBy(Predator p);
}
class Cat implements Animal {
    public String hasBeenEatenBy(Predator p) {
        return p.eat(this);
    }
}
class Bird implements Animal {
    public String hasBeenEatenBy(Predator p) {
        return p.eat(this);
    }
}

interface Predator {
    default String eat(Animal animal) { 
        return animal.hasBeenEatenBy(this);
    }
    String eat(Cat cat);
    String eat(Bird bird);
}
class Lion implements Predator {
    public String eat(Cat cat) { return "Lion eat a cat"; }
    public String eat(Bird bird) { return "Lion eat a bird"; }
}

public class PredatorApp {
    public static void log(Object o) { System.out.println(o); }

    public static void main(String[] args) {
        Predator p = new Lion();
        Animal a = new Cat();
        log( p.eat(a) );
    }
}

