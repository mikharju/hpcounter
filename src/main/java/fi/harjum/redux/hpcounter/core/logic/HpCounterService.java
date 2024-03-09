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
        RpgCharacter old = checkExistingCharacter(id);
        int newHp = old.hp() + hpDelta;
        int limitedHp = Math.min(Math.max(0, newHp), old.maxHp());
        ActionResponse updateResponse = data.updateCharacter(new RpgCharacter(old.name(), limitedHp, old.id(), old.maxHp()));
        if (updateResponse.error() != null) {
            return ResponseFactory.error(2, "Failed to update character %d hp, error was: %s", id, updateResponse.error());
        }
        return ResponseFactory.success("Updated hp of %s to %d.", old.name(), limitedHp);
    }

    @Override
    public RpgCharacter newCharacter(NewCharacterRequest newChar) {
        return data.persistNewCharacter(newChar.name(), newChar.hp());
    }

    @Override
    public ActionResponse updateCharacter(int id, String newName, int newMaxHp) {
        RpgCharacter old = checkExistingCharacter(id);
        int limitedHp = Math.max(old.maxHp(), newMaxHp);
        data.updateCharacter(new RpgCharacter(newName, limitedHp, id, newMaxHp));
        return ResponseFactory.success("Updated character with id %d name from %s to %s and maxHp from %d to %d", id, old.name(), newName, old.maxHp(), limitedHp);
    }

    @Override
    public ActionResponse deleteCharacter(int id) {
        RpgCharacter old = checkExistingCharacter(id);
        ActionResponse resp = data.deleteCharacter(id);
        if (resp.error() == null) {
            return ResponseFactory.success("Deleted character %s", old.toString());
        }
        return resp;
    }

    @Override
    public List<RpgCharacter> listCharacters() {
        return data.listCharacters();
    }

    private RpgCharacter checkExistingCharacter(int id) {
        RpgCharacter old = data.getCharacter(id);
        if (old == null) {
            throw new HpCounterException("Character with id %d not found.", id);
        }
        return old;
    }
}
