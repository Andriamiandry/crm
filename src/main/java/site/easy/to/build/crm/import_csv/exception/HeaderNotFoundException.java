package site.easy.to.build.crm.import_csv.exception;

public class HeaderNotFoundException extends RuntimeException{
    public HeaderNotFoundException(String headerName){
        super("Header "+headerName+" not found");
    }
}
