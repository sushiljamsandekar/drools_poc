package com.example.app;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ProcessedMessage extends SpecificRecordBase implements SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ProcessedMessage\",\"namespace\":\"com.example.app\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"long\"},{\"name\":\"payload\",\"type\":\"string\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"discount\",\"type\":\"double\"},{\"name\":\"suspicious\",\"type\":\"boolean\"}]}");
    private java.lang.CharSequence id;
    private long timestamp;
    private java.lang.CharSequence payload;
    private java.lang.CharSequence status;
    private double discount;
    private boolean suspicious;

    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

    public ProcessedMessage() {}

    public ProcessedMessage(java.lang.CharSequence id, java.lang.Long timestamp, java.lang.CharSequence payload, java.lang.CharSequence status, java.lang.Double discount, java.lang.Boolean suspicious) {
        this.id = id;
        this.timestamp = timestamp;
        this.payload = payload;
        this.status = status;
        this.discount = discount;
        this.suspicious = suspicious;
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

    public java.lang.CharSequence getStatus() {
        return status;
    }

    public void setStatus(java.lang.CharSequence value) {
        this.status = value;
    }

    public java.lang.Double getDiscount() {
        return discount;
    }

    public void setDiscount(java.lang.Double value) {
        this.discount = value;
    }

    public java.lang.Boolean getSuspicious() {
        return suspicious;
    }

    public void setSuspicious(java.lang.Boolean value) {
        this.suspicious = value;
    }

    @Override
    public Object get(int field$) {
        switch (field$) {
            case 0: return id;
            case 1: return timestamp;
            case 2: return payload;
            case 3: return status;
            case 4: return discount;
            case 5: return suspicious;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    @Override
    public void put(int field$, java.lang.Object value) {
        switch (field$) {
            case 0: id = (java.lang.CharSequence)value; break;
            case 1: timestamp = (java.lang.Long)value; break;
            case 2: payload = (java.lang.CharSequence)value; break;
            case 3: status = (java.lang.CharSequence)value; break;
            case 4: discount = (java.lang.Double)value; break;
            case 5: suspicious = (java.lang.Boolean)value; break;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    @Override
    public Schema getSchema() {
        return SCHEMA$;
    }

    @Override
    public String toString() {
        return "ProcessedMessage{" +
                "id=\'" + id + "\'," +
                " timestamp=" + timestamp +
                ", payload=\'" + payload + "\'," +
                " status=\'" + status + "\'," +
                " discount=" + discount +
                ", suspicious=" + suspicious +
                "}";
    }
}

