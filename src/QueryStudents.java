import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QueryStudents {
    public static void main(String[] args) throws IOException {
        List<Student> studentDatabase = readAllStudents();

//        printStudentsByGroup(studentDatabase);//01
//        printStudentsByFnameLname(studentDatabase);//02
//        printStudentsByAge(studentDatabase);//03
//        printSortedStudents(studentDatabase);//04
//        printStudentsByMail(studentDatabase);//05
//        printStudentsByPhone(studentDatabase);//06
//        printExcelentStudents(studentDatabase);//07
//        printWeakStudents(studentDatabase);//08
        printEnrolledStudents(studentDatabase);//09
    }

    private static void printStudentsByGroup(List<Student> studentDatabase) {
        LinkedList<Student> filterGroup = studentDatabase.stream()
                .filter(student -> student.getGroup() == 2)
                .collect(Collectors.toCollection(LinkedList::new));
        filterGroup.sort(Comparator.comparing(Student::getFirstName));
        for (Student student : filterGroup) {
            System.out.printf("%s %s %d%n",student.getFirstName(),student.getLastName(),student.getGroup());
        }
    }

    private static void printStudentsByFnameLname(List<Student> studentDatabase) {
        ArrayList<Student> filterFnameBeforeLname = new ArrayList<>(studentDatabase);
        filterFnameBeforeLname.sort(Comparator.comparing(Student::getFirstName).thenComparing(Student::getLastName));
        filterFnameBeforeLname.forEach(student -> System.out.printf("%s %s%n",student.getFirstName(),student.getLastName()));
    }

    private static void printStudentsByAge(List<Student> studentDatabase) {
        LinkedList<Student> filterAge = studentDatabase.stream()
                .filter(student -> student.getAge() >= 18 && student.getAge() <= 24)
                .collect(Collectors.toCollection(LinkedList::new));
        for (Student student : filterAge) {
            System.out.printf("%s %s %d%n",student.getFirstName(),student.getLastName(),student.getAge());
        }
    }

    private static void printSortedStudents(List<Student> studentDatabase) {
        ArrayList<Student> sortedStudents = new ArrayList<>(studentDatabase);
        sortedStudents.sort(Comparator.comparing(Student::getLastName).thenComparing((a, b)->b.getFirstName().compareTo(a.getFirstName())));
        sortedStudents.forEach(student -> System.out.printf("%s %s%n",student.getFirstName(),student.getLastName())); // different syntax same code
    }

    private static void printStudentsByMail(List<Student> studentDatabase) {
        LinkedList<Student> gmailStudents = studentDatabase.stream()
                .filter(student -> student.getEmail().endsWith("@gmail.com"))
                .collect(Collectors.toCollection(LinkedList::new));
        for (Student student : gmailStudents) {
            System.out.printf("%s %s %s%n",student.getFirstName(),student.getLastName(),student.getEmail());
        }
    }

    private static void printStudentsByPhone(List<Student> studentDatabase) {
        LinkedList<Student> sofiaStudents = studentDatabase.stream()
                .filter(student -> (student.getPhone().startsWith("02") || student.getPhone().startsWith("+3592")))
                .collect(Collectors.toCollection(LinkedList::new));
        for (Student student : sofiaStudents) {
            System.out.printf("%s %s %s%n",student.getFirstName(),student.getLastName(),student.getPhone());
        }
    }

    private static void printExcelentStudents(List<Student> studentDatabase) {
        LinkedList<Student> excelentStudents = studentDatabase.stream()
                .filter(student -> student.grades.contains(6))
                .collect(Collectors.toCollection(LinkedList::new));
        for (Student student : excelentStudents) {
            System.out.printf("%s %s %s%n",student.getFirstName(),student.getLastName(),student.getGrades().toString().replaceAll("[\\[,\\]]",""));
        }
    }

    private static void printWeakStudents(List<Student> studentDatabase) {
        Predicate<ArrayList<Integer>> twoWeakGrades = integers -> {
            int count = 2;
            for (int grade : integers) {
                if (grade <= 3){
                    count--;
                }
            }
            return count <= 0;
        };
        LinkedList<Student> weakStudents = studentDatabase.stream()
                .filter(student -> twoWeakGrades.test(student.grades))
                .collect(Collectors.toCollection(LinkedList::new));

        for (Student student : weakStudents) {
            System.out.printf("%s %s %s%n",student.getFirstName(),student.getLastName(),student.getGrades().toString().replaceAll("[\\[,\\]]",""));
        }
    }

    private static void printEnrolledStudents(List<Student> studentDatabase) {
        Map<String,ArrayList<Student>> enrollmentYear = new TreeMap<>();
        for (Student student:studentDatabase) {
            String year = "20" + student.getFacNum().substring(4);
            if (!enrollmentYear.containsKey(year)){
                enrollmentYear.put(year,new ArrayList<>());
            }
            enrollmentYear.get(year).add(student);
        }
        for (String key : enrollmentYear.keySet()) {
            System.out.printf("%s:%n",key);
            for (Student student : enrollmentYear.get(key)) {
                System.out.printf("-- %s %s%n",student.getFirstName(),student.getLastName());
            }
        }
    }

    private static ArrayList<Student> readAllStudents() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Student> students = new ArrayList<>();
        while (true){
            String[] rowInput = reader.readLine().split("\\s+");
            if ("END".equals(rowInput[0])){
                break;
            }
            String facNum = rowInput[0];
            String firstName = rowInput[1];
            String lastName = rowInput[2];
            String email = rowInput[3];
            int age = Integer.parseInt(rowInput[4]);
            int group = Integer.parseInt(rowInput[5]);
            ArrayList<Integer> grades = new ArrayList<>();
            grades.add(Integer.parseInt(rowInput[6]));
            grades.add(Integer.parseInt(rowInput[7]));
            grades.add(Integer.parseInt(rowInput[8]));
            grades.add(Integer.parseInt(rowInput[9]));
            String phone  = rowInput[10];
            Student current = new Student(facNum, firstName, lastName, email, age, group, grades, phone);
            students.add(current);
        }
        return students;
    }
}
