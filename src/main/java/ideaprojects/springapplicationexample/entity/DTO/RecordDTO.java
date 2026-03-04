package ideaprojects.springapplicationexample.entity.DTO;

import java.util.List;
import ideaprojects.springapplicationexample.entity.Record;


public class RecordDTO {
    private final String username;
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

    public String getUserName() {
        return username;
    }

    public RecordDTO(String username, List<Record> records, int numberOfDone, int numberOfActive) {
        this.username = username;
        this.records = records;
        this.numberOfDone = numberOfDone;
        this.numberOfActive = numberOfActive;
    }
}
