package fi.harjum.redux.hpcounter.core.logic;

import fi.harjum.redux.hpcounter.core.interfaces.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class HpCounterService implements HpCounterCommandInterface {
    private final HpCounterPersistenceInterface data;

    public HpCounterService(HpCounterPersistenceInterface data) {
        this.data = data;
        List<RpgCharacter> rpgCharacters = data.listCharacters();
        if (CollectionUtils.isEmpty(rpgCharacters)) {
            data.persistNewCharacter("Burt", 15);
            data.persistNewCharacter("Wart", 25);
            data.persistNewCharacter("Eemo", 74);
            data.persistNewCharacter("Haldor", 105);
        }
    }

    @Override
    public ActionResponse incHp(int id, int hpDelta) {
        RpgCharacter old = data.getCharacter(id);
        ActionResponse updateResponse = data.updateCharacter(HpCounterFunc.incHp(id, old, hpDelta));
        return reportDataOpsResult("increment hp", updateResponse, "character %s", updateResponse.payload());
    }

    @Override
    public RpgCharacter newCharacter(String name, int maxHp) {
        HpCounterFunc.validateMaxHpIsPositive(maxHp);
        HpCounterFunc.validateCharacterNameIsNotEmpty(name);
        return data.persistNewCharacter(name, maxHp);
    }

    @Override
    public ActionResponse updateCharacter(int id, String newName, Integer newMaxHp) {
        RpgCharacter oldChar = data.getCharacter(id);
        RpgCharacter newChar = HpCounterFunc.updateCharacter(id, oldChar, newName, newMaxHp);
        data.updateCharacter(newChar);
        return ResponseFactory.success("Updated character from %s to %s", oldChar, newChar);
    }

    @Override
    public ActionResponse deleteCharacter(int id) {
        RpgCharacter old = data.getCharacter(id);
        HpCounterFunc.validateCharacterExists(old, id);
        ActionResponse resp = data.deleteCharacter(id);
        return reportDataOpsResult("delete character", resp, "character %s", old);
    }

    @Override
    public List<RpgCharacter> listCharacters() {
        return data.listCharacters();
    }

    private ActionResponse reportDataOpsResult(String actionType, ActionResponse dataOpResult, String details, Object ... params) {
        if (dataOpResult.error() != null) {
            return ResponseFactory.error(1, "[FAILED] %s, error was: %s", actionType, dataOpResult.error());
        }
        return ResponseFactory.success("[SUCCESS] %s, details: %s.", actionType, String.format(details, params));
    }
}
