package com.laktyushin.messages;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import java.io.*;

public class MathCodec<T> implements MessageCodec<T, T> {
    public MathCodec() {
    }

    @Override
    public void encodeToWire(Buffer buffer, T s) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(s);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            buffer.appendInt(bytes.length);
            buffer.appendBytes(bytes);
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T decodeFromWire(int position, Buffer buffer) {
        position += 4;
        byte[] bytes = buffer.getBytes(position, position + buffer.getInt(position));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            T msg = (T) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
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
