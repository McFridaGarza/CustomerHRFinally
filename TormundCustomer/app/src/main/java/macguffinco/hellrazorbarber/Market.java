package macguffinco.hellrazorbarber;

public class Market {
    private String Title;
    private String Price;
    private String Description;
    private int Image;


    public Market(String title, String price, String description, int image) {
        Title = title;
        Price = price;
        Description = description;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
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

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(int image) {
        Image = image;
    }
}
