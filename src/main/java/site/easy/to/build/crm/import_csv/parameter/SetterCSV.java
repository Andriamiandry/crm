package site.easy.to.build.crm.import_csv.parameter;

import site.easy.to.build.crm.import_csv.LineValue;

@FunctionalInterface
public interface SetterCSV<T> {
    public T get(LineValue value);
}
