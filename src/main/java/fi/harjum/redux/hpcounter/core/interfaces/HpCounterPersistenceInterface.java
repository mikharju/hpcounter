package fi.harjum.redux.hpcounter.core.interfaces;

import java.util.List;

public interface HpCounterPersistenceInterface {
    RpgCharacter persistNewCharacter(String name, int maxHp);
    List<RpgCharacter> listCharacters();
    ActionResponse updateCharacter(RpgCharacter rpgCharacter);
    RpgCharacter getCharacter(int id);
    ActionResponse deleteCharacter(int id);
}
