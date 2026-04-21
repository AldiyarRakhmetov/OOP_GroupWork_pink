package models;

import java.io.Serializable;
import java.util.Objects;


public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    // Пустой конструктор для сериализации
    
    public Mark() {}

    // Полный конструктор для удобства создания
    
    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        setFirstAttestation(firstAttestation);
        setSecondAttestation(secondAttestation);
        setFinalExam(finalExam);
    }

    // Сеттеры с глубокой валидацией (защита целостности данных)
    
    public void setFirstAttestation(double val) {
        this.firstAttestation = (val >= 0 && val <= 30) ? val : 0;
    }

    public void setSecondAttestation(double val) {
        this.secondAttestation = (val >= 0 && val <= 30) ? val : 0;
    }

    public void setFinalExam(double val) {
        this.finalExam = (val >= 0 && val <= 40) ? val : 0;
    }

    public double getFirstAttestation() { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam() { return finalExam; }

   
    public double calculateTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

     // Возвращает буквенную оценку
     
    public String calculateLetter() {
        double total = calculateTotal();
        if (total >= 95) return "A";
        if (total >= 90) return "A-";
        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "B-";
        if (total >= 70) return "C+";
        if (total >= 65) return "C";
        if (total >= 60) return "C-";
        if (total >= 55) return "D+";
        if (total >= 50) return "D";
        return "F";
    }

     // Рассчитывает GPA по шкале 4.0.
    
    public double getGPA() {
        double total = calculateTotal();
        if (total >= 95) return 4.0;
        if (total >= 90) return 3.67;
        if (total >= 85) return 3.33;
        if (total >= 80) return 3.0;
        if (total >= 75) return 2.67;
        if (total >= 70) return 2.33;
        if (total >= 65) return 2.0;
        if (total >= 60) return 1.67;
        if (total >= 55) return 1.33;
        if (total >= 50) return 1.0;
        return 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return Double.compare(mark.firstAttestation, firstAttestation) == 0 &&
                Double.compare(mark.secondAttestation, secondAttestation) == 0 &&
                Double.compare(mark.finalExam, finalExam) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstAttestation, secondAttestation, finalExam);
    }

    @Override
    public String toString() {
        return String.format("Total: %.1f [%s], GPA: %.2f (1st: %.1f, 2nd: %.1f, Final: %.1f)",
                calculateTotal(), calculateLetter(), getGPA(), firstAttestation, secondAttestation, finalExam);
    }
}
