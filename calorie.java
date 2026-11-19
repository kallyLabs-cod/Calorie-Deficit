import java.util.Scanner;
import java.util.ArrayList;

public class calorie {

    static class Food{
        String name;
        double calories;
        double grams;

        Food(String name, double calories, double grams){
            this.name = name;
            this.calories = calories;
            this.grams = grams;
        }
    }
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
    
        // variables
        double defiamt;
        int act;
        double age;
        double weight;
        double height;
        String gender;
        // Opening Message
        System.out.println("+========================+");
        System.out.println("         CALCOUNT");
        System.out.println("+========================+");
        //Ask Age
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
        // Get height and weight
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
            sc.nextLine();
            
        //Ask Gender
        do{
            System.out.print("Gender(Male/Female): ");
            gender = sc.nextLine().toLowerCase();

            if(!gender.equalsIgnoreCase("Male") && 
            !gender.equalsIgnoreCase("Female")){
                System.out.println("Invalid Input! Please input between male or female.");
            }
        }while(!gender.equalsIgnoreCase("Male") && 
            !gender.equalsIgnoreCase("Female"));
        System.out.println("Your bmr is " + bmr(gender, height, weight, age) + ".");
       
        // Get Activity Levels
        do{
            System.out.println("\nActivity Levels: ");
            System.out.println("[1] Sedentary");
            System.out.println("[2] Light");
            System.out.println("[3] Moderate");
            System.out.println("[4] Very");
            System.out.print("Activity lvl(1-4): ");
            act = sc.nextInt();

            if(act < 1 || act > 4){
                System.out.println("Invalid Input! Please input a number from 1 to 4.");
            }
        }while(act < 1 || act > 4);
        System.out.printf("Your TDEE is: %.0f%n ", 
            Tdee(height, act));

        //Target 
        do{
            System.out.println("\nDeficit Options");
            System.out.println("[1] 250 (0.25kg/week)");
            System.out.println("[2] 500 (0.5kg/week)");
            System.out.println("[3] 750 (0.75kg/week)");
            System.out.println("[4] 7700 (1kg/week)");
            System.out.print("Choose your Deficit Amount:");
            defiamt = sc.nextInt();

            if(defiamt < 1 || defiamt > 4){
                System.out.println("Invalid Input! Please choose from 1 to 4.");
            }
        }while(defiamt < 1 || defiamt > 4);

        System.out.printf("Your Target Deficit Amount is: %.0f%n", 
            deficitAmount(height, defiamt));
        sc.nextLine();
        //Start of the Loop

        String[] weeks = {"Previous Week", "Current Week"};
        String[] days = {"Monday", "Tuesday", "Wednesdy", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] meals = {"Breakfast", "Lunch", "Dinner"};

        //Food Records
        ArrayList<Food>[][][] records = new ArrayList[2][7][3]; //for the food entries(day, meals)
        double[][][] mealCalories = new double[2][7][3]; //calories per meal
        double[][][] mealGrams = new double[2][7][3]; //grams per meal
        double[][] dailyCalories = new double[2][7]; //daily calories
        boolean[][] dayStarted = new boolean[2][7]; //to track the days progressed in a week
        boolean exit = false; //for the exit option
        double weeklyTotal = 0;

        double weeklyTotalPrevious = 0;
        double weeklyTotalCurrent = 0;
        //Two week loop
        for(int w = 0; w < 2 && !exit;w++){
            //Weekly Loop
            for(int i = 0; i < 7 && !exit; i++){
            dayStarted[w][i] = true;

            System.out.println("\n======================================");
            System.out.println("                 " + days[i].toUpperCase());
            System.out.println("======================================");

            //The 3 meals loop
            for(int j = 0; j < 3 && !exit; j++){
                records[w][i][j] = new ArrayList<>();

                System.out.println("---------------" + meals[j] + "--------------");
                boolean mealFinished = false;

                //Time to input the food
                while(!mealFinished && !exit){
                    System.out.println("\nEnter Food Information");
                    System.out.print("Food Name: ");
                    String foodName = sc.nextLine().trim();

                    while(foodName.isEmpty()){
                        System.out.println("Please enter the food name.");
                        System.out.print("Food Name: ");
                        foodName = sc.nextLine().trim();
                    }

                    System.out.print("Calories(kcal): ");
                    double calories = Double.parseDouble(sc.nextLine());
                    System.out.print("Weight(grams): ");
                    double grams = Double.parseDouble(sc.nextLine());

                    Food food = new Food(foodName, calories, grams);

                    records[w][i][j].add(food);

                    mealCalories[w][i][j] += calories;
                    dailyCalories[w][i] += calories;
                    System.out.println("\nFood Recorded."); //to test
                    System.out.printf("Current Meal Calories: %.2fkcal\n", mealCalories[w][i][j]); //current meal calories

                    //Menu time
                    boolean validChoice = false;

                    while(!validChoice && !exit){
                        System.out.println("\n========== MENU ==========");
                        System.out.println("[1] Add Another Food");
                        System.out.println("[2] Continue to Next Meal");
                        System.out.println("[3] Check Calorie Progress");
                        System.out.println("[4] Exit System");

                        int choice = inputInt(sc, "Choose your Option: ", 1, 4);

                        switch(choice) {

                            case 1:
                                //Add another food
                                validChoice = true;
                                break;
                            case 2:
                                // next meal
                                validChoice = true;
                                mealFinished = true;
                                break;
                            case 3:
                                //check calories
                                checkProgress(days[i], dailyCalories[w][i], deficitAmount(Tdee(bmr(gender, height, weight, age), act), defiamt));
                                break;
                            case 4:
                                //Exit
                                exit = true;
                                break;
                        }
                    }

                }
                    if(!exit){
                        System.out.println("\n==============SUMMARY============");
                        System.out.printf("Total Grams: %.2f grams%n\n", mealGrams[w][i][j]);
                        System.out.printf("Total Meal Calories: %.2fkcal%n\n", mealCalories[w][i][j]);
                        checkProgress(days[i], dailyCalories[w][i], deficitAmount(Tdee(bmr(gender, height, weight, age), act), defiamt));
                    }
                }
            }
        }
        System.out.println("\n==========WEEKLY REPORT==========");
        int recordedDays = 0;
        int exceededDays = 0;
        
