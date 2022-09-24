package com.iiitb;

import com.iiitb.modal.User;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class PaperGenerator {
    static Scanner sc = new Scanner(System.in);
    public void generatePaper(Statement stmt, ResultSet rs, String query, User admin){
        try {
            admin.printCourseList(stmt, rs, query);
            int courseId = sc.nextInt();
            boolean isBreak = false;
            while (!isBreak) {
                int option;
                admin.printSubMenu1();
                option = sc.nextInt();
                switch (option) {
                    case 1: {
                        JSONObject testPattern = admin.getTestPattern(stmt, rs, query, courseId);
                        if (testPattern == null) {
                            System.out.println("Test Pattern is not defined");
                            break;
                        }
                        admin.generatePaperSet(stmt, rs, query, courseId, testPattern, sc);
                        break;
                    }
                    case 2: {
                        String testPatternString = "{";
                        System.out.print("Number of section: ");
                        sc = new Scanner(System.in);
                        String numberOfSection = sc.nextLine();
                        testPatternString += "\"numberOfSection\":\"" + numberOfSection + "\"";

                        System.out.print("Total marks: ");
                        String totalMarks = sc.nextLine();
                        testPatternString += ",\"totalMarks\":\"" + totalMarks + "\"";

                        for (int i = 1; i <= Integer.valueOf(numberOfSection); i++) {
                            System.out.println("Enter detail about section " + i + ":");
                            String sectionInfo = ",\"" + Integer.toString(i) + "\":{";
                            System.out.print("Section marks:");
                            String sectionMarks = sc.nextLine();
                            sectionInfo += "\"sectionMarks\":\"" + sectionMarks + "\"";

                            System.out.print("Question Type: ");
                            String questionType = sc.nextLine();
                            sectionInfo += ",\"questionType\":\"" + questionType + "\"";

                            System.out.print("Number of Question:");
                            String numberOfQuestion = sc.nextLine();
                            sectionInfo += ",\"numberOfQuestion\":\"" + numberOfQuestion + "\"";

                            System.out.print("Number of question attempt: ");
                            String numberOfQuestionAttempt = sc.nextLine();
                            sectionInfo += ",\"numberOfQuestionAttempt\":\"" + numberOfQuestionAttempt + "\"}";
                            testPatternString += sectionInfo;

                        }
                        testPatternString += "}";
                        JSONObject testPattern = new JSONObject(testPatternString);
                        admin.printSubMenu2();
                        option = sc.nextInt();
                        switch (option) {
                            case 1: {
                                admin.updateTestPatternStyle(stmt, query, courseId, testPattern);
                                admin.generatePaperSet(stmt, rs, query, courseId, testPattern, sc);
                                break;
                            }
                            case 2: {
                                admin.generatePaperSet(stmt, rs, query, courseId, testPattern, sc);
                                break;
                            }
                        }

                        break;
                    }
                    case 3: {
                        isBreak = true;
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input");
                        break;
                    }


                }
            }
        }
        catch (Exception e)
        {
         e.printStackTrace();
        }
    }
}
