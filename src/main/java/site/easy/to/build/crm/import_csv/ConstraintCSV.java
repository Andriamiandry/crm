package site.easy.to.build.crm.import_csv;

import site.easy.to.build.crm.import_csv.exception.CSVException;
import site.easy.to.build.crm.import_csv.parameter.CellCSV;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class ConstraintCSV {

    public static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    public static final LongPositive LONG_POSITIVE=new LongPositive();

    public static final IntPositive INT_POSITIVE=new IntPositive();

    public static final DoublePositive DOUBLE_POSITIVE=new DoublePositive();

    public static final LocalDateConstraint LOCALDATE=new LocalDateConstraint();

    public static final BigDecimalPositive BIG_DECIMAL_POSITIVE = new BigDecimalPositive();


    public static final ListForeign LIST_FOREIGN = new ListForeign();

    public static final TimeConstraint LOCAL_TIME = new TimeConstraint();

    public static final LocalDateTimeConstraint LOCALDATE_TIME = new LocalDateTimeConstraint();

    public static class BigDecimalPositive implements CellCSV<BigDecimal> {

        @Override
        public BigDecimal getValue(String cell, int line) throws CSVException {
            try {
                BigDecimal value = new BigDecimal(cell);
                if (value.compareTo(BigDecimal.ZERO) < 0) {
                    throw new CSVException("Valeur négative sur la ligne " + line);
                }
                return value;
            } catch (NumberFormatException ex) {
                throw new CSVException("Format numérique invalide sur la ligne " + line);
            }
        }
    }

    public static class EmailValidator implements CellCSV<String> {

        private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        @Override
        public String getValue(String cell, int line) throws CSVException {
            if (cell == null || cell.trim().isEmpty()) {
                throw new CSVException("Email is required on line " + line);
            }

            if (!EMAIL_PATTERN.matcher(cell).matches()) {
                throw new CSVException("Invalid email format on line " + line);
            }

            return cell.trim();
        }
    }

    public static class LongPositive implements CellCSV<Long>{

        @Override
        public Long getValue(String cell,int line) throws CSVException {
            try{
                Long value=Long.parseLong(cell);
                if(value<0){
                    throw new CSVException("Valeur négative sur la ligne "+line);
                }
                return value;
            }
            catch (NumberFormatException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class IntPositive implements CellCSV<Integer>{

        @Override
        public Integer getValue(String cell,int line) throws CSVException {
            try{
                Integer value=Integer.parseInt(cell);
                if(value<0){
                    throw new CSVException("Valeur négative sur la ligne "+line);
                }
                return value;
            }
            catch (NumberFormatException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class TimeConstraint implements CellCSV<LocalTime>{

        @Override
        public LocalTime getValue(String cell,int line) throws CSVException {
            try{
                LocalTime value=LocalTime.parse(cell);
                return value;
            }
            catch (Exception ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class LocalDateTimeConstraint implements CellCSV<LocalDateTime>{

        @Override
        public LocalDateTime getValue(String cell,int line) throws CSVException {
            try{
                LocalDateTime value=LocalDateTime.parse(cell);
                return value;
            }
            catch (Exception ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class DoublePositive implements CellCSV<Double>{

        @Override
        public Double getValue(String cell,int line) throws CSVException {
            try{
                Double value=Double.parseDouble(cell);
                if(value<0){
                    throw new CSVException("Valeur négative sur la ligne "+line);
                }
                return value;
            }
            catch (NumberFormatException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class LocalDateConstraint implements CellCSV<LocalDate>{

        @Override
        public LocalDate getValue(String cell,int line) throws CSVException {
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate value=LocalDate.parse(cell,formatter);
                return value;
            }
            catch (Exception ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class ListForeign implements CellCSV<List<String>>{

        @Override
        public List<String> getValue(String cell,int line) throws CSVException {
            try{
                List<String> value=new ArrayList<>();
                String[] foreignKeySplitted=cell.split(";");
                for(String foreignKey:foreignKeySplitted){
                    value.add(foreignKey.replace(" ",""));
                }
                return value;
            }
            catch (DateTimeException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }
}
