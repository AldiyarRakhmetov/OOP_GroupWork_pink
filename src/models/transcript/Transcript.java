package models.transcript;

import models.course.Mark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Mark> records;

    public Transcript() {
        this.records = new ArrayList<>();
    }

    public void printTranscript() {
        if (records.isEmpty()) {
            System.out.println("Transcript is empty.");
            return;
        }
        System.out.println("TRANSCRIPT");
        for (int i = 0; i < records.size(); i++) {
            Mark mark = records.get(i);
            System.out.printf(
                    "Course #%d | FA: %.1f | SA: %.1f | Final: %.1f | Total: %.2f | Grade: %s%n",
                    i + 1,
                    mark.getFirstAttestation(),
                    mark.getSecondAttestation(),
                    mark.getFinalExam(),
                    mark.calculateTotal(),
                    mark.calculateLetter()
            );
        }
        System.out.printf("GPA: %.2f%n", calculateGPA());
    }
    public double calculateGPA() {
        if (records.isEmpty()) return 0.0;
        double total = 0.0;
        for (Mark mark : records) {
            total += letterToGpaPoints(mark.calculateLetter());
        }
        return total / records.size();
    }

    private double letterToGpaPoints(String letter) {
        return switch (letter) {
            case "A+", "A" -> 4.0;
            case "A-"      -> 3.67;
            case "B+"      -> 3.33;
            case "B"       -> 3.0;
            case "B-"      -> 2.67;
            case "C+"      -> 2.33;
            case "C"       -> 2.0;
            case "C-"      -> 1.67;
            case "D+"      -> 1.33;
            case "D"       -> 1.0;
            default        -> 0.0; // F
        };
    }

    public void addRecord(Mark mark) {
        records.add(mark);
    }

    public List<Mark> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return "Transcript{records=" + records.size() + ", GPA=" + String.format("%.2f", calculateGPA()) + "}";
    }
}