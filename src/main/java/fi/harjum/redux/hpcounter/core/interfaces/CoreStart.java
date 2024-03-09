package fi.harjum.redux.hpcounter.core.interfaces;

import fi.harjum.redux.hpcounter.core.logic.HpCounterService;

public class CoreStart {
    public static HpCounterCommandInterface init(HpCounterPersistenceInterface persistenceSystem) {
        return new HpCounterService(persistenceSystem);
    }
}
