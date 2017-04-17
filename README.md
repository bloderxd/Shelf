# Shelf
Shelf is an easy way to implement repository pattern.

## But how?
Add ```@Locker``` on your repositories and create a fixture and production implementations for them. 
<br/> in fixture implementation add the annotation ```@Fake``` and in production implementation add the annotation 
<br/> ```@Prod```. Build the project and be happy! :smile:

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
Shelf.forTest().withProd().test();
```

##### Fake call
```java
Shelf.forTest().withFake().test();
```
## Import

First, import apt classpath in your project gradle file
```groovy
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8' 
```

Then import shelf deppendencies in your app gradle file
```groovy 
apply plugin: 'com.neenbedankt.android-apt'

dependencies {
    compile 'com.bloder.shelf:core:1.0'
    apt 'com.bloder.shelf:compiler:1.0'
}
```
