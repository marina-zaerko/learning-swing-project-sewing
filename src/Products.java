public class Products {
    private String articul;
    private String name;
    private int num;

    Products(){
      articul="";
      name="";
      num=-1;
    };

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public int getNum() {
        return num;
    }

    public String getArticul() {
        return articul;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
