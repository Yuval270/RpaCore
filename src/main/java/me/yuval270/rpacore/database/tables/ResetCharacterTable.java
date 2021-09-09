package me.yuval270.rpacore.database.tables;

import me.yuval270.rpacore.database.SingleAbstractTable;

public class ResetCharacterTable extends SingleAbstractTable {
    public ResetCharacterTable() {
        super("character_reset", "UUID", "STAGE");
    }
}
