package site.easy.to.build.crm.import_csv.parameter;

import site.easy.to.build.crm.import_csv.exception.CSVException;

public interface CellCSV<T> {
    public T getValue(String value,int line)throws CSVException;
}
