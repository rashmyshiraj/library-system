package com.rashmy.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fee_config")
public class FeeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // normal fee per book per day
    private double normalFeePerDay;

    // multiplier for overdue fee
    // example 0.5 means 50% extra per day late
    private double overdueMultiplier;

    public FeeConfig() {}

    public FeeConfig(double normalFeePerDay, double overdueMultiplier) {
        this.normalFeePerDay = normalFeePerDay;
        this.overdueMultiplier = overdueMultiplier;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getNormalFeePerDay() { return normalFeePerDay; }
    public void setNormalFeePerDay(double normalFeePerDay) { this.normalFeePerDay = normalFeePerDay; }

    public double getOverdueMultiplier() { return overdueMultiplier; }
    public void setOverdueMultiplier(double overdueMultiplier) { this.overdueMultiplier = overdueMultiplier; }
}
