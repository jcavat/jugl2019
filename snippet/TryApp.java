import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Stream;


public class TryApp {

    public static void log(Object o) { System.out.println(o.toString()); }
    public static int nextThrowingInt() {
        int i = new Random().nextInt(10);
        if( i <= 6 ) { return i; } 
        throw new RuntimeException();
    }
    public static int askThrowingInt() { return Integer.valueOf(System.console().readLine()); }

    public static void main(String[] args) {

        log("Hello JUGL! ");


























        /*
        Try<Integer> t = Try.of( nextThrowingInt() );
        if( t.isSuccess() ) {
            log("Value: " + t.get() );
        } else {
            log("Error with: " + t.getException() );
        }


        Stream.generate( () -> nextThrowingInt() ) 
              .filter(t -> t.isSuccess())
              .map( t -> t.get() )
              .limit(10)
              .forEach(System.out::println);










        Try.of( () -> 3 / new Random().nextInt(2) )
            .map( i -> String.valueOf(i) )
            .map( s -> "La valeur est " + s)
            .ifSuccess( s -> log(s) )
            .ifFailure( e -> log(e) );







        String s = Try.of( () -> 3 / new Random().nextInt(2) ).match(
            i -> String.valueOf("VAL: " + i),
            e -> e.getMessage()
        );









        String s2 = Try.of( () -> 3 / new Random().nextInt(2) ).match( new Case<>() {
            public String success(Integer i) { return "VAL: " + String.valueOf(i); }
            public String failure(Exception e) { return e.getMessage(); }
        });








        Try<Integer> result = askInt().flatmap( i -> askInt().map( j -> i / j) );
        String msg = result.match(v -> "Value: " + v, e -> "Exc: " + e);
        log(msg);
        */

    }
}
