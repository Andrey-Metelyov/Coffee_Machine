package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private static final Scanner scanner = new Scanner(System.in);

    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    enum COFFEE {ESPRESSO, LATTE, CAPPUCCINO}

    CoffeeMachine() {
        water = 400;
        milk = 540;
        beans = 120;
        cups = 9;
        money = 550;
    }

    void status() {
        System.out.printf("\nThe coffee machine has:\n" +
                "%d of water\n" +
                "%d of milk\n" +
                "%d of coffee beans\n" +
                "%d of disposable cups\n" +
                (money > 0 ? "$" : "") +
                "%d of money\n\n", water, milk, beans, cups, money);
    }

    void make(COFFEE type) {
        if (canMake(type)) {
            System.out.println("I have enough resources, making you a coffee!\n");
        } else {
            return;
        }
        switch (type) {
            case LATTE:
                water -= 350;
                milk -= 75;
                beans -= 20;
                money += 7;
                break;
            case ESPRESSO:
                water -= 250;
                beans -= 16;
                money += 4;
                break;
            case CAPPUCCINO:
                water -= 200;
                milk -= 100;
                beans -= 12;
                money += 6;
                break;
        }
        cups--;
    }

    private boolean canMake(COFFEE type) {
        int needWater = 0;
        int needMilk = 0;
        int needBeans = 0;
        switch (type) {
            case LATTE:
                needWater = 350;
                needMilk = 75;
                needBeans = 20;
                break;
            case ESPRESSO:
                needWater = 250;
                needBeans = 16;
                break;
            case CAPPUCCINO:
                needWater = 200;
                needMilk = 100;
                needBeans = 12;
                break;
        }
        if (cups == 0) {
            System.out.println("Sorry, not enough cups!\n");
            return false;
        }
        if (water < needWater) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        }
        if (milk < needMilk) {
            System.out.println("Sorry, not enough milk!\n");
            return false;
        }
        if (beans < needBeans) {
            System.out.println("Sorry, not enough beans!\n");
            return false;
        }
        return true;
    }

    void fill(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
    }

    int take() {
        int result = money;
        money = 0;
        return result;
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        menu(coffeeMachine);
    }

    private static void menu(CoffeeMachine coffeeMachine) {
        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.nextLine();
            switch (action) {
                case "buy":
                    buy(coffeeMachine);
                    break;
                case "fill":
                    fill(coffeeMachine);
                    break;
                case "take":
                    System.out.println("I gave you $" + coffeeMachine.take() + "\n");
                    break;
                case "remaining":
                    coffeeMachine.status();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("error");
            }
        }
    }

    private static void fill(CoffeeMachine coffeeMachine) {
        System.out.println("\nWrite how many ml of water do you want to add:");
        int water = Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many ml of milk do you want to add:");
        int milk = Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many grams of coffee beans do you want to add:");
        int beans = Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        int cups = Integer.parseInt(scanner.nextLine());
        coffeeMachine.fill(water, milk, beans, cups);
    }

    private static void buy(CoffeeMachine coffeeMachine) {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String answer = scanner.nextLine();
        switch (answer) {
            case "1":
                coffeeMachine.make(COFFEE.ESPRESSO);
                break;
            case "2":
                coffeeMachine.make(COFFEE.LATTE);
                break;
            case "3":
                coffeeMachine.make(COFFEE.CAPPUCCINO);
                break;
            case "back":
                break;
            default:
                System.out.println("error");
        }
    }

    private int availableCups(COFFEE type) {
        switch (type) {
            case CAPPUCCINO:
                return Math.min(Math.min(water / 200, milk / 100), beans / 12);
            case ESPRESSO:
                return Math.min(water / 250, beans / 16);
            case LATTE:
                return Math.min(Math.min(water / 350, milk / 75), beans / 20);
        }
        return 0;
    }

}
