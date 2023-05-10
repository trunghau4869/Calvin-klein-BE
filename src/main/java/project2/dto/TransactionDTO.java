package project2.dto;

public class TransactionDTO {
    private String transaction_ID;
    private String successfulBiddingTime;
    private String bidder;
    private String buyer;
    private String productName;
    private Double initialBidPrice;
    private Double finalBidPrice;
    private Double serviceFee;
    private String status;
    private Long totalPage;

    public TransactionDTO() {
    }

    public TransactionDTO(String transaction_ID, String successfulBiddingTime, String bidder, String buyer, String productName, Double initialBidPrice, Double finalBidPrice, Double serviceFee, String status) {
        this.transaction_ID = transaction_ID;
        this.successfulBiddingTime = successfulBiddingTime;
        this.bidder = bidder;
        this.buyer = buyer;
        this.productName = productName;
        this.initialBidPrice = initialBidPrice;
        this.finalBidPrice = finalBidPrice;
        this.serviceFee = serviceFee;
        this.status = status;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public String getTransaction_ID() {
        return transaction_ID;
    }

    public void setTransaction_ID(String transaction_ID) {
        this.transaction_ID = transaction_ID;
    }

    public String getSuccessfulBiddingTime() {
        return successfulBiddingTime;
    }

    public void setSuccessfulBiddingTime(String successfulBiddingTime) {
        this.successfulBiddingTime = successfulBiddingTime;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getInitialBidPrice() {
        return initialBidPrice;
    }

    public void setInitialBidPrice(Double initialBidPrice) {
        this.initialBidPrice = initialBidPrice;
    }

    public Double getFinalBidPrice() {
        return finalBidPrice;
    }

    public void setFinalBidPrice(Double finalBidPrice) {
        this.finalBidPrice = finalBidPrice;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
