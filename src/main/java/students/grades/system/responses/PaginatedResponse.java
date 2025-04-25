package students.grades.system.responses;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalRecords;

    public PaginatedResponse(Page<T> pageData) {
        this.data = pageData.getContent();
        this.page = pageData.getNumber();
        this.pageSize = pageData.getSize();
        this.totalPages = pageData.getTotalPages();
        this.totalRecords = pageData.getTotalElements();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
