# Shelf
Shelf is an easy way to implement repository pattern.

## But how?
Add ```@Locker``` on your repositories and create a fixture and production implementations for them. 
<br/> in fixture implementation add the annotation ```@Fake``` and in production implementation add the annotation 
<br/> ```@Prod```. Be happy! :smile:

## Example

#### Locker
```java
@Locker
public interface Test {

    void test();
}
```
#### Fake
```java
@Fake
public class TestFake implements Test {

    @Override
    public void test() {

    }
}
```
#### Prod
```java
@Prod
public class TestProd implements Test {

    @Override
    public void test() {

    }
}
```
<br/>
You can call your repository with this:

##### Prod call
```java
Shelf.forTest().inProdEnvironment().test();
```

##### Fake call
```java
Shelf.forTest().inFakeEnvironment().test();
```
