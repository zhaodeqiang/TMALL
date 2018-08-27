package tmall.util;

/**
 * 分页类
 */
public class Pagination {
    private int start;//起始索引
    private int count;//每页数据量
    private int total;//总数据量
    private String param;

    public int getStart() {
       return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Pagination() {
        this.start = 0;
        this.count = 15;
    }

    public Pagination(int start, int count, int total) {
        this.start = start;
        this.count = count;
        this.total = total;
    }

    /**
     * @return 总页数
     */
    public int getTotalPage() {
    // int totalPage=(int)Math.ceil((total*1.0)/count);
        int totalPage=(this.total+this.count-1)/this.count;
        return totalPage;
    }

    /**
     * @return 最后一页的第一项的页码
     */

    public int getLastPage() {
        int lastPage;
       //14      10
        //  4    10
        if (total % count == 0) {
            lastPage = total - count;
        } else {
            lastPage = total - total % count;
        }
        //total < count
        lastPage = lastPage < 0 ? 0 : lastPage;
        return lastPage;
    }

    public boolean isHasPrevious() {
        return this.start > 0;
    }

    public boolean isHasNext() {
        return this.start != getLastPage();
    }
}
