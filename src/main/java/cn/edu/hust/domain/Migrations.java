package cn.edu.hust.domain;

public class Migrations {
    private String migration;

    private Integer batch;

    public String getMigration() {
        return migration;
    }

    public void setMigration(String migration) {
        this.migration = migration == null ? null : migration.trim();
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }
}