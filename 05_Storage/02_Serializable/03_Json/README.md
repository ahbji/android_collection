# 解析普通对象

```json
{
  "age": 10,
  "name": "TEST"
}
```

```java
Person person1 = new Person("TEST", 10); String jsonPerson = gson.toJson(person1);
String jsonPersonString = "{\"age\":10,\"name\":\"TEST\"}";
Person person2 = gson.fromJson(jsonPersonString, Person.class);
Log.i("PERSON", person2.name + " " + person2.age);
```

# 解析嵌套对象

```json
{
  "age": 10,
  "name": "TEST",
  "score": {
    "chinese": 80,
    "english": 85,
    "grade": "B",
    "math": 90
  }
}
```

```java
Student student1=new Student("TEST",10,new Score(90,85,80));
String jsonStudent=gson.toJson(student1);
Student student2=gson.fromJson(jsonStudent,Student.class);
Log.i("Student",student2.getName()+" "+student2.getAge()+" "+student2.getScore().getGrade());
```

# 解析数组

```json
[
  {
    "age": 10,
    "student_name": "TEST",
    "score": {
      "chinese": 80,
      "english": 85,
      "grade": "B",
      "math": 90
    }
  },
  {
    "age": 10,
    "student_name": "TEST",
    "score": {
      "chinese": 80,
      "english": 85,
      "grade": "B",
      "math": 90
    }
  }
]
```

```java
String jsonPerson=gson.toJson(new Person("TEST",10));
Person person=gson.fromJson(jsonPersonString,Person.class);
```

```java
String jsonStudent=gson.toJson(new Student("TEST",10,new Score(90,85,80)));
Student student=gson.fromJson(jsonStudent,Student.class);
```

```java
String jsonStudents=gson.toJson(new Student[]{student1,student2});
Student[]students=gson.fromJson(jsonStudents,Student[].class);
```

# 解析List:

```json
[
  {
    "age": 10,
    "student_name": "TEST",
    "score": {
      "chinese": 80,
      "english": 85,
      "grade": "B",
      "math": 90
    }
  },
  {
    "age": 10,
    "student_name": "TEST",
    "score": {
      "chinese": 80,
      "english": 85,
      "grade": "B",
      "math": 90
    }
  }
]
```

```java
String jsonStudents=gson.toJson(Arrays.asList(students));
Type typeStudents=new TypeToken<List<Student>>(){}.getType();
List<Student> list=gson.fromJson(jsonStudents),typeStudents);
```

# 解析Set:

```json
[
  "a",
  "b",
  "c"
]
```

```java
Set<String> set1=new HashSet<>();
set1.add("a");
set1.add("b");
set1.add("c");
Log.i("Set",gson.toJson(set1));
Type typeSet=new TypeToken<Set<String>>(){}.getType();
Set<String> set2=gson.fromJson(gson.toJson(set1),typeSet);
```

# 解析Map:

```json
{
  "1": "abc",
  "2": "aaa",
  "3": "bbb"
}
```

```java
Map<Integer, String> hm1=new HashMap<Integer, String>();
hm1.put(1,"abc");
hm1.put(2,"aaa");
hm1.put(3,"bbb");
Log.i("Map",gson.toJson(hm1));
Type typeMap=new TypeToken<Map<Integer, String>>(){}.getType();
Map<Integer, String> hm2=gson.fromJson(gson.toJson(hm1),typeMap);
```
