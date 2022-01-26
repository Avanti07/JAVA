Final vs Immutable
“final” is a keyword used to create weak immutable classes.
If a class is final, it cannot be extended. Hence its methods cannot be overridden as well (since there cannot be a subclass to override)
If it’s variables are also final, it cannot be set using setter methods.
This is an immutable class but the value of the final variable needs to be hard coded (as it cannot be defined in setters)
To avoid this, we can declare the final variable and initialize using the constructor.


Singleton vs Immutable
An immutable object is initialized by its constructor only, while a singleton is instantiated by a static method.
• A set of functions (or static methods) which manipulate some shared mutable state constitute a singleton.
• If a singleton A provides a reference to mutable object B, then B is a singleton as well.
• This implies that every mutable member of a singleton collection is itself a singleton.
• A transitively immutable object is NOT a singleton even if globally accessible. It is a constant.
• A stand-alone function that does not access any singletons is NOT itself a singleton, assuming that code is immutable.



Singleton vs Synchronized
1) Singleton restricts at class level while “synchronized” restricts access at method level only
2) So, “synchronized” will have better performance than “singleton” since in case of synchronized
a. ThreadB can access the non-synchronized methods of ClassA even while the synchronized method of ClassA is being accessed by ThreadA
b. But of course, ThreadB cannot access any of the synchronized methods until ThreadA hasn’t completed its execution on any of the synchronized methods
3) Though you can make all methods as synchronized, still these are the other problems that creeps up
a. Variables of the class needs to be synchronized explicitly using either synchronized blocks or by using the keyword “volatile”
b. Constructors cannot be synchronized; To solve this problem we have to go for singleton only (or) keep the constructor as private
  
  
  singleton 
public static instance variables;
private constructor(){}
static block to initialize instance

Immutable
class should be final
variables should be private so that no one can access
variables should be final so that cannot be modified
parm ctr to initialize the fields so that they cannot be modified using object ref.
only getters no setters

1. The state of object doesn't change after creation
2. Since object state doesn't change, the hashcode assigned by JVM to object also remains the same
3. Hence hashcode for immutable objects are cached as and when they are created, as there is no need to calculate it every time we call hashcode method of immutable object. You can use static factory methods to provide methods like valueOf(), which can return an existing Immutable object from cache, instead of creating a new one. This saves quite a lot of time.
4. Hence immutable objects are used as keys in HashMaps for faster retrieval (implemented via hashing mechanism wherein there is no need for hashcode computation)
5. Since immutable object's state doesn't change, they can be used in concurrent programming without any need for synchronization. This also improves the performance to a larger extent.

Eg of Immutable object
1. String
2. Wrapper classes like Integer, Long

  
