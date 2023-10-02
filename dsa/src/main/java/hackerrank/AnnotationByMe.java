package hackerrank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 *
 * https://www.hackerrank.com/challenges/java-annotations/problem
 *
 * Java annotation can be used to define the metadata of a Java class or class element. We can use Java annotation at the compile
 * time to instruct the compiler about the build process. Annotation is also used at runtime to get insight into the properties of
 * class elements.
 * <p>
 * Java annotation can be added to an element in the following way:
 *
 * @Entity Class DemoClass{
 * <p>
 * }
 * We can also set a value to the annotation member. For example:
 * @Entity(EntityName="DemoClass") Class DemoClass{
 * <p>
 * }
 * In Java, there are several built-in annotations. You can also define your own annotations in the following way:
 * @Target(ElementType.METHOD)
 * @Retention(RetentionPolicy.RUNTIME)
 * @interface hackerrank.FamilyBudget {
 * String userRole() default "GUEST";
 * }
 * Here, we define an annotation , where  is the only member in that custom annotation. The  takes only  type values, and the
 * default is "GUEST". If we do not define the value for this annotation member, then it takes the default. By using @Target,
 * we can specify where our annotation can be used. For example, the  annotation can only be used with the method in a
 * class. @Retention defines whether the annotation is available at runtime. To learn more about Java annotation, you can read the
 * tutorial and oracle docs.
 * <p>
 * <p>
 * Here, we partially define an annotation,  and a class, . In this problem, we give the user role and the amount of
 * money that a user spends as inputs. Based on the user role, you have to call the appropriate method in the  class. If
 * the amount of money spent is over the budget limit for that user role, it prints Budget Limit Over.
 * <p>
 * Your task is to complete the  annotation and the  class so that the  class works perfectly with the defined constraints.
 * <p>
 * Note: You must complete the  incomplete lines in the editor. You are not allowed to change, delete or modify any other
 * lines. To restore the original code, click on the top-left button on the editor and create a new buffer.
 * <p>
 * Input Format
 * <p>
 * The first line of input contains an integer  representing the total number of test cases. Each test case contains a
 * string and an integer separated by a space on a single line in the following format:
 * <p>
 * UserRole MoneySpend
 * Constraints
 * <p>
 * <p>
 * <p>
 * <p>
 * Name contains only lowercase English letters.
 * <p>
 * Output Format
 * <p>
 * Based on the user role and budget outputs, output the contents of the certain method. If the amount of money spent is
 * over the budget limit, then output Budget Limit Over.
 * <p>
 * <p>
 * Sample Input
 * <p>
 * 3
 * SENIOR 75
 * JUNIOR 45
 * SENIOR 40
 * Sample Output
 * <p>
 * Senior Member
 * Spend: 75
 * Budget Left: 25
 * Junior Member
 * Spend: 45
 * Budget Left: 5
 * Senior Member
 * Spend: 40
 * Budget Left: 60
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FamilyBudget {
    String userRole() default "GUEST";

    int budget() default 0;
}

class FamilyMember {
    @FamilyBudget(userRole = "SENIOR", budget = 100)
    public void seniorMember(int budget, int moneySpend) {
        System.out.println("Senior Member");
        System.out.println("Spend: " + moneySpend);
        System.out.println("Budget Left: " + (budget - moneySpend));
    }

    @FamilyBudget(userRole = "JUNIOR", budget = 50)
    public void juniorUser(int budget, int moneySpend) {
        System.out.println("Junior Member");
        System.out.println("Spend: " + moneySpend);
        System.out.println("Budget Left: " + (budget - moneySpend));
    }
}

public class AnnotationByMe {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testCases = Integer.parseInt(in.nextLine());
        while (testCases > 0) {
            String role = in.next();
            int spend = in.nextInt();
            try {
                Class annotatedClass = FamilyMember.class;
                Method[] methods = annotatedClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(FamilyBudget.class)) {
                        FamilyBudget family = method
                                .getAnnotation(FamilyBudget.class);
                        String userRole = family.userRole();
                        int budgetLimit = family.budget();
                        if (userRole.equals(role)) {
                            if (budgetLimit >= spend) {
                                method.invoke(FamilyMember.class.newInstance(),
                                        budgetLimit, spend);
                            } else {
                                System.out.println("Budget Limit Over");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            testCases--;
        }
    }
}



