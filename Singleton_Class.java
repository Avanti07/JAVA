public class SimpleSingleton {
	private static SimpleSingleton singleInstance =  new SimpleSingleton();

	//Marking default constructor private
	//to avoid direct instantiation.
	private SimpleSingleton() {
	}

	//Get instance for class SimpleSingleton
	public static SimpleSingleton getInstance() {

		return singleInstance;
	}
}


//other example
public enum SimpleSingleton {
	INSTANCE;
	public void doSomething() {
	}
}

//Call the method from Singleton:
SimpleSingleton.INSTANCE.doSomething();
