package br.com.letscode.starwars.enums.types;

public enum ItemType {
    WEAPONS(4), AMMUNITION(3), WATERS(2), FOOD(1);

    private final int price;

    ItemType(int price){
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
