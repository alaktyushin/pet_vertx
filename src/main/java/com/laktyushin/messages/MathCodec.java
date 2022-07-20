package com.laktyushin.messages;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import java.io.*;

public class MathCodec<T> implements MessageCodec<T, T> {
    private final Class<T> cls;
    public MathCodec(Class<T> cls) {
        super();
        this.cls = cls;
    }

    @Override
    public void encodeToWire(Buffer buffer, T s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(s);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            byte[] yourBytes = bos.toByteArray();
            buffer.appendInt(yourBytes.length);
            buffer.appendBytes(yourBytes);
        try {
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T decodeFromWire(int pos, Buffer buffer) {
        int position = pos;
        int length = buffer.getInt(position);
        position += 4;
        byte[] bytes = buffer.getBytes(position, position + length);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            T msg = (T) ois.readObject();
            ois.close();
            bis.close();
            return msg;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T transform(T customMessage) {
        return customMessage;
    }

    @Override
    public String name() {
        return "MathCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
