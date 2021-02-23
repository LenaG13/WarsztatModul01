package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
/// ERROR
    static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadFileToTab(FILE_NAME);
        showMenu();
        menu();
    }

    // method is to display the options available in the program for execution
    public static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit\n");
    }

    //read the data from a given file and then put them into an array
    public static String[][] loadFileToTab(String fileName) {
        String[] line = new String[0];
        int countLine = 0;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                line = Arrays.copyOf(line, line.length + 1);
                line[line.length - 1] = scanner.nextLine();
                countLine++;
            }
            //System.out.println(Arrays.toString(line));
            //System.out.println(countLine);
        } catch (FileNotFoundException e) {
            System.out.println("brak pliku");
        }
        String[][] tasks = new String[countLine][3];
        for (int i = 0; i < tasks.length; i++) {
            String[] tekst = line[i].split(",");
            for (int j = 0; j < tekst.length; j++) {
                tasks[i][j] = tekst[j];
            }
        }
        //System.out.println(Arrays.deepToString(tasks));
        return tasks;
    }

    // Download the selected action
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();

        switch (option) {
            case "exit":
                saveTabToFile(FILE_NAME, tasks);
                System.out.println(ConsoleColors.RED + "Good bye!!!");
                System.exit(0);
                break;
            case "add":
                addTask();
                break;
            case "list":
                printTab(tasks);
                break;
            case "remove":
                removeTask(tasks, getNumber());
                System.out.println("Value was delete.");
                break;
            // other options
            default:
                System.out.println("Please select a correct option.");
                break;
        }
        showMenu();
    }

    private static void saveTabToFile(String fileName, String[][] tasksArr) {
        try {
            FileWriter fWriter = new FileWriter(fileName, false);
            for (int i = 0; i < tasksArr.length; i++) {
                if (i < tasksArr.length - 1) {
                    fWriter.append(Arrays.toString(tasksArr[i]).substring(1, Arrays.toString(tasksArr[i]).length() - 1) + "\n");
                } else {
                    fWriter.append(Arrays.toString(tasksArr[i]).substring(1, Arrays.toString(tasksArr[i]).length() - 1));
                }
            }
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getNumber() {
        System.out.println("Please select number to remove:");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Incorrect argument passed");
        }
        System.out.println(option);
        return 0;
    }

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task date: YYYY-MM-DD");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        String isImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;
    }

    private static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element not exist in tab");
        }
    }
}
