import java.util.Scanner;
public class calcount {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        //Variables 
        String gender;
        double age;
        double weight;
        double height;
        
        System.out.println("+========================+");
        System.out.println("|         CALCOUNT       |");
        System.out.println("+========================+");

        do{
            System.out.print("Enter your age: ");

            if(sc.hasNextInt()){
                age = sc.nextInt();
                break;
            }else{
                System.out.println("Invalid Input! Whole Numbers Only!");
                sc.next();
            }
        }while(true);

        do{
            System.out.print("Enter your Weight(kg): ");

            if(sc.hasNextInt()){
                weight = sc.nextDouble();
                break;
            } else{
                System.out.println("Invalid Input! Numbers Only!");
                sc.next();
            }
        }while(true);

        do{
            System.out.print("Enter your Height(cm): ");

            if(sc.hasNextDouble()){
                height = sc.nextDouble();
                break;
            }else{
                System.out.println("Invalid Input! Numbers Only!");
                sc.next();
            }
        }while(true);
    
        do{
            System.out.print("Gender: ");
            gender = sc.nextLine().toLowerCase();

            if(!gender.equalsIgnoreCase("Male") && 
            !gender.equalsIgnoreCase("Female")){
                System.out.println("Invalid Input! Male or Female Only!");
            }
        }while(!gender.equalsIgnoreCase("Male") && 
            !gender.equalsIgnoreCase("Female"));
        
        
    }

    static double bmr(double height, double weight, double age, String Gender){
        double m;
        double f;

        if(Gender == "male"){
            m = 10 * weight + 6.25 * height - 5 * age + 5;
            return m;
        }

        if(Gender == "female"){
            f = 10 * weight + 6.25 * height - 5 * age - 161;
            return f;
        }
    }
    
}
