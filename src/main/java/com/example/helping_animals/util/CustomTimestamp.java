package com.example.helping_animals.util;

import java.sql.Timestamp;

public class CustomTimestamp extends Timestamp {

    public CustomTimestamp(int year, int month, int date, int hour, int minute, int second, int nano) {
        super(year, month, date, hour, minute, second, nano);
    }

    public CustomTimestamp(long time) {
        super(time);
    }

    public CustomTimestamp(Timestamp timestamp){
        super(timestamp.getTime());
    }

    @Override
    public String toString() {
        return super.getDate() + "." + (super.getMonth() + 1) + "." + (super.getYear() + 1900);
    }
}
