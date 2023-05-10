package project2.dto;

public class AuctionDTO {
    private Long productId;
    private Long memberId;
    private Long accountId;
    private String bidder;
    private Double price;
    private String username;
    private String time;

    public AuctionDTO() {
    }

    public AuctionDTO(Long productId, Long memberId, Long accountId, String bidder, Double price, String username, String time) {
        this.productId = productId;
        this.memberId = memberId;
        this.accountId = accountId;
        this.bidder = bidder;
        this.price = price;
        this.username = username;
        this.time = time;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
