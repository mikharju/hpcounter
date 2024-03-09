package fi.harjum.redux.hpcounter.core.interfaces;

import java.util.List;

public interface HpCounterCommandInterface {
    ActionResponse incHp(int id, int hpDelta);
    RpgCharacter newCharacter(String name, int maxHp);
    ActionResponse updateCharacter(int id, String newName, Integer newMaxHp);
    ActionResponse deleteCharacter(int id);
    List<RpgCharacter> listCharacters();
}
