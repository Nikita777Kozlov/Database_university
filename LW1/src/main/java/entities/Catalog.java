package entities;


import entities.main.University;
import exeptions.ShelfLifeException;
import handlers.FileHandler;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Catalog {
    private List<University> companyList = new LinkedList<>();

    public int getSize() {
        return companyList.size();
    }

    public void add(University company) {
        try {
            if (company.getSubject().getPeriod() > company.getStudent().getAge()) {
                companyList.add(company);
            } else {
                throw new ShelfLifeException("delivery time more than shelf life");
            }
        } catch (ShelfLifeException e) {
            e.printStackTrace();
        }
    }

    public void sort() {
        companyList.sort(Comparator.comparing(University::getGrade));
    }

    public void clear() {
        companyList.clear();
    }

    public void println() {
        companyList.forEach(System.out::println);
    }

    public String toString() {
        return companyList.stream()
                .map(University::toString)
                .collect(Collectors.joining("\n"));
    }

    public String toText() {
        return companyList.stream()
                .map(University::toText)
                .collect(Collectors.joining("\n"));
    }

    public void fromText(String s) {
        String[] ss = s.split("\n");
        Arrays.stream(ss).forEach(e -> {
            if (!e.equals("")) {
                companyList.add(University.fromText(e));
            } else {
                System.out.println("empty");
            }
        });
    }

    public void write(String path) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.createFileOutputStream(path);
        fileHandler.write(toText());
        fileHandler.closeOutput();
    }

    public void read(String path) {
        readFromFile(path);
    }

    private void readFromFile(String path) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.createBufferedInputStream(path);
        String s = fileHandler.read();
        fileHandler.closeInput();
        fromText(s);
    }


}
