package fi.harjum.redux.hpcounter.core.logic;

import fi.harjum.redux.hpcounter.core.interfaces.HpCounterException;
import fi.harjum.redux.hpcounter.core.interfaces.RpgCharacter;
import org.apache.commons.lang3.StringUtils;

public class HpCounterFunc {
    public static RpgCharacter updateCharacter(int id, RpgCharacter old, String newName, Integer newMaxHp) {
        validateMaxHpIsPositive(newMaxHp);
        validateCharacterExists(old, id);
        return new RpgCharacter(
                StringUtils.isBlank(newName) ? old.name() : newName,
                Math.min(newMaxHp, old.hp()),
                old.id(),
                newMaxHp != null ? newMaxHp : old.maxHp()
        );
    }

    public static RpgCharacter incHp(int id, RpgCharacter old, int hpDelta) {
        validateCharacterExists(old, id);
        int newHp = old.hp() + hpDelta;
        int limitedHp = Math.min(Math.max(0, newHp), old.maxHp());
        return new RpgCharacter(old.name(), limitedHp, old.id(), old.maxHp());
    }

    public static void validateMaxHpIsPositive(Integer newMaxHp) {
        if (newMaxHp < 1) {
            throw new HpCounterException("NewMaxHp can not be negative.");
        }
    }

    public static void validateCharacterExists(RpgCharacter in, int id) {
        if (in == null) {
            throw new HpCounterException("Character with id %s not found.", id);
        }
    }

    public static void validateCharacterNameIsNotEmpty(String name) {
        if (StringUtils.isBlank(name)) {
            throw new HpCounterException("Character name can not be blank.");
        }
    }
}
