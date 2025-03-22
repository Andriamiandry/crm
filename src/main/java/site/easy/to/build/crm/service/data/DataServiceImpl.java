package site.easy.to.build.crm.service.data;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.DataRepository;

@Service
public class DataServiceImpl implements DataService {
    private final DataRepository dataRepository;

    public DataServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public String resetData() {
        return this.dataRepository.resetDatabase();
    }

    @Override
    public String importCSV(String csvFile){
        return this.dataRepository.importCSV(csvFile);
    }
}