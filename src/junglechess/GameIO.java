package junglechess;

public interface GameIO {

    void load(byte[] data, Board board);

    byte[] save(Board board);

    String getFileTypeDescription();

    String getFileExtension();
}