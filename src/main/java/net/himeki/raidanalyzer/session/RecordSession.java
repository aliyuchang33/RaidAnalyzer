package net.himeki.raidanalyzer.session;

import net.himeki.raidanalyzer.record.RaidRecord;

import java.time.LocalDate;
import java.util.ArrayList;

public class RecordSession {
    private ArrayList<RaidRecord> recordsList = new ArrayList<>();
    private LocalDate date;
    private String alias;


    public RecordSession(LocalDate date, String alias) {
        this.date = date;
        if (alias == null)
            this.alias = date.toString();
        else
            this.alias = alias;
    }

    public void addRecord(RaidRecord record) {
        recordsList.add(record);

    }
}
