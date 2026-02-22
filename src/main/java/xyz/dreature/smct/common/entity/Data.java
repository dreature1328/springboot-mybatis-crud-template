package xyz.dreature.smct.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "数据实体")
public class Data {
    // ===== 字段 =====
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "整型数值")
    private Integer numericValue;

    @Schema(description = "浮点数值")
    private Double decimalValue;

    @Schema(description = "文本内容")
    private String textContent;

    @Schema(description = "激活标志")
    private Boolean activeFlag;

    // ===== 构造方法 =====
    // 无参构造器
    public Data() {
    }

    // 全参构造器
    public Data(Long id, Integer numericValue, Double decimalValue, String textContent, Boolean activeFlag) {
        this.id = id;
        this.numericValue = numericValue;
        this.decimalValue = decimalValue;
        this.textContent = textContent;
        this.activeFlag = activeFlag;
    }

    // 复制构造器
    public Data(Data data) {
        this.id = data.getId();
        this.numericValue = data.getNumericValue();
        this.decimalValue = data.getDecimalValue();
        this.textContent = data.getTextContent();
        this.activeFlag = data.getActiveFlag();
    }

    // ===== Getter 与 Setter 方法 =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Integer numericValue) {
        this.numericValue = numericValue;
    }

    public Double getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(Double decimalValue) {
        this.decimalValue = decimalValue;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    // ===== 其他 =====
    // 字符串表示
    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", numericValue=" + numericValue +
                ", decimalValue=" + decimalValue +
                ", textContent='" + textContent + '\'' +
                ", activeFlag=" + activeFlag +
                '}';
    }
}
