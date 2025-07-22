package com.example.app;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class RawMessage extends SpecificRecordBase implements SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"RawMessage\",\"namespace\":\"com.example.app\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"long\"},{\"name\":\"payload\",\"type\":\"string\"}]}");
    private java.lang.CharSequence id;
    private long timestamp;
    private java.lang.CharSequence payload;

    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

    public RawMessage() {}

    public RawMessage(java.lang.CharSequence id, java.lang.Long timestamp, java.lang.CharSequence payload) {
        this.id = id;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public java.lang.CharSequence getId() {
        return id;
    }

    public void setId(java.lang.CharSequence value) {
        this.id = value;
    }

    public java.lang.Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(java.lang.Long value) {
        this.timestamp = value;
    }

    public java.lang.CharSequence getPayload() {
        return payload;
    }

    public void setPayload(java.lang.CharSequence value) {
        this.payload = value;
    }

    @Override
    public Object get(int field$) {
        switch (field$) {
            case 0: return id;
            case 1: return timestamp;
            case 2: return payload;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    @Override
    public void put(int field$, java.lang.Object value) {
        switch (field$) {
            case 0: id = (java.lang.CharSequence)value; break;
            case 1: timestamp = (java.lang.Long)value; break;
            case 2: payload = (java.lang.CharSequence)value; break;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    @Override
    public Schema getSchema() {
        return SCHEMA$;
    }

    @Override
    public String toString() {
        return "RawMessage{" +
                "id=\'" + id + "\'," +
                " timestamp=" + timestamp +
                ", payload=\'" + payload + "\'" +
                "}";
    }
}


