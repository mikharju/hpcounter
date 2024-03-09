package fi.harjum.redux.hpcounter.core.interfaces;

import java.util.List;

public interface HpCounterCommandInterface {
    ActionResponse incHp(int id, int hpDelta);
    RpgCharacter newCharacter(NewCharacterRequest newChar);
    ActionResponse updateCharacter(int id, String newName, int newMaxHp);
    ActionResponse deleteCharacter(int id);
    List<RpgCharacter> listCharacters();
}
