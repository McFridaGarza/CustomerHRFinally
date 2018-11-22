package macguffinco.hellrazorbarber;

public class Cart {
    private String Title;
    private String Price;
    private String Cant;
    private int Image;


    public Cart() {
    }

    public Cart(String title, String price, String cant, int image) {
        Title = title;
        Price = price;
        Cant = cant;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public String getPrice() {
        return Price;
    }

    public String getCant() {
        return Cant;
    }

    public int getImage() {
        return Image;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setCant(String cant) {
        Cant = cant;
    }

    public void setImage(int image) {
        Image = image;
    }
}
