//Sample 1. Get count of elements greater than 1 using Lambda Expression
intSet.stream().filter(moreThan2Pred).count())

// Get all elements greater than 2, sort them and then push them to a new set, using Lambda Expression
Predicate<Integer> moreThan2Pred = (p) -> (p > 1); 
intSet =  intSet.stream().filter(moreThan2Pred).sorted().collect(Collectors.toSet());

//ind the Average of all collection elements using Lambda Expressions
System.out.println(intSet.stream().collect(Collectors.averagingInt(p->((Integer)p))))

// Convert elements to strings and concatenate them, separated by commas
     String joined = things.stream()
                           .map(Object::toString)
                           .collect(Collectors.joining(", "));
                           
                           
                           
// Compute sum of salaries of employee

     int total = employees.stream()
                          .collect(Collectors.summingInt(Employee::getSalary)));
                          
                          
                          
// Compute sum of salaries by department

     Map<Department, Integer> totalByDept
         = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment,
                                                   Collectors.summingInt(Employee::getSalary)));
                                                   
 //Populate a List using Set elements and Lambda Expression 
List li = intSet.stream().collect(Collectors.toList());


IntStream.range(1, 10)
         .filter(n -> n % 2 == 0)
         .forEach(System.out::println);
//Get the complete Summary of Collection Elements using Lambda Expressions

Set<Integer> intSet = new HashSet<Integer>();
intSet.add(1);
intSet.add(2);
intSet.add(3);
intSet.add(4);
// Use the stream and collectors to Summarize all Integer elements 
System.out.println(intSet.stream().collect(Collectors.summarizingInt(p->((Integer)p)))); // Prints IntSummaryStatistics{count=4, sum=10, min=1, average=2.500000, max=4}

//Set to Map
System.out.println(intSet.stream().collect((Collectors.toMap(p->p,q->q*500)))); // Prints {1=500, 2=1000, 3=1500, 4=2000} 

//Find max rlrmrnt from list
intList.stream().reduce.(Math::max).get()

    // custom sorting
List<User> sorted = list.stream()
        .sorted(Comparator.comparingInt(User::getAge))
        .collect(Collectors.toList());

//in descendng sorting
List<Integer> sortedList = list.stream()
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());

list.stream().sorted().filter(p -> p>25).collect.


List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.parallelStream()
     .filter(name -> name.startsWith("A"))
     .forEach(System.out::println);

// Filtering orders where the sum of prices is less than 120
        List<Order> filteredOrders = orders.stream()
            .filter(order -> order.getItems().stream()
                                  .mapToDouble(Item::getPrice)
                                  .sum() < 120)
            .toList();

                              
 