        for(int w = 0; w < 2;w++){
            for(int i = 0; i < 7;i++){
                if(dayStarted[w][i]){
                    recordedDays++;

                    System.out.println("\n" + days[i]);
                    for(int j = 0; j < 3;j++){
                        if(records[w][i][j] == null){
                            continue;
                    }
                    System.out.println("\n" + meals[j]);
                    for(Food food : records[w][i][j]){
                        System.out.printf(" %s | %.2f grams | %.2f kcal ",
                            food.name,
                            food.grams,
                            food.calories
                        );
                    }
                    System.out.printf("Meal Total: %.2fkcal\n", mealCalories[w][i][j]);
                    }
                    System.out.printf("\nDaily Total: %.2fkcal\n", dailyCalories[w][i]);

                    if(dailyCalories[w][i] > deficitAmount(Tdee(bmr(gender, height, weight, age), act), defiamt)){
                        System.out.println("Status: TARGET EXCEEDED");
                        exceededDays++;
                    }else{
                        System.out.println("Status: WITHIN TARGET");
                    }
                    weeklyTotal += dailyCalories[w][i];

                    if(recordedDays > 0){
                    System.out.printf("Average Calorie Total: %.2f kcal\n", weeklyTotal/recordedDays);
                    }

                    System.out.println("Days Recorded: " + recordedDays + "/7");
                    System.out.println("Days Exceeding Target: " + exceededDays);
                    System.out.println("============================");
                }
            }
        }
        //Final Stuff
        int previousDays = 0;
        int currentDays = 0;

        for (int i = 0; i < 7; i++) {

            if (dayStarted[0][i]) {
                weeklyTotalPrevious += dailyCalories[0][i];
                previousDays++;
            }

            if (dayStarted[1][i]) {
                weeklyTotalCurrent += dailyCalories[1][i];
                currentDays++;
            }
        }

        System.out.println("\n===========WEEKLY COMPARISON===========");

        System.out.printf("Daily Target Calorie: %.2f kcal\n", deficitAmount(Tdee(bmr(gender, height, weight, age), act), defiamt));
        System.out.printf("Weekly Calories Consumed: %.2f kcal\n", weeklyTotal);

        System.out.printf(weeks[0], " Total: %.2fkcal%n\n", weeklyTotalPrevious);
        System.out.printf(weeks[1], " Total: %.2fkcal%n\n", weeklyTotalCurrent);

        double difference = weeklyTotalCurrent - weeklyTotalPrevious;

        System.out.printf(
            "Weekly Difference: %.2f kcal%n",
            difference
        );

        if (difference > 0) {
            System.out.println("Calorie intake increased this week.");
        } else if (difference < 0) {
            System.out.println("Calorie intake decreased this week.");
        } else {
            System.out.println("Calorie intake remained the same.");
        }

        //Percentage Change
        if (weeklyTotalPrevious > 0) {

            double percentageChange =
                (difference / weeklyTotalPrevious) * 100;

            System.out.printf(
                "Percentage Change: %.2f%%%n",
                percentageChange
            );

        } else {
            System.out.println("Percentage Change: Cannot calculate.");
        }

    }

    static int inputInt(Scanner sc, String message, int min, int max) {

        while (true) {
            System.out.print(message);

            try {
                int num = Integer.parseInt(sc.nextLine());

                if (num >= min && num <= max) {
                    return num;
                }

                System.out.println("Choose between " + min + " and " + max + ".");

            } catch (NumberFormatException e) {
                System.out.println("Numbers only!");
            }
        }
    }

    static void checkProgress(String day, double consumed, double amount){
        System.out.println("\n===========CALORIE PROGRESS===========");
        System.out.println("Day: " + day);

        System.out.printf("Target Calories: %.2fkcal\n", amount);
        System.out.printf("Consumed Calories: %.2fkcal\n", consumed);

        if(consumed > amount){
            System.out.printf("Exceeded by: %.2fkcal\n",
                consumed - amount
            );
            System.out.println("Status: CALORIE TARGET EXCEEDED");
        }else{
            System.out.printf("Remaining Calories: %.2fkcal\n",
                amount - consumed
            );
            System.out.println("Status: WITHIN CALORIE TARGET");
        }
    };

    static double deficitAmount(double Tdee, double defiamt){
        double amount;

        amount = Tdee - defiamt;
        return amount;
    };
    static double Tdee(double bmr, int act){
        double tdee;

        switch(act) {
            case 1 -> tdee = bmr * 1.2;
            case 2 -> tdee = bmr * 1.375;
            case 3 -> tdee = bmr * 1.55;
            case 4 -> tdee = bmr * 1.725;
            default -> tdee = 0;
        };

        return tdee;
    };
    static double bmr(String gender, double height, double weight, double age){

        if(gender.equalsIgnoreCase("male")){
            return 10 * weight + 6.25 * height - 5 * age + 5;
           
        }
        
        if(gender.equalsIgnoreCase("female")){
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
        return 0;
    };
}
