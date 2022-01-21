# 使用 Serializable 序列化对象

必须：实现 Serializable 接口
可选：提供 serialVersionUID（可使用 IDE 进行补全），如果不提供，修改对象字段后，使用新对象读取旧数据会抛 InvalidClassException 异常

```java
public class Student implements Serializable {
private static final long serialVersionUID = 3378277299998560800L;
    private String name;
    private int age;
    private Score score;
    ...
}

class Score implements Serializable{
    private static final long serialVersionUID = -2666679671019714018L;
    private int math;
    private int english;
    private int chinese;
    private final String grade;
    ...
}
```
- 对于暂时不需要序列化的字段，使用 transient 关键字标识。

openFileOutput 、openFileInput 获取文件流，然后使用对象流读写序列化后的对象

```java
try {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
    Student student = new Student("测试",10, new Score(90, 80, 70));
    objectOutputStream.writeObject(student);
    objectOutputStream.flush();
    objectOutputStream.close();
    } catch (IOException e) {
    e.printStackTrace();
    }

try {
    ObjectInputStream objectInputStream = new ObjectInputStream(openFileInput(FILE_NAME));
    Student student = (Student)objectInputStream.readObject();
    Log.i("Student", student.getName() + " " + student.getAge() + " " +  student.getScore().getGrade());
} catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```
- MODE_PRIVATE 覆盖模式
- MODE_APPEND 追加模式