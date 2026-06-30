import java.util.ArrayList;
import java.util.Iterator;

public class week1 {
    //메소드 오버로딩
    public static int add(int a, int b){
        return a+b;
    }

    public static int add(int a, int b, int c){
        return a+b+c;
    }
    public static double add(double a, double b){
        return a+b;
    }
    static class Person{
        public void introduce(){
            System.out.println("사람입니다");
        }
    }
    static class Student extends Person{
        public void introduce(){
            System.out.println("학생입니다");
        }
    }
    //제네릭스
    public static <T> void printValue(T value) {
        System.out.println(value);
    }
    //함수형 인터페이스
    @FunctionalInterface
    interface Calculator {
        int calculate(int x, int y);
    }
    //동기화
    static class SharedData{
        public int data = 0;
        synchronized public void increment(){
            data++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        //switch-case
//        int menu = 2;
//        switch (menu) {
//            case 1:
//                System.out.println("start");
//            case 2:
//                System.out.println("menu");
//            default:
//                System.out.println("wrong");
//        }
        //다차원 배열 순회
//        int[][] numbers = new int[][]{
//                {1,2,3,4,5},{6,7,8,9,10}
//        };
//        for (int i=0;i<numbers.length;i++){
//            for (int j=0;j<numbers[i].length;j++){
//                System.out.print(numbers[i][j]+"\t");
//            }
//        }
        //메소드 오버로딩
        System.out.println(add(1,2));
        System.out.println(add(1,2,3));
        System.out.println(add(4.3,5.2));

        Person person = new Person();
        Person student = new Student();
        person.introduce();
        student.introduce();

        int intValue = 3;
        double doubleValue = 3.14;
        String stringValue = "안녕";

        printValue(intValue);
        printValue(doubleValue);
        printValue(stringValue);

        //Wrapper 클래스
        Integer i=1;
        Double d =1.0;
        Character c ='a';

        System.out.println(i.intValue());
        System.out.println(d.intValue());
        System.out.println(c.charValue());

        //Iterator
        ArrayList<String> list = new ArrayList<>();
        list.add("철수");
        list.add("영희");

        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        Calculator add = (x,y) -> x+y;
        int result = add.calculate(2,3);
        System.out.println("2+3="+result);

        SharedData sharedData = new SharedData();
        Thread thread1 = new Thread(() -> {
            for(int j=0; j<1000; j++){
                sharedData.increment();
            }
        });

        Thread thread2 = new Thread(()-> {
            for(int j=0; j<1000; j++){
                sharedData.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("SharedData: "+ sharedData.data);
    }
}
