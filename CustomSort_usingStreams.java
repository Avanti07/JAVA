import java.io.*;
import java.util.*;

class Person {

	private String name;
	private Integer age;

	public Person(String name, int age)
	{
		this.name = name;
		this.age = age;
	}
	public Integer getAge() { return age; }
	public String getName() { return name; }
}

public class GFG {
	public static int compareByName(Person a, Person b)
	{
		return a.getName().compareTo(b.getName());
	}

	public static int compareByAge(Person a, Person b)
	{
		return a.getAge().compareTo(b.getAge());
	}

	public static void main(String[] args)
	{

		List<Person> personList = new ArrayList<>();
		personList.add(new Person("vicky", 24));
		personList.add(new Person("poonam", 25));
		personList.add(new Person("sachin", 19));

		Collections.sort(personList, GFG::compareByName);
    
		personList.stream()
			.map(x -> x.getName())
			.forEach(System.out::println);

		// Now using static method reference
		// to sort array by age
		Collections.sort(personList, GFG::compareByAge);

		// Display message only
		System.out.println("Sort by age :");

		// Using streams over above object of Person type
		personList.stream()
			.map(x -> x.getName())
			.forEach(System.out::println);
	}
}
