package site.easy.to.build.crm.util.csv;

import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.util.csv.exception.HeaderNotFoundException;
import site.easy.to.build.crm.util.csv.parameter.CellCSV;
import site.easy.to.build.crm.util.csv.parameter.SetterCSV;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFile<T> {
    MultipartFile file;

    List<HeaderCSV> headerCSVs;

    String separation;

    List<T> data=new ArrayList<>();

    List<String> errors=new ArrayList<>();

    public CSVFile(MultipartFile multipartFile,String separation) {
        this.file=multipartFile;
        this.separation=separation;
        this.headerCSVs=new ArrayList<>();
    }

    public void readAndTransform(SetterCSV<T> setterCSV) throws Exception {
        List<T> data = new ArrayList<T>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
        ) {
            String line;
            boolean header = true;
            int nbLine=0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(separation);
                if (header) {
                    setHeaders(values);
                    header = false;
                } else {
                    try{
                        LineValue lineValue=getValues(values,nbLine+1);
                        T object = setterCSV.get(lineValue);
                        this.data.add(object);
                    }
                    catch (Exception ex){
                        this.errors.add(ex.getMessage());
                    }
                }
                nbLine++;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private LineValue getValues(String[] values,int line) throws Exception{
        LineValue lineValues = new LineValue();
        for (HeaderCSV headerCSV : headerCSVs) {
            try {
                lineValues.add(headerCSV.header, headerCSV.getValue(values[headerCSV.getIndex()],line));
            } catch (Exception ex) {
                throw ex;
            }
        }
        return lineValues;
    }

    private void setHeaders(String[] values) {
        for (int i = 0; i < values.length; i++) {
            this.addOrSetPosition(new HeaderCSV(values[i], i));
        }
        checkHeaderIfAllGotPosition();
        this.headerCSVs = this.headerCSVs.stream().sorted(Comparator.comparing(HeaderCSV::getIndex)).collect(Collectors.toList());
    }

    private void checkHeaderIfAllGotPosition() {
        for (HeaderCSV headerCSV : headerCSVs) {
            if (headerCSV.index == null) {
                throw new HeaderNotFoundException(headerCSV.header);
            }
        }
    }

    private void addOrSetPosition(HeaderCSV newHeaderCSV) {
        if (!this.headerCSVs.contains(newHeaderCSV)) {
            this.headerCSVs.add(newHeaderCSV);
            return;
        }
        for (HeaderCSV headerCSV : headerCSVs) {
            if (headerCSV.equals(newHeaderCSV)) {
                headerCSV.index = newHeaderCSV.index;
                return;
            }
        }
    }

    public CSVFile<T> addConstraint(String header, CellCSV constraintColumn) {
        this.headerCSVs.add(new HeaderCSV(header, constraintColumn));
        return this;
    }
}
