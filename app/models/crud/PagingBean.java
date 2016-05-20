package models.crud;

public class PagingBean {

    private Integer page;
    private Boolean current;

    public PagingBean(Integer page, Boolean current) {
        this.page = page;
        this.current = current;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}
