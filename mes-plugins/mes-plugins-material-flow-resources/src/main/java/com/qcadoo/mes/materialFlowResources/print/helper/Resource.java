package com.qcadoo.mes.materialFlowResources.print.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Resource {

    private Long id;
    private String number;
    private BigDecimal quantity;
    private BigDecimal price;
    private Date productionDate;
    private Date expirationDate;
    private BigDecimal quantityInAdditionalUnit;
    private BigDecimal conversion;
    private BigDecimal availableQuantity;
    private BigDecimal reservedQuantity;
    private String givenUnit;
    private Long storageLocationId;
    private String storageLocationNumber;
    private Long additionalCodeId;
    private String additionalCodeCode;
    private Long productId;
    private String productNumber;
    private String productName;
    private Long palletNumberId;
    private String palletNumberNumber;

    public Resource(ResourceDto dto) {
        this.id = dto.getId();
        this.number = dto.getNumber();
        this.quantity = dto.getQuantity();
        this.price = dto.getPrice();
        this.productionDate = dto.getProductionDate();
        this.expirationDate = dto.getExpirationDate();
        this.quantityInAdditionalUnit = dto.getQuantityInAdditionalUnit();
        this.conversion = dto.getConversion();
        this.availableQuantity = dto.getAvailableQuantity();
        this.reservedQuantity = dto.getReservedQuantity();
        this.givenUnit = dto.getGivenUnit();
        this.storageLocationId = dto.getStorageLocationId();
        this.storageLocationNumber = dto.getStorageLocationNumber();
        this.additionalCodeId = dto.getAdditionalCodeId();
        this.additionalCodeCode = dto.getAdditionalCodeCode();
        this.productId = dto.getProductId();
        this.productNumber = dto.getProductNumber();
        this.productName = dto.getProductName();
        this.palletNumberId = dto.getPalletNumberId();
        this.palletNumberNumber = dto.getPalletNumberNumber();
    }

    public String getAdditionalCodeCode() {
        return additionalCodeCode;
    }

    public void setAdditionalCodeCode(String additionalCodeCode) {
        this.additionalCodeCode = additionalCodeCode;
    }

    public Long getAdditionalCodeId() {
        return additionalCodeId;
    }

    public void setAdditionalCodeId(Long additionalCodeId) {
        this.additionalCodeId = additionalCodeId;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getConversion() {
        return conversion;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getGivenUnit() {
        return givenUnit;
    }

    public void setGivenUnit(String givenUnit) {
        this.givenUnit = givenUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getPalletNumberId() {
        return palletNumberId;
    }

    public void setPalletNumberId(Long palletNumberId) {
        this.palletNumberId = palletNumberId;
    }

    public String getPalletNumberNumber() {
        return palletNumberNumber;
    }

    public void setPalletNumberNumber(String palletNumberNumber) {
        this.palletNumberNumber = palletNumberNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantityInAdditionalUnit() {
        return quantityInAdditionalUnit;
    }

    public void setQuantityInAdditionalUnit(BigDecimal quantityInAdditionalUnit) {
        this.quantityInAdditionalUnit = quantityInAdditionalUnit;
    }

    public BigDecimal getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(BigDecimal reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public String getStorageLocationNumber() {
        return storageLocationNumber;
    }

    public void setStorageLocationNumber(String storageLocationNumber) {
        this.storageLocationNumber = storageLocationNumber;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Resource))
            return false;
        Resource resource = (Resource) o;
        return Objects.equals(expirationDate, resource.expirationDate) &&
                Objects.equals(conversion, resource.conversion) &&
                Objects.equals(storageLocationId, resource.storageLocationId) &&
                Objects.equals(additionalCodeId, resource.additionalCodeId) &&
                Objects.equals(productId, resource.productId) &&
                Objects.equals(palletNumberId, resource.palletNumberId);
    }

    @Override public int hashCode() {
        return Objects.hash(expirationDate, conversion, storageLocationId, additionalCodeId, productId, palletNumberId);
    }
}
