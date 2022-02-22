package vn.cmc.du21.orderservice.presentation.external.response;

import java.util.List;

public class CartResponse {
    private List<VoucherResponse> selectedVouchers;
    private List<CartProductResponse> items;
    private TotalCartResponse totals;

    public CartResponse() {
    }

    public CartResponse(List<VoucherResponse> selectedVouchers, List<CartProductResponse> items, TotalCartResponse totals) {
        this.selectedVouchers = selectedVouchers;
        this.items = items;
        this.totals = totals;
    }

    public List<VoucherResponse> getSelectedVouchers() {
        return selectedVouchers;
    }

    public void setSelectedVouchers(List<VoucherResponse> selectedVouchers) {
        this.selectedVouchers = selectedVouchers;
    }

    public List<CartProductResponse> getItems() {
        return items;
    }

    public void setItems(List<CartProductResponse> items) {
        this.items = items;
    }

    public TotalCartResponse getTotals() {
        TotalCartResponse totalResponse = new TotalCartResponse();

        long totalPrice = 0;
        for(CartProductResponse item : items)
        {
            totalPrice += item.getPriceWithoutSale();
        }
        totalResponse.setTotalPrice(totalPrice);

        long totalSale = 0; // tong tien sale (cua tung san pham)
        long totalSalePrice = 0;
        for(CartProductResponse item : items)
        {
            totalSalePrice += item.getTotalPrice();
        }
        totalSale = totalPrice - totalSalePrice;
        totalResponse.setTotalSale(totalSale);

        long shippingFee = 40; // phi ship
        totalResponse.setShippingFee(shippingFee);

        long totalDiscount = 0; // tong giam gia (phieu giam gia)
        if(selectedVouchers != null)
        {
            for (VoucherResponse item : selectedVouchers)
            {
                if(totalSalePrice*item.getPercentValue()/100 > item.getUpToValue())
                {
                    totalDiscount += item.getUpToValue();
                }
                else
                {
                    totalDiscount += item.getPercentValue()/100;
                }
            }
        }


        totalResponse.setTotalDiscount(totalDiscount);

        long totalPaySum = totalSalePrice + shippingFee - totalDiscount; // tong tien phai tra
        totalResponse.setTotalPaySum(totalPaySum);
        totals = totalResponse;
        return totals;
    }

    public void setTotals(TotalCartResponse totals) {
        this.totals = totals;
    }
}
