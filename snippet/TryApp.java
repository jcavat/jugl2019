import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Stream;

import jdk.jshell.execution.FailOverExecutionControlProvider;

@FunctionalInterface
interface ThrowableSupplier<T> {
    T get() throws Exception;
}

interface Case<T> {
    void success(T t);
    void failure(Exception e);
}

interface Try<T> {
    class Success<T> implements Try<T> {
        private T t;
        private Success(T t) { this.t = t; }
        public boolean isSuccess() { return true; }
        public boolean isFailure() { return false; }
        public T get() { return this.t; }
        public <R> Try<R> map(Function<T,R> f) {
            return Try.of( () -> f.apply(t) );
        }
        public <R> Try<R> flatmap(Function<T,Try<R>> f) {
            return f.apply(t);
        }
        public Exception getException() { throw new NoSuchElementException(); }
        public void match(Case<T> c) {
            c.success(t);
        }
    }
    class Failure<T> implements Try<T> {
        private Exception e;
        private Failure(Exception e) { this.e = e; }
        public boolean isSuccess() { return false; }
        public boolean isFailure() { return true; }
        public T get() { throw new NoSuchElementException(); }
        public <R> Try<R> map(Function<T,R> f) {
            return new Failure<>(e);
        }
        public <R> Try<R> flatmap(Function<T,Try<R>> f) {
            return new Failure<>(e);
        }
        public Exception getException() { return this.e; }
        public void match(Case<T> c) {
            c.failure(e);
        }
    }
    boolean isSuccess();
    boolean isFailure();
    T get();
    Exception getException();
    void match(Case<T> c);
    <R> Try<R> map(Function<T,R> f);
    <R> Try<R> flatmap(Function<T,Try<R>> f);
    default void match(Consumer<T> c1, Consumer<Exception> c2) {
        if( isSuccess() ) {
            c1.accept( get() );
        } else {
            c2.accept( getException() );
        }
    }

    static <T> Try<T> of(ThrowableSupplier<T> t) {
        try {
            return new Success<>( t.get() );
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }
}

public class TryApp {

    public static void log(Object o) { System.out.println(o.toString()); }
    public static int nextThrowingInt() throws Exception {
        int i = new Random().nextInt(10);
        if( i <= 5 ) { return i; } 
        throw new Exception();
    }
    public static int askThrowingInt() { return Integer.valueOf(System.console().readLine()); }

    public static Try<Integer> ask() {
        return Try.of( () -> askThrowingInt() );
    }

    public static void main(String[] args) {

        log("Hello JUGL! ");

        Try<Integer> t1 = Try.of( () -> nextThrowingInt() );
        /*
        if( t1.isSuccess() ) {
            log("Exists: " + t1.get() );
        } else {
            log("Oups: " + t1.getException() );
        }



        Stream.generate( () -> Try.of( () -> nextThrowingInt()) ) // Stream<Try<Integer>>
              .filter(t -> t.isSuccess())
              .map( t -> t.get() )
              .limit(10)
              .forEach(System.out::println);

              */

        t1.match( new Case<>() {
            public void success(Integer i) { log("Exists: " + i); }
            public void failure(Exception e) { log("OUPS: " + e); }
        });

        t1.match( 
            i -> log("Exists: " + i ),
            e -> log("Oups: " + e )
        );


        Try<String> t2 = t1.map( i -> i.toString() );

        Try<Integer> t3 = ask().flatmap( i -> ask().map( j -> i / j ));

        t3.match( 
            i -> log("Exists: " + i ),
            e -> log("Oups: " + e )
        );

















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
