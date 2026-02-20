package ideaprojects.springapplicationexample.entity.DTO;

import java.util.List;
import ideaprojects.springapplicationexample.entity.Record;


public class RecordDTO {
    private final List<Record> records;
    private final int numberOfDone;
    private final int numberOfActive;


    public List<Record> getRecords() {
        return records;
    }

    public int getNumberOfDone() {
        return numberOfDone;
    }

    public int getNumberOfActive() {
        return numberOfActive;
    }

    public RecordDTO(List<Record> records, int numberOfDone, int numberOfActive) {
        this.records = records;
        this.numberOfDone = numberOfDone;
        this.numberOfActive = numberOfActive;
    }

}
