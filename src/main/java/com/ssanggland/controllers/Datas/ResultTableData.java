package com.ssanggland.controllers.Datas;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class ResultTableData {
    private LongProperty resultIdColumn;
    private StringProperty resultMatchColumn;
    private StringProperty resultMatchDateColumn;
    private StringProperty resultMatchVerseColumn;
    private StringProperty resultBettingStateColumn;
    private LongProperty resultMoneyColumn;

    public ResultTableData(LongProperty resultIdColumn, StringProperty resultMatchColumn,
                           StringProperty resultMatchDateColumn, StringProperty resultMatchVerseColumn,
                           StringProperty resultBettingStateColumn, LongProperty resultMoneyColumn) {
        this.resultIdColumn = resultIdColumn;
        this.resultMatchColumn = resultMatchColumn;
        this.resultMatchDateColumn = resultMatchDateColumn;
        this.resultMatchVerseColumn = resultMatchVerseColumn;
        this.resultBettingStateColumn = resultBettingStateColumn;
        this.resultMoneyColumn = resultMoneyColumn;
    }

    public LongProperty resultIdColumnProperty() {
        return resultIdColumn;
    }

    public StringProperty resultMatchColumnProperty() {
        return resultMatchColumn;
    }

    public StringProperty resultMatchDateColumnProperty() {
        return resultMatchDateColumn;
    }

    public StringProperty resultMatchVerseColumnProperty() {
        return resultMatchVerseColumn;
    }

    public StringProperty resultBettingStateColumnProperty() {
        return resultBettingStateColumn;
    }

    public LongProperty resultMoneyColumnProperty() {
        return resultMoneyColumn;
    }
}
