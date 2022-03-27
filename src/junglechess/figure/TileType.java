package junglechess.figure;

public enum TileType {

    TRAP("trap"),
    DEN("den"),
    SHORE("shore"),
    WATER("water"),
    LAND("land");

    private String name;

    TileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
