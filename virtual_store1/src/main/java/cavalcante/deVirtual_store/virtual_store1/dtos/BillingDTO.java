package cavalcante.deVirtual_store.virtual_store1.dtos;

import java.io.Serializable;

public class BillingDTO implements Serializable {

    private static final long serialVversionUID = 1l;

    private boolean free;
    private boolean dataBase;

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isDataBase() {
        return dataBase;
    }

    public void setDataBase(boolean dataBase) {
        this.dataBase = dataBase;
    }
}
