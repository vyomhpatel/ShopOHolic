package b12app.vyom.com.shopoholic.utility;

public class EventTotal {
    String pid;
    String quantity;

    public EventTotal(String pid, String quantity) {
        this.pid = pid;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
